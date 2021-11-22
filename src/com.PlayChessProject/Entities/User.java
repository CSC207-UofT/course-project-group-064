package Entities;

public class User{
    private String name;

    // TODO: should we add password to the User?
    private String password;

    private int elo;

    // TODO: do we have a default elo score?
    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    public User(){};

    public User(String username){
        this.name = username;
    }

    public User(String username, int elo){
        this.name = username;
        this.elo = elo;
    }

    public User(String username, String password, int elo){
        this.name = username;
        this.password = password;
        this.elo = elo;
    }

    public String getName(){
        return this.name;
    }

    public boolean passwordMatch(String password) {return this.password.matches(password);}

    public int getElo(){
        return this.elo;
    }
}
