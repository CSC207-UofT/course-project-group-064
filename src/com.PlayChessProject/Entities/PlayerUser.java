package Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlayerUser extends User {
    @Id
    private String name;
    @Column
    private int elo;
    @Column
    // TODO: should we add password to the User?
    private String password;

//    public PlayerUser(String username, int elo) {
//        super(username);
//        this.elo = elo;
//    }

    // TODO: do we have a default elo score?
//    public PlayerUser(String username) {
//        super(username);
//        this.elo = 0;
//    }


    @Override
    public String getName() {return name;}

    public void setName(String name) {this.name = name;
    }

    public void setElo(int elo) {this.elo = elo;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public int getElo(){
        return this.elo;
    }

    public void updateElo(int new_elo){
        this.elo = new_elo;
    }
}
