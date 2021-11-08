package Exceptions;

public class UserAlreadyExistsException extends Exception{
    private String message;

    public UserAlreadyExistsException(){
        this.message = "The User Already Exists in the System ><";
    }
}
