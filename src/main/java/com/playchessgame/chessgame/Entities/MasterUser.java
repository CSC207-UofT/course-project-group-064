package com.playchessgame.chessgame.Entities;
public class MasterUser extends User {
    private String name = "masterusername";
    private String password = "masteruserpassword";
    /** The idea here is that there will only ever be one master user with a set username and password*/

    public MasterUser() {
    }
}