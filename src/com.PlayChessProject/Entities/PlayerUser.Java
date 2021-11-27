package Entities;
public class PlayerUser extends User{
    private String name;
    private int elo;

    // TODO: should we add password to the User?
    private String password;

    public PlayerUser(String username, int elo) {
        super(username);
        this.elo = elo;
    }

    public PlayerUser(String username) {
        super(username);
        this.elo = 1000;
    }

    public int getElo(){
        return this.elo;
    }
    public void setElo(int newElo){
        super.setElo(newElo);
    }
}