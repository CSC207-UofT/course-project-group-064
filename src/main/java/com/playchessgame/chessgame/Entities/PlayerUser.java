package com.playchessgame.chessgame.Entities;

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

    /**
     * Returns the player user's username.
     *
     * @return the player user's name
     */
    @Override
    public String getName() {return name;}

    /**
     * Sets the player user's username to the specified name.
     *
     * @param name The specified name
     */
    public void setName(String name) {this.name = name;
    }

    /**
     * Returns the player user's elo.
     *
     * @return the player user's elo
     */
    public int getElo(){
        return this.elo;
    }

    /**
     * Sets the player user's elo to the specified elo.
     *
     * @param elo The specified elo
     */
    public void setElo(int elo) {this.elo = elo;}

    /**
     * Returns the player user's password.
     *
     * @return the player user's password
     */
    public String getPassword() {return password;}

    /**
     * Sets the player user's password to the specified password.
     *
     * @param password The specified password
     */
    public void setPassword(String password) {this.password = password;}


    public void updateElo(int new_elo){
        this.elo = new_elo;
    }
}
