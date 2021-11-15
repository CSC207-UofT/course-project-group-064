package Controllers;

import Entities.Game;
import UseCases.PlayChess.PlayChess;

import java.util.Scanner;

public class PlayChessController {

    private PlayChess playChess;

    public PlayChessController(PlayChess playChess){
        this.playChess = playChess;
    }

    public void playGame(String gameMode){
        //TODO: present players a GUI and players select a game mode

        playChess.playGame(gameMode);
    }

    public static void main(String[] args) {
        PlayChessController playChessController = new PlayChessController(new PlayChess());

        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Chess Game!\n");
        System.out.println("Please Enter Your GameMode:\n");
        String gameMode = sc.nextLine().strip();

        playChessController.playGame(gameMode);
    }








}
