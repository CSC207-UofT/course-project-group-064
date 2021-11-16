package UseCases.PlayChess;

import Entities.Game;

public class PlayChess {

    /**
     * Start off the Chess Game
     * @param gameMode: the gamemode selected by players
     */
    public void playGame(String gameMode){
        Game game = new Game(gameMode);
        game.initializeDisplay();
        String move = game.getMove();
        while (!move.equals("end")){
            game.updateDisplay(move);
            move = game.getMove();
        }

    }
}
