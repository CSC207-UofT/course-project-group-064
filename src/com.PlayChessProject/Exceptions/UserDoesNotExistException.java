package Exceptions;

public class UserDoesNotExistException extends Exception{
    private String message;

    public UserDoesNotExistException(){
        this.message = "The user does not exist in the database.><";
    }

    public String getMessage(){
        return this.message;
    }
}