import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
    public Board(String gameMode){
        this.piecePositions = new HashMap<>();
        //TODO implement constructor
        if (gameMode.equals("Standard")){
            standardPieceArangement();
        }
    }

    public void getLegalMoves(){
        //TODO iterate through pieces list and determine their legal moves based on other pieces' positions

    }

    public boolean checkMoveLegal(int origin, int destination){
        //TODO alternate to getLegalMoves, checks a specific input move.
        Piece originPiece = piecePositions.get(origin);
        if (!Arrays.asList(originPiece.getValidMoves()).contains(destination)){
            return false;
        }
        if(piecePositions.containsKey(destination)){
            if (originPiece.getColor() == piecePositions.get(destination).getColor()){
                return false;
            }
        }
        if (originPiece instanceof Knight){
            //Don't need to check collisions for knight as long as it isn't landing on an allied piece
            return true;
        }
        if (originPiece instanceof Pawn){
            return destination == origin + 1 || destination == origin - 1 || piecePositions.containsKey(destination);
        }
        //TODO check if squares between current and destination are occupied. Necessary for king, bishop, rook, and queen
        return false;
    }
    //TODO make private once inside bigger method
    public int[] getKingMoves(int origin){
        Piece piece = piecePositions.get(origin);
        ArrayList<Integer> moves = new ArrayList<>();
        for(int move : piece.getValidMoves()){
            if(!piecePositions.containsKey(move) || piecePositions.get(move).getColor() != piece.getColor()){
                moves.add(move);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }
    public int[] getPawnMoves(int origin){
        Piece piece = piecePositions.get(origin);
        int[] offsets = piece.getColor() ? whitePawnOffsets : blackPawnOffsets;
        ArrayList<Integer> moves = new ArrayList<>();
        int destination;
        if(Utils.NUMSQUARESTOEDGE[origin][3] > 0){
            destination = origin + offsets[0];
            if(piecePositions.containsKey(destination) && piecePositions.get(destination).getColor() != piece.getColor() || canEnPassant(origin, true)){
                moves.add(destination);
            }
        }
        destination = origin + offsets[1];
        if (!piecePositions.containsKey(destination)){
            moves.add(destination);
        }
        if (piece.getNotMoved()){
            destination = origin + 2 * offsets[1];
            if (!piecePositions.containsKey(destination)){
                moves.add(destination);
            }
        }
        if (Utils.NUMSQUARESTOEDGE[origin][4] > 0){
            destination = origin + offsets[2];
            if((piecePositions.containsKey(destination) && piecePositions.get(destination).getColor() != piece.getColor()) || canEnPassant(origin, false)){
                moves.add(destination);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }

    private boolean canEnPassant(int origin, boolean left_right) {
        int target = left_right ? origin - 1 : origin + 1; //Is the target piece to the left or the right of origin
        Piece targetPiece = piecePositions.get(target);
        return (targetPiece instanceof Pawn && targetPiece.getColor() != piecePositions.get(origin).getColor() &&
                lastMove[1] == target && (lastMove[0] == target +16 || lastMove[0] == target - 16));
    }

    public int[] getKnightMoves(int origin){
        Piece piece = piecePositions.get(origin);
        ArrayList<Integer> moves = new ArrayList<>();
        for(int move : piece.getValidMoves()){
            if (piecePositions.get(move) == null || piecePositions.get(move).getColor() != piece.getColor()){
                moves.add(move);
            }
        }
        return moves.stream().mapToInt(i -> i).toArray();
    }
    public int[] getSlidingMoves(int origin){
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

    public void makePlayerMove(String move, boolean color){
        //TODO Once the player inputs a move through the CLI and it's determined legal,
        // adjust piece position and prompt game to update position

        //Parse CLI move input
        String [] orDest = move.split(",");
        int origin = algebraicToInt(orDest[0]);
        int destination = algebraicToInt(orDest[1]);

        //Check that the origin is occupied
        if (piecePositions.containsKey(origin)){
            piecePositions.remove(destination);
            piecePositions.put(destination, piecePositions.remove(origin));
            piecePositions.get(destination).updatePosition(destination);
        }
        lastMove[0] = origin;
        lastMove[1] = destination;
    }

    public Map<Integer, Piece> getPiecePositions(){
        return piecePositions;
    }

    private void standardPieceArangement(){
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
        piecePositions.put(3, new Queen(false,3,7));
        piecePositions.put(4, new King(false, 4,7));
        piecePositions.put(5, new Bishop(false, 5, 7));
        piecePositions.put(6, new Knight(false, 6,7));
        piecePositions.put(7, new Rook(false, 7, 7));
        //White Pawns
        for (int i = 48; i < 56; i++){
            piecePositions.put(i, new Pawn(true, i - 8, 6));
        }
        //Black Pawns
        for (int i = 8; i < 16; i++){
            piecePositions.put(i, new Pawn(false, i - 40, 0));
        }
    }

    //Helper method that converts a string in algebraic notation to an integer location on the board
    public int algebraicToInt(String move){
        int file = Character.toUpperCase(move.charAt(0)) - 65;
        if (move.length() == 2 && 0 <= file && file < 8){
            int rank = Character.getNumericValue(move.charAt(1));
            int inMove = (8-rank) * 8 + file;
            if (inMove < 64 && inMove >= 0){
                return inMove;
            }
            else {
                throw new RuntimeException("Move is Invalid");
            }
        }
        else {
            throw new RuntimeException("Moves must be entered in algebraic notation");
        }
    }
}
