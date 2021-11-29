package com.playchessgame.chessgame.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MasterUser extends User {
    @Id
    private String name = "masterusername";
    @Column
    private String password = "masteruserpassword";
    @Column
    private int elo;
    /** The idea here is that there will only ever be one master user with a set username and password*/

    public MasterUser() {
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;
    }

    public void setElo(int elo) {this.elo = elo;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public int getElo(){
        return this.elo;
    }


}