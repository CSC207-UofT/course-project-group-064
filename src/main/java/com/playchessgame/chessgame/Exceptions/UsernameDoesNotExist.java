package com.playchessgame.chessgame.Exceptions;

/**
 * Representing an UsernameDoesNotExist Exception
 */
public class UsernameDoesNotExist extends Exception{

    private final String message;

    public UsernameDoesNotExist(){
        this.message = "The username does not exist in the system.><";
    }

    /**
     * return the message of the exception
     * @return the message attribute of the exception
     */
    public String getMessage(){
        return this.message;
    }
}
