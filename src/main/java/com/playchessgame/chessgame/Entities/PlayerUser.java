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

    public PlayerUser(String username, String password){
        super(username, password);
        this.kFactor = 50;
        this.games = 0;
    }

    public PlayerUser() {
        super();
    }

    public PlayerUser(String username, String password, int elo){
        super(username, password);
        this.elo = elo;
    }

//    /**
//     * Returns the player user's username.
//     *
//     * @return the player user's name
//     */
//    @Override
//    public String getName() {return name;}
//
//    /**
//     * Sets the player user's username to the specified name.
//     *
//     * @param name The specified name
//     */
//    public void setName(String name) {this.name = name;
//    }

//    /**
//     * Returns the player user's password.
//     *
//     * @return the player user's password
//     */
//    public String getPassword() {return password;}

//    /**
//     * Sets the player user's password to the specified password.
//     *
//     * @param password The specified password
//     */
//    public void setPassword(String password) {this.password = password;}
  
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


    public int getkFactor(){
        return this.kFactor;
    }
}
