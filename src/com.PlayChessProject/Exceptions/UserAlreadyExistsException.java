package Exceptions;

public class UserAlreadyExistsException extends Exception{
    private String message;

    public UserAlreadyExistsException(){
        this.message = "The user already exists in the System ><";
    }

    public String getMessage(){
        return this.message;
    }
}
