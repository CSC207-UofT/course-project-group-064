public class Game {
    private String game_mode;
    public boolean isBlacksTurn;
    public Game(String game_mode){
        this.game_mode = game_mode;
        //TODO implement initial game setup
    }
    public void initializeDisplay(){
        //TODO displays image of board and pieces in default positions depending on gamemode
    }

    public void updateDisplay(String move){
        //TODO parse move and update display based on new board state.
    }

    public void calculateElo(boolean game_result, User white, User black){
        //TODO update user elos based on game result
    }
}
