
public class Board {
    public Piece[][] pieces = new Piece[8][8];
    public Board(){
        //TODO implement constructor
    }

    public void getLegalMoves(){
        //TODO iterate through pieces list and determine their legal moves based on other pieces' positions
    }

    public void checkMoveLegal(String move){
        //TODO alternate to getLegalMoves, checks a specific input move.
    }

    public void makePlayerMove(String move){
        //TODO Once the player inputs a move through the CLI and it's determined legal,
        // adjust piece position and prompt game to update position
    }
}
