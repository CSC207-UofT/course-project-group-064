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

    //TODO: do we need this?
//    public PlayerUser(String username, int elo){
//        this.name = username;
//        this.elo = elo;
//    }

//    public String getName() {return name;}
//
//    public void setName(String name) {this.name = name;
//    }
//
//    public String getPassword() {return password;}
//
//    public void setPassword(String password) {this.password = password;}

    public int getElo(){
        return this.elo;
    }

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
