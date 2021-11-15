import java.util.*;
import java.util.concurrent.TransferQueue;

public class Board {
    private final int[] queenOffsets = {-9, -8, -7, -1, 1, 7, 8, 9};
    private final int[] queenIndecies = {0, 1, 2, 3, 4, 5, 6, 7};
    private final int[] rookIndecies = {1, 3, 4, 6};
    private final int[] bishopIndecies = {0, 2, 5, 7};
    private final int[] whitePawnOffsets = {-7, -8, -9};
    private final int[] blackPawnOffsets = {7, 8, 9};
    private int[] lastMove = {0, 0};

    private Map<Integer, Piece> piecePositions;

    public Board(String gameMode) {
        this.piecePositions = new HashMap<>();
        //TODO implement constructor
        if (gameMode.equals("Standard")) {
            standardPieceArangement();
        }
    }

    public void getLegalMoves(boolean turn) {
        //TODO iterate through pieces list and determine their legal moves based on other pieces' positions
        //for piece in piecePositions, if color matches turn (maybe hold pieces of different
        //colors in different arrays
        //get legal moves for piece's type, check that each move doesn't lead to
        //check for turn player
    }

    public boolean checkMoveLegal(int origin, int destination) {
        Piece piece = piecePositions.get(origin);
        if (piece instanceof King) {
            return Utils.contains(getKingMoves(origin), destination);
        }
        if (piece instanceof Pawn) {
            return Utils.contains(getPawnMoves(origin), destination);
        }
        if (piece instanceof Knight) {
            return Utils.contains(getKnightMoves(origin), destination);
        } else {
            return Utils.contains(getSlidingMoves(origin), destination);
        }
    }

    //TODO make private once inside bigger method
    public int[] getKingMoves(int origin) {
        Piece piece = piecePositions.get(origin);
        ArrayList<Integer> moves = new ArrayList<>();
        for (int move : piece.getValidMoves()) {
            if (!piecePositions.containsKey(move) || piecePositions.get(move).getColor() != piece.getColor()) {
                moves.add(move);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    public int[] getPawnMoves(int origin) {
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
        if (piece.getNotMoved()) {
            destination = origin + 2 * offsets[1];
            if (!piecePositions.containsKey(destination)) {
                moves.add(destination);
            }
        }
        //Check capture diagonally right
        if (Utils.NUMSQUARESTOEDGE[origin][4] > 0) {
            destination = origin + offsets[2];
            if ((piecePositions.containsKey(destination) && piecePositions.get(destination).getColor() != piece.getColor()) || canEnPassant(origin, false)) {
                moves.add(destination);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    private boolean canEnPassant(int origin, boolean left_right) {
        int target = left_right ? origin - 1 : origin + 1; //Is the target piece to the left or the right of origin
        Piece targetPiece = piecePositions.get(target);
        return (targetPiece instanceof Pawn && targetPiece.getColor() != piecePositions.get(origin).getColor() &&
                lastMove[1] == target && (lastMove[0] == target + 16 || lastMove[0] == target - 16));
    }

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

    public int[] getSlidingMoves(int origin) {
        //Gets moves for long range sliding pieces: Queen, Rook, Bishop
        Piece piece = getPiecePositions().get(origin);
        int[] offsets = (piece instanceof Queen) ? queenIndecies : (piece instanceof Rook) ? rookIndecies : bishopIndecies;
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

    //checks if given board state is in check
    public String inCheck(Board board) {
        for (Piece piece : board.piecePositions.values()) {
            if (piece instanceof King) {
                //declarations for easy access
                int king_pos = piece.getPos();
                int king_file = posToFileRank(king_pos)[0];
                int king_rank = posToFileRank(king_pos)[1];
                boolean king_color = piece.getColor();
                Bishop bishop = new Bishop(king_color, king_file, king_rank);
                Rook rook = new Rook(king_color, king_file, king_rank);
                Queen queen = new Queen(king_color, king_file, king_rank);
                //check pawns
                for (int move : piece.getValidMoves()) {
                    if (king_color && (piecePositions.get(move) instanceof Pawn && (move == (king_pos - 9) ||
                            move == (king_pos - 7)) && !piecePositions.get(move).getColor())) {
                        return returnResult(true);
                    }
                    if (!king_color && (piecePositions.get(move) instanceof Pawn &&(move == (king_pos + 9) ||
                            move == (king_pos + 7)) &&  piecePositions.get(move).getColor())) {
                        return returnResult(false);
                    }
                }
                //check for bishop, rook, queen, knight, king
                if (checkSliding(king_color, king_pos, bishop) || checkSliding(king_color, king_pos, rook) ||
                        checkSliding(king_color, king_pos, queen) || checkKnights(king_color, king_file, king_rank) ||
                        checkKing(king_color, piece)) {
                    return returnResult(king_color);
                }

            }
        }
        return "";
    }

    public boolean makePlayerMove(String move, boolean color) {
        //TODO Once the player inputs a move through the CLI and it's determined legal,
        // adjust piece position and prompt game to update position

        //Parse CLI move input
        boolean move_valid = false;
        String[] orDest = move.split(",");
        int origin = algebraicToInt(orDest[0]);
        int destination = algebraicToInt(orDest[1]);

        //Check that the origin is occupied
        if (checkMoveLegal(origin, destination)) {
            piecePositions.remove(destination);
            piecePositions.put(destination, piecePositions.remove(origin));
            piecePositions.get(destination).updatePosition(destination);
            lastMove[0] = origin;
            lastMove[1] = destination;
            move_valid = true;
        }
        return move_valid;
    }

    public Map<Integer, Piece> getPiecePositions() {
        return piecePositions;
    }

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
            piecePositions.put(i, new Pawn(true, i - 8, 6));
        }
        //Black Pawns
        for (int i = 8; i < 16; i++) {
            piecePositions.put(i, new Pawn(false, i - 40, 0));
        }
    }

    //Helper method that converts a string in algebraic notation to an integer location on the board
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

    //A copy of getSlidingMoves, with different args
    public int[] inCheckSlidingMoves(int origin, Piece piece) {
        int[] offsets = (piece instanceof Queen) ? queenIndecies : (piece instanceof Rook) ? rookIndecies : bishopIndecies;
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

    public boolean checkSliding(boolean color, int king_pos, Piece piece) {
        for (int move : inCheckSlidingMoves(king_pos, piece)){
            if (piecePositions.get(move) != null && piecePositions.get(move).getClass().getName() ==
                    piece.getClass().getName() && piecePositions.get(move).getColor() != color) {
                return true;
            }
        }
        return false;
    }

    public boolean checkKing(boolean color, Piece king) {
        for (int move : king.getValidMoves()) {
            if (piecePositions.get(move) instanceof King && (piecePositions.get(move).getColor() != color)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkKnights(boolean color, int file, int rank) {
        Knight knight = new Knight(color, file, rank);
        for (int move : knight.getValidMoves()) {
            if (piecePositions.get(move) instanceof Knight && (piecePositions.get(move).getColor() != color)) {
                return true;
            }
        }
        return false;
    }

    public int[] posToFileRank(int pos){
        int[] fin = {0, 0};
        if (pos <= 7) {
            fin[0] = pos;
            fin[1] = 7;
        }
        if (pos <= 15) {
            fin[0] = pos - 8;
            fin[1] = 6;
        }
        if (pos <= 23) {
            fin[0] = pos - 16;
            fin[1] = 5;
        }
        if (pos <= 31) {
            fin[0] = pos - 24;
            fin[1] = 4;
        }
        if (pos <= 7) {
            fin[0] = pos - 32;
            fin[1] = 3;
        }
        if (pos <= 15) {
            fin[0] = pos - 40;
            fin[1] = 2;
        }
        if (pos <= 23) {
            fin[0] = pos - 48;
            fin[1] = 1;
        }
        if (pos <= 31) {
            fin[0] = pos - 56;
            fin[1] = 0;
        }
        return fin;
    }
    public String returnResult(boolean color) {
        if (!color){
            return "black";
        }
        else {
            return "white";
        }
    }
}


