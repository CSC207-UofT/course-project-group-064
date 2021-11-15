package Entities;
public class User{
    private String name;
    private int elo;

    // TODO: should we add password to the User?
    private String password;

    // TODO: do we have a default elo score?
    public User(String username, String password, int elo){
        this.name = username;
        this.password = password;
        this.elo = elo;
    }

    public User(String username, String password){
        this.name = username;
        this.password = password;
        this.elo = 0;
    }


    public String getName(){
        return this.name;
        }
    public int getElo(){
        return this.elo;
        }

    public boolean passwordMatch(String password) {return this.password.matches(password);}

    public void updateElo(int new_elo){
        this.elo = new_elo;
    }
}