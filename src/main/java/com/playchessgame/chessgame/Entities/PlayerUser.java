package com.playchessgame.chessgame.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class PlayerUser extends User {
    @Id
    private String name;
    @Column
    private String password;
    @Column
    private int elo;
    @Column
    private int kFactor;
    @Column
    private int games;

    public PlayerUser() {
        super();
        this.elo = 1000;
        this.kFactor = 50;
        this.games = 0;
    }

    public PlayerUser(String username){
        this.name = username;
        this.kFactor = 50;
        this.games = 0;
        this.elo = 1000;
    }

    public PlayerUser(String username, String password){
        super(username, password);
        this.kFactor = 50;
        this.games = 0;
        this.elo = 1000;
    }

    public PlayerUser(String username, String password, int elo){
        super(username, password);
        this.elo = elo;
        this.kFactor = 50;
        this.games = 0;
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
     * @param newElo The specified elo
     */
     public void setElo(int newElo) {
        this.elo = newElo;
        if (this.games==10){
            kFactor = 10;
        }
        this.games++;
    }

    /**
     * Returns the player user's K factor.
     *
     * @return the player user's K factor
     */
    public int getkFactor(){
        return this.kFactor;
    }
}
