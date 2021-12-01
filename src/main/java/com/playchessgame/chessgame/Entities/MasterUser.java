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
    /** The idea here is that there will only ever be one master user with a set username and password*/

    public MasterUser() {
    }

    /**
     * Returns the master user's username.
     *
     * @return the master user's name
     */
    public String getName() {return name;}

    /**
     * Sets the master user's username to the specified name.
     *
     * @param name The specified name
     */
    public void setName(String name) {this.name = name;
    }

    /**
     * Returns the master user's password.
     *
     * @return the master user's password
     */
    public String getPassword() {return password;}

    /**
     * Sets the master user's password to the specified password.
     *
     * @param password The specified password
     */
    public void setPassword(String password) {this.password = password;}
}