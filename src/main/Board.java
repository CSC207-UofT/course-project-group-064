import java.util.HashMap;
import java.util.Map;

public class Board {
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

    public void checkMoveLegal(String move){
        //TODO alternate to getLegalMoves, checks a specific input move.
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
        }
    }

    public Map<Integer, Piece> getPiecePositions(){
        return piecePositions;
    }

    private void standardPieceArangement(){
        //White back rank
        piecePositions.put(56, new Rook(true, 0, 7));
        piecePositions.put(57, new Knight(true, 1, 7));
        piecePositions.put(58, new Bishop(true, 2, 7));
        piecePositions.put(59, new Queen(true, 3, 7));
        piecePositions.put(60, new King(true, 4, 7));
        piecePositions.put(61, new Bishop(true, 5, 7));
        piecePositions.put(62, new Knight(true, 6, 7));
        piecePositions.put(63, new Rook(true, 7, 7));
        //Black back rank
        piecePositions.put(0, new Rook(false, 0, 0));
        piecePositions.put(1, new Knight(false, 1, 0));
        piecePositions.put(2, new Bishop(false, 2, 0));
        piecePositions.put(3, new Queen(false,3,0));
        piecePositions.put(4, new King(false, 4,0));
        piecePositions.put(5, new Bishop(false, 5, 0));
        piecePositions.put(6, new Knight(false, 6,0));
        piecePositions.put(7, new Rook(false, 7, 0));
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
