package com.playchessgame.chessgame.Exceptions;

public class UsernameDoesNotExist extends Exception{

    private String message;

    public UsernameDoesNotExist(){
        this.message = "The username does not exist in the system.><";
    }

    public String getMessage(){
        return this.message;
    }
}
