package UseCases.PlayChess;

import Entities.Game;
import Entities.PlayerUser;

public class PlayChess {

    /**
     * Start off the Chess Game
     * @param gameMode: the gamemode selected by players
     */
    public void playGame(String gameMode){
        PlayerUser white = new PlayerUser("Test", 1000);
        PlayerUser black = new PlayerUser("test", 1000);
        Game game = new Game(gameMode, white, black);
        game.initializeDisplay();
        String move = game.getMove();
        while (!move.equals("end")){
            //game.updateDisplay(move);
            move = game.getMove();
        }

    }
}
