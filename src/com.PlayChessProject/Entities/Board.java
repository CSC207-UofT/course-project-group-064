package Entities;
import java.util.*;

public class Board {

    //Piece type offsets
    private final int[] queenOffsets = {-9, -8, -7, -1, 1, 7, 8, 9};
    private final int[] queenIndecies = {0, 1, 2, 3, 4, 5, 6, 7};
    private final int[] rookIndecies = {1, 3, 4, 6};
    private final int[] bishopIndecies = {0, 2, 5, 7};
    private final int[] whitePawnOffsets = {-7, -8, -9};
    private final int[] blackPawnOffsets = {7, 8, 9};

    private int[] lastMove = {0, 0};
    private boolean turn = true;


    //Move result indicators
    public static final int LEGAL = 0;
    public static final int ILLEGAL = 1;
    public static final int CHECKMATE = 2;
    public static final int STALEMATE = 3;

    private Map<Integer, Piece> piecePositions;

    public Board(String gameMode) {
        this.piecePositions = new HashMap<>();
        if (gameMode.equals("Standard")) {
            standardPieceArangement();
        }
    }

    public int[][] getLegalMoves(boolean checkTurn) {
        /*Returns every legal move that can be made on this turn. The returned 2d array is a list of every move that
        * can be made by the pieces currently availible to the active player. The first element of each piece's move
        * array is the piece's current position.*/
        int[][] moves = new int[16][];
        int index = 0;
        HashMap<Integer, Piece> tempPositions = new HashMap<>(piecePositions);
        for(int key : tempPositions.keySet()){
            Piece piece = piecePositions.get(key);
            if(piece.getColor() == checkTurn) {
                moves[index] = pieceTypeMoves(piece, checkTurn);
                index++;
            }
        }
        return moves;
    }

    public boolean checkMoveLegal(int origin, int destination) {
        if (!piecePositions.containsKey(origin) || piecePositions.get(origin).getColor() != turn){
            return false;
        }
        int[] moves = pieceTypeMoves(piecePositions.get(origin), turn);
        return Utils.contains(moves, destination);
    }

    /**Helper method that returns a piece's legal moves regardless of type with the first element of the returned
     * array being the piece's starting position.*/
    public int[] pieceTypeMoves(Piece piece, boolean checkTurn){
        int[] pseudoMoves; //Pseudo legal moves before check and mate checks
        int position = piece.getPos();
        if (piece instanceof Pawn){
            pseudoMoves = getPawnMoves(position);
        }
        else if (piece instanceof King){
            pseudoMoves = getKingMoves(position);
        }
        else if (piece instanceof Knight){
            pseudoMoves = getKnightMoves(position);
        }
        else{
            pseudoMoves = getSlidingMoves(position);
        }
        ArrayList<Integer> moves = new ArrayList<>();
        moves.add(piece.getPos());
        for (int move : pseudoMoves){
            Map<Integer, Piece> shallowPiecePositions = new HashMap<>(piecePositions);
            piecePositions.remove(move);
            piecePositions.put(move, piecePositions.remove(position));
            if (!inCheck(checkTurn)){
                moves.add(move);
            }
            piecePositions = shallowPiecePositions;
        }
        return moves.stream().mapToInt(i -> i).toArray();
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
        if (origin==0){
            int k=0;
        }
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
    public boolean inCheck(boolean player) {
        for (int key : piecePositions.keySet()) {
            Piece piece = piecePositions.get(key);
            if (piece instanceof King && piece.getColor() == player) {
                //declarations for easy access
                int king_file = key % 8;
                int king_rank = 7 - ((key - king_file) / 8);
                boolean king_color = piece.getColor();
                Bishop bishop = new Bishop(king_color, king_file, king_rank);
                Rook rook = new Rook(king_color, king_file, king_rank);
                Queen queen = new Queen(king_color, king_file, king_rank);
                //check pawns
                int pawnOffset1 = king_color ? 7 : -7;
                int pawnOffset2 = king_color ? 9 : -9;
                for (int move : piece.getValidMoves()) {
                    if ((piecePositions.get(move) instanceof Pawn && (move == (key - pawnOffset2) ||
                            move == (key - pawnOffset1)) && piecePositions.get(move).getColor() != king_color)) {
                        return true;
                    }
                }
                //check for bishop, rook, queen, knight, king
                if (checkSliding(king_color, key, bishop) || checkSliding(king_color, key, rook) ||
                        checkSliding(king_color, key, queen) || checkKnights(king_color, king_file, king_rank) ||
                        checkKing(king_color, piece)) {
                    return true;
                }

            }
        }
        return false;
    }

    /**checks if a position is checkmate or stalemate.
     returns 0 if no mate
     returns 2 if checkmate
     returns 3 if stalemate**/
    public int checkMate(){
        int[][] moves = getLegalMoves(!turn);
        for (int[] piece : moves){
            if (piece != null && piece.length > 1){
                return LEGAL;
            }
        }
        if (inCheck(!turn)){
            return CHECKMATE;
        }
        else {
            return STALEMATE;
        }
    }

    /**Makes players move
     * returns 0 if the move was valid and the game continues
     * retunrs 1 if the move was illegal
     * returns 2 if the move was checkmate
     * returns 3 if the move was stalemate
     * */
    public int makePlayerMove(String move) {
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
            if (piecePositions.get(origin) instanceof Pawn && (destination >= 56 || destination <= 7)){
                piecePositions.remove(destination);
                piecePositions.put(destination, new Queen(turn, destination % 8, 7 -
                        ((destination - (destination % 8)) / 8)));
            }
        }
        if (!move_valid){
            return ILLEGAL;
        }
        else {
            int result = checkMate();
            turn = !turn;
            return result;
        }
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
            piecePositions.put(i, new Pawn(true, i % 8, 1));
        }
        //Black Pawns
        for (int i = 8; i < 16; i++) {
            piecePositions.put(i, new Pawn(false, i % 8, 6));
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
}


