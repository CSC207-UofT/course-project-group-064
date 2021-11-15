package Entities;
public class User{
    private String name;

    // TODO: should we add password to the User?
    private String password;

    // TODO: do we have a default elo score?
    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    public String getName(){
        return this.name;
    }

    public boolean passwordMatch(String password) {return this.password.matches(password);}
}