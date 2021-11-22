package Entities;

public class PlayerUser extends User {
    private String name;
    private int elo;

    // TODO: should we add password to the User?
    private String password;

    public PlayerUser(String username, int elo) {
        super(username);
        this.elo = elo;
    }

    // TODO: do we have a default elo score?
    public PlayerUser(String username) {
        super(username);
        this.elo = 0;
    }

    public int getElo(){
        return this.elo;
    }
    public void updateElo(int new_elo){
        this.elo = new_elo;
    }
}
