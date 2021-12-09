package com.playchessgame.chessgame.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    //Piece type movement direction offsets
    private final int[] queenOffsets = {-9, -8, -7, -1, 1, 7, 8, 9};
    private final int[] queenIndices = {0, 1, 2, 3, 4, 5, 6, 7};
    private final int[] whitePawnOffsets = {-9, -8, -7};
    private final int[] blackPawnOffsets = {7, 8, 9};

    //Indices of concern for pieces in numSquaresToEdge
    private final int[] rookIndices = {1, 3, 4, 6};
    private final int[] bishopIndices = {0, 2, 5, 7};
    private final int[][] whiteCastleIndices = {{56, 57, 58, 59, 60}, {63, 100, 62, 61, 60}};
    private final int[][] blackCastleIndices = {{0, 1, 2, 3, 4}, {7, 100, 6, 5, 4}};

    private int[] lastMove = {0, 0};
    private boolean turn = true; //True if white's turn, false if black's turn

    //Move result indicators
    public static final int LEGAL = 0;
    public static final int ILLEGAL = 1;
    public static final int CHECKMATE = 2;
    public static final int STALEMATE = 3;

    public static final int WHITE_KING_START_SQUARE = 60;
    public static final int BLACK_KING_START_SQUARE = 4;

    private Map<Integer, Piece> piecePositions; //Keys are integer positions of pieces on board.

    public Board(String gameMode) {
        this.piecePositions = new HashMap<>();
        if (gameMode.equals("Standard")) {
            standardPieceArangement();
        }
    }

    /**
     * @param checkTurn boolean player whose moves we are looking at.
     * @return 2d int array of every move that can be made. [piece][piece's moves]. The first element of each sub-array
     * is the current position of the piece.
     */
    public int[][] getLegalMoves(boolean checkTurn) {
        int[][] moves = new int[16][];
        int index = 0;
        HashMap<Integer, Piece> tempPositions = new HashMap<>(piecePositions);
        for (int key : tempPositions.keySet()) {
            Piece piece = piecePositions.get(key);
            if (piece.getColor() == checkTurn) {
                moves[index] = pieceTypeMoves(piece, checkTurn);
                index++;
            }
        }
        return moves;
    }

    /**
     * Checks if a given move is legal.
     *
     * @param origin      the starting location of the piece being moved
     * @param destination the ending location of the piece being moved
     * @return boolean true if the move is legal, false if the move is illegal.
     */
    public boolean checkMoveLegal(int origin, int destination) {
        if (!piecePositions.containsKey(origin) || piecePositions.get(origin).getColor() != turn) {
            return false;
        }
        int[] moves = pieceTypeMoves(piecePositions.get(origin), turn);
        return Utils.contains(moves, destination);
    }

    /**
     * Helper method to get list of legal moves for a specific piece by first filtering it by piece type and calling
     * relevant sub-methods.
     *
     * @param piece     the piece whose moves are being checked.
     * @param checkTurn the player we are checking moves for, or their opponent if we are looking for check mate.
     * @return int array of legal moves for a specific piece starting with that piece's current location.
     */
    public int[] pieceTypeMoves(Piece piece, boolean checkTurn) {
        int[] pseudoMoves; //Pseudo legal moves before check and mate checks
        int position = piece.getPos();
        if (piece instanceof Pawn) {
            pseudoMoves = getPawnMoves(position);
        } else if (piece instanceof King) {
            pseudoMoves = getKingMoves(position);
        } else if (piece instanceof Knight) {
            pseudoMoves = getKnightMoves(position);
        } else {
            pseudoMoves = getSlidingMoves(position);
        }
        return filterLegalMoves(piece, pseudoMoves, checkTurn);
    }

    /**
     * @param piece       the piece whose moves are being checked
     * @param pseudoMoves a list of pseudo legal moves for the piece. This method checks that those moves do not leave
     *                    the king attacked.
     * @param checkTurn   the player whose king is being checked for attacks.
     * @return int array of legal moves for piece beginning with its current location.
     */
    public int[] filterLegalMoves(Piece piece, int[] pseudoMoves, boolean checkTurn) {
        ArrayList<Integer> moves = new ArrayList<>();
        int position = piece.getPos();
        moves.add(position);
        for (int move : pseudoMoves) {
            Map<Integer, Piece> shallowPiecePositions = new HashMap<>(piecePositions);
            piecePositions.remove(move);
            piecePositions.put(move, piecePositions.remove(position));
            if (!inCheck(checkTurn)) {
                moves.add(move);
            }
            piecePositions = shallowPiecePositions;
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * @param origin king's current position
     * @return int list of moves the king can make that are not blocked by other pieces.
     */
    private int[] getKingMoves(int origin) {
        Piece piece = piecePositions.get(origin);
        ArrayList<Integer> moves = new ArrayList<>();
        for (int move : piece.getValidMoves()) {
            if (!piecePositions.containsKey(move) || piecePositions.get(move).getColor() != piece.getColor()) {
                moves.add(move);
            }
        }
        if (piece.getNotMoved() && (piece.getPos() == WHITE_KING_START_SQUARE || piece.getPos() == BLACK_KING_START_SQUARE)) {
            for (int move : castleMoves(piece)) {
                moves.add(move);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * @param origin the pawn's current position
     * @return array of pseudo-legal moves and captures including en passant.
     */
    private int[] getPawnMoves(int origin) {
        Piece piece = piecePositions.get(origin);
        int[] offsets = piece.getColor() ? whitePawnOffsets : blackPawnOffsets;
        ArrayList<Integer> moves = new ArrayList<>();
        int destination;
        //Check capture diagonally left
        if (Utils.NUMSQUARESTOEDGE[origin][3] > 0) {
            destination = origin + offsets[0];
            if (piecePositions.containsKey(destination) && piecePositions.get(destination).getColor() != piece.getColor() || canEnPassant(origin, true)) {
                moves.add(destination);
            }
        }
        //Check move forward
        destination = origin + offsets[1];
        if (!piecePositions.containsKey(destination)) {
            moves.add(destination);
        }
        if (piece.getNotMoved() && !piecePositions.containsKey(destination)) {
            destination = origin + 2 * offsets[1];
            if (!piecePositions.containsKey(destination)) {
                moves.add(destination);
            }
        }
        //Check capture diagonally right
        if (Utils.NUMSQUARESTOEDGE[origin][4] > 0) {
            destination = origin + offsets[2];
            if ((piecePositions.containsKey(destination) && piecePositions.get(destination).getColor() != piece.getColor())
                    || canEnPassant(origin, false)) {
                moves.add(destination);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * @param origin     pawn's current position
     * @param left_right location of target piece. left = true, right = false
     * @return true if the pawn can capture en passant, false otherwise.
     */
    private boolean canEnPassant(int origin, boolean left_right) {
        int target = left_right ? origin - 1 : origin + 1; //Is the target piece to the left or the right of origin
        Piece targetPiece = piecePositions.get(target);
        return (targetPiece instanceof Pawn && targetPiece.getColor() != piecePositions.get(origin).getColor() &&
                lastMove[1] == target && (lastMove[0] == target + 16 || lastMove[0] == target - 16));
    }

    /**
     * @param origin knight's current position
     * @return array of pseudo-legal knight moves.
     */
    public int[] getKnightMoves(int origin) {
        Piece piece = piecePositions.get(origin);
        ArrayList<Integer> moves = new ArrayList<>();
        for (int move : piece.getValidMoves()) {
            if (piecePositions.get(move) == null || piecePositions.get(move).getColor() != piece.getColor()) {
                moves.add(move);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * gets moves for long range sliding pieces: Queen, Rook Bishop
     * Sliding piece moves are all checked the same way, so we use preset piece movement indices and loop over them to
     * the edge of the board, stopping if we encounter a friendly piece or after capturing an unfriendly piece.
     *
     * @param origin sliding piece starting position
     * @return int array of pseudo-legal moves
     */
    public int[] getSlidingMoves(int origin) {
        Piece piece = getPiecePositions().get(origin);
        int[] offsets = (piece instanceof Queen) ? queenIndices : (piece instanceof Rook) ? rookIndices : bishopIndices;
        ArrayList<Integer> moves = new ArrayList<>();
        for (int offset : offsets) {
            for (int j = 1; j <= Utils.NUMSQUARESTOEDGE[origin][offset]; j++) {
                int move = origin + queenOffsets[offset] * j;
                if (piecePositions.containsKey(move)) {
                    if (piecePositions.get(move).getColor() != piece.getColor()) {
                        moves.add(move);
                    }
                    break;
                } else {
                    moves.add(move);
                }
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Checks if the given player is in check by using inCheckHelper
     *
     * @param player the given player
     * @return true if they are in check, false otherwise
     */
    public boolean inCheck(boolean player) {
        for (int key : piecePositions.keySet()) {
            if (piecePositions.get(key) instanceof King && piecePositions.get(key).getColor() == player) {
                int king_file = key % 8;
                int king_rank = 7 - ((key - king_file) / 8);
                if (inCheckHelper(key, king_file, king_rank, player)) return true;
            }
        }
        return false;
    }

    /**
     * Helper method for inCheck that does most of the work. Declares temp pieces at the kings location and uses
     * the valid moves of those pieces to check the corresponding spaces. Uses helpers to check for each type of
     * attacking piece.
     *
     * @param key        pos of King
     * @param king_file  King's file
     * @param king_rank  King's rank
     * @param king_color King's color
     * @return returns true if a check is detected, false otherwise.
     */
    private boolean inCheckHelper(int key, int king_file, int king_rank, boolean king_color) {
        //declaring temp pieces
        Bishop bishop = new Bishop(king_color, king_file, king_rank);
        Rook rook = new Rook(king_color, king_file, king_rank);
        Queen queen = new Queen(king_color, king_file, king_rank);
        King king = new King(king_color, king_file, king_rank);
        //check pawns
        int pawnOffset1 = king_color ? 7 : -7;
        int pawnOffset2 = king_color ? 9 : -9;
        for (int move : king.getValidMoves()) {
            if ((piecePositions.get(move) instanceof Pawn && (move == (key - pawnOffset2) ||
                    move == (key - pawnOffset1)) && piecePositions.get(move).getColor() != king_color)) {
                return true;
            }
        }
        //check for bishop, rook, queen, knight, king
        return checkSliding(king_color, key, bishop) || checkSliding(king_color, key, rook) ||
                checkSliding(king_color, key, queen) || checkKnights(king_color, king_file, king_rank) ||
                checkKing(king_color, king);
    }

    /**
     * checks if a position is checkmate or stalemate.
     * returns 0 if no mate
     * returns 2 if checkmate
     * returns 3 if stalemate or insufficient material
     **/
    public int checkMate() {
        ArrayList<Piece> pieces = new ArrayList<>();
        for (int key : piecePositions.keySet()) {
            pieces.add(piecePositions.get(key));
        }
        if (pieces.size() < 3) {
            return STALEMATE;
        }
        int[][] moves = getLegalMoves(!turn);
        for (int[] piece : moves) {
            if (piece != null && piece.length > 1) {
                return LEGAL;
            }
        }
        if (inCheck(!turn)) {
            return CHECKMATE;
        } else {
            return STALEMATE;
        }
    }

    /**
     * Makes players move
     * returns 0 if the move was valid and the game continues
     * returns 1 if the move was illegal
     * returns 2 if the move was checkmate
     * returns 3 if the move was stalemate
     */
    public int makePlayerMove(int origin, int destination) {
        boolean move_valid = false;
        if (origin == destination) {
            return Board.ILLEGAL;
        }
        //Check that the origin is occupied
        if (checkMoveLegal(origin, destination)) {
            if (piecePositions.get(origin) instanceof King && piecePositions.get(origin).getNotMoved()) {
                castleMoveHelper(origin, destination);
            }
            piecePositions.remove(destination);
            piecePositions.put(destination, piecePositions.remove(origin));
            piecePositions.get(destination).updatePosition(destination);
            lastMove[0] = origin;
            lastMove[1] = destination;
            move_valid = true;
            if (piecePositions.get(origin) instanceof Pawn && (destination >= 56 || destination <= 7)) {
                piecePositions.remove(destination);
                piecePositions.put(destination, new Queen(turn, destination % 8, 7 -
                        ((destination - (destination % 8)) / 8)));
            }
        }
        if (!move_valid) {
            return ILLEGAL;
        } else {
            int result = checkMate();
            turn = !turn;
            return result;
        }
    }

    public Map<Integer, Piece> getPiecePositions() {
        return piecePositions;
    }

    /**
     * Sets up pieces in standard chess starting position.
     */
    private void standardPieceArangement() {
        //White back rank
        piecePositions.put(56, new Rook(true, 0, 0));
        piecePositions.put(57, new Knight(true, 1, 0));
        piecePositions.put(58, new Bishop(true, 2, 0));
        piecePositions.put(59, new Queen(true, 3, 0));
        piecePositions.put(60, new King(true, 4, 0));
        piecePositions.put(61, new Bishop(true, 5, 0));
        piecePositions.put(62, new Knight(true, 6, 0));
        piecePositions.put(63, new Rook(true, 7, 0));
        //Black back rank
        piecePositions.put(0, new Rook(false, 0, 7));
        piecePositions.put(1, new Knight(false, 1, 7));
        piecePositions.put(2, new Bishop(false, 2, 7));
        piecePositions.put(3, new Queen(false, 3, 7));
        piecePositions.put(4, new King(false, 4, 7));
        piecePositions.put(5, new Bishop(false, 5, 7));
        piecePositions.put(6, new Knight(false, 6, 7));
        piecePositions.put(7, new Rook(false, 7, 7));
        //White Pawns
        for (int i = 48; i < 56; i++) {
            piecePositions.put(i, new Pawn(true, i % 8, 1));
        }
        //Black Pawns
        for (int i = 8; i < 16; i++) {
            piecePositions.put(i, new Pawn(false, i % 8, 6));
        }
    }

    /**
     * Currently unused but relevant for implementation of fen string reader.
     *
     * @param move square string in algebraic notation
     * @return integer location of that square
     */
    public int algebraicToInt(String move) {
        int file = Character.toUpperCase(move.charAt(0)) - 65;
        if (move.length() == 2 && 0 <= file && file < 8) {
            int rank = Character.getNumericValue(move.charAt(1));
            int inMove = (8 - rank) * 8 + file;
            if (inMove < 64 && inMove >= 0) {
                return inMove;
            } else {
                throw new RuntimeException("Move is Invalid");
            }
        } else {
            throw new RuntimeException("Moves must be entered in algebraic notation");
        }
    }

    /**
     * A copy of getSlidingMoves with different arguments. Helper method for inCheck.
     *
     * @param origin Origin of piece
     * @param piece  Piece to be checked for
     * @return an int array of spaces to check for inCheck
     */
    public int[] inCheckSlidingMoves(int origin, Piece piece) {
        int[] offsets = (piece instanceof Queen) ? queenIndices : (piece instanceof Rook) ? rookIndices : bishopIndices;
        ArrayList<Integer> moves = new ArrayList<>();
        for (int offset : offsets) {
            for (int j = 1; j <= Utils.NUMSQUARESTOEDGE[origin][offset]; j++) {
                int move = origin + queenOffsets[offset] * j;
                if (piecePositions.containsKey(move)) {
                    if (piecePositions.get(move).getColor() != piece.getColor()) {
                        moves.add(move);
                    }
                    break;
                } else {
                    moves.add(move);
                }
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Checks sliding moves by iterating through inCheckSlidingMoves for check. Helper for inCheck.
     *
     * @param color    color of original king
     * @param king_pos king's position
     * @param piece    temporary piece (sliding pieces)
     * @return true if a check is detected, false otherwise.
     */
    private boolean checkSliding(boolean color, int king_pos, Piece piece) {
        for (int move : inCheckSlidingMoves(king_pos, piece)) {
            if (piecePositions.get(move) != null && piecePositions.get(move).getClass().getName().
                    equals(piece.getClass().getName()) && piecePositions.get(move).getColor() != color) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks king moves for check. Helper for inCheck.
     *
     * @param color color of original king
     * @param king  temporary piece (king)
     * @return true if a check is detected, false otherwise.
     */
    private boolean checkKing(boolean color, Piece king) {
        for (int move : king.getValidMoves()) {
            if (piecePositions.get(move) instanceof King && (piecePositions.get(move).getColor() != color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks knights moves for check. Helper for inCheck.
     *
     * @param color color of original king
     * @param file  file of king
     * @param rank  rank of king
     * @return true if a check is detected, false otherwise.
     */
    private boolean checkKnights(boolean color, int file, int rank) {
        Knight knight = new Knight(color, file, rank);
        for (int move : knight.getValidMoves()) {
            if (piecePositions.get(move) instanceof Knight && (piecePositions.get(move).getColor() != color)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Finds the possible castling moves a King can make. Uses castleHelper to figure it out.
     *
     * @param piece The original King
     * @return an integer array of positions the King can castle to.
     */
    private int[] castleMoves(Piece piece) {
        List<Integer> moves = new ArrayList<>();
        if (turn) {
            if (castleHelper(whiteCastleIndices[0], piece)) {
                moves.add(58);
            }
            if (castleHelper(whiteCastleIndices[1], piece)) {
                moves.add(62);
            }
        } else {
            if (castleHelper(blackCastleIndices[0], piece)) {
                moves.add(2);
            }
            if (castleHelper(blackCastleIndices[1], piece)) {
                moves.add(6);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Helper method for castleMoves. Checks the requirements for a King to castle.
     *
     * @param indices the indices to check
     * @param piece   the original king
     * @return true if a castle is possible on a specific side.
     */
    private boolean castleHelper(int[] indices, Piece piece) {
        if (piece.getNotMoved() && piecePositions.get(indices[0]) instanceof Rook &&
                piecePositions.get(indices[0]).getNotMoved() && !(piecePositions.containsKey(indices[1])) &&
                !(piecePositions.containsKey(indices[2])) && !(piecePositions.containsKey(indices[3])) &&
                !inCheck(turn)) {
            int king_file = indices[3] % 8;
            int king_rank = 7 - ((indices[3] - king_file) / 8);
            return !inCheckHelper(indices[3], king_file, king_rank, turn);
        }
        return false;
    }

    /**
     * Helper method to move the rook when a castle is made.
     *
     * @param origin      Origin of King
     * @param destination destination of king
     */
    private void castleMoveHelper(int origin, int destination) {
        if (turn) {
            if (destination == origin + 2) {
                piecePositions.put(61, piecePositions.remove(63));
                piecePositions.get(61).updatePosition(61);
            }
            if (destination == origin - 2) {
                piecePositions.put(59, piecePositions.remove(56));
                piecePositions.get(59).updatePosition(59);
            }
        } else {
            if (destination == origin - 2) {
                piecePositions.put(3, piecePositions.remove(0));
                piecePositions.get(3).updatePosition(3);
            }
            if (destination == origin + 2) {
                piecePositions.put(5, piecePositions.remove(7));
                piecePositions.get(5).updatePosition(5);
            }
        }
    }

    /**
     * Reinitializes the board class with a given board.
     *
     * @param board the given board that needs to be copied from
     */
    public void copy(Board board) {
        this.piecePositions = new HashMap<>();
        for (int key : board.getPiecePositions().keySet()) {
            boolean color = board.getPiecePositions().get(key).getColor();
            int file = key % 8;
            int rank = 7 - ((key - file) / 8);
            copyHelper(board, key, color, file, rank);
        }
        this.lastMove[0] = board.lastMove[0];
        this.lastMove[1] = board.lastMove[1];
        this.turn = board.turn;
    }

    /**
     * helper method for copy. Creates and places the respective pieces at the locations that are in the other board.
     *
     * @param board the given board to be copied
     * @param key   the given key in which the piece is to be placed
     * @param color the color of the piece
     * @param file  the file in which the piece should be placed
     * @param rank  the rank in which the piece should be placed
     */
    private void copyHelper(Board board, int key, boolean color, int file, int rank) {
        if (board.getPiecePositions().get(key) instanceof Pawn) {
            Pawn pawn = new Pawn(color, file, rank);
            this.piecePositions.put(key, pawn);
        } else if (board.getPiecePositions().get(key) instanceof Bishop) {
            Bishop bishop = new Bishop(color, file, rank);
            this.piecePositions.put(key, bishop);
        } else if (board.getPiecePositions().get(key) instanceof Rook) {
            Rook rook = new Rook(color, file, rank);
            this.piecePositions.put(key, rook);
        } else if (board.getPiecePositions().get(key) instanceof Knight) {
            Knight knight = new Knight(color, file, rank);
            this.piecePositions.put(key, knight);
        } else if (board.getPiecePositions().get(key) instanceof King) {
            King king = new King(color, file, rank);
            this.piecePositions.put(key, king);
        } else if (board.getPiecePositions().get(key) instanceof Queen) {
            Queen queen = new Queen(color, file, rank);
            this.piecePositions.put(key, queen);
        }
    }
}