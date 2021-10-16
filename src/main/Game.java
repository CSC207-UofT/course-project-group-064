import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Game {
    private String game_mode;
    public boolean isWhitesTurn;
    private String textBoardDisplay; //Temporary board display in text
    private Board board;
    private Scanner console;
    private boolean turn = true;

    public Game(String game_mode){
        this.game_mode = game_mode;
        //TODO implement initial game setup
        this.textBoardDisplay = "";
        this.board = new Board(game_mode);
        this.console = new Scanner(System.in);
    }
    public void initializeDisplay(){
        //TODO displays image of board and pieces in default positions depending on gamemode
        if (this.game_mode.equals("Standard")){
            this.standardDisplay();
        }
    }

    public void updateDisplay(String move){
        //TODO parse move and update display based on new board state.
        board.makePlayerMove(move, turn);
        turn = !turn;
        String boardString = toDisplayString();
        System.out.println(boardString);
    }

    public void calculateElo(boolean game_result, User white, User black){
        //TODO update user elos based on game result
    }

    //Initializes display for a classic game of chess.
    private void standardDisplay(){
        String boardString = toDisplayString();
        System.out.println(boardString);
    }

    //Move String Parsing Helper methods
    public String getMove(){
        return console.nextLine();
    }



    //BELOW THIS LINE EXIST TEMPORARY METHODS TO FACILITATE PRINTING AN ASCII CHESSBOARD IN LIEU OF GRAPHICAL DISPLAY
    private String toDisplayString(){
        Map<Integer, Piece> pieceMap = board.getPiecePositions();
       final StringBuilder builder = new StringBuilder();
       for(int i = 0; i < 64; i++){
           String tileText;
           if (pieceMap.containsKey(i)){
               tileText = typeToString(pieceMap.get(i));
               }
           else{
               tileText = "-";
           }
           builder.append(String.format("%3s", tileText));
           if ((i+1) % 8 == 0){
               builder.append("\n");
           }
       }
       return builder.toString();
    }

    //converts piece type to string
    private String typeToString(Piece piece){
        if (piece instanceof Pawn){
            return piece.getColor() ? "P" : "p";
        }

        if (piece instanceof Rook){
            return piece.getColor() ? "R" : "r";
        }

        if (piece instanceof Knight){
            return piece.getColor() ? "N" : "n";
        }

        if (piece instanceof Bishop){
            return piece.getColor() ? "B" : "b";
        }
        else if (piece instanceof King){
            return piece.getColor() ? "K" : "k";
        }
        else if (piece instanceof Queen){
            return piece.getColor() ? "Q" : "q";
        }
        else{
            return "-";
        }
    }

    public static void main(String[] args) {
        Game game = new Game("Standard");
        game.standardDisplay();
        String move = game.getMove();
        while (!move.equals("end")){
            game.updateDisplay(move);
            move = game.getMove();
        }
    }
}
