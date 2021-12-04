import java.util.Map;
import java.util.Scanner;

public class Game {
    private String game_mode; //To be used later
    private String textBoardDisplay; //Temporary board display in text
    public Board board;
    private Scanner console;
    private boolean turn = true; //White = true, Black = false throughout the program

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

    public void updateDisplay(int origin, int destination){
        int valid = board.makePlayerMove(origin, destination);
        if(valid == Board.LEGAL) {
            turn = !turn;
            String boardString = toDisplayString();
            System.out.println(boardString);
        }
        else if (valid == Board.CHECKMATE){
            endGame(true);
        }
        else if (valid == Board.STALEMATE){
            endGame(false);
        }
        else {
            System.out.println("Illegal move, please input a legal move");
        }
    }

    /**Handles game result. If passed true, the player whose turn it is achieved checkmate.
     * if passed false the game ended in a draw*/
    public void endGame(boolean result){
    }

    //Initializes display for a classic game of chess.
    public void standardDisplay(){
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
            //game.updateDisplay(origin, destination);
            move = game.getMove();
        }
    }
}
