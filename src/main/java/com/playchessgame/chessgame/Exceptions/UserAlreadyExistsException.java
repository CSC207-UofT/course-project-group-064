package com.playchessgame.chessgame.Exceptions;

/**
 * Representing an UserAlreadyExist Exception
 */
public class UserAlreadyExistsException extends Exception {
    private final String message;

    public UserAlreadyExistsException() {
        this.message = "The User Already Exists in the System ><";
    }

    /**
     * return the message of the exception
     *
     * @return the message attribute of the exception
     */
    public String getMessage() {
        return this.message;
    }
}
