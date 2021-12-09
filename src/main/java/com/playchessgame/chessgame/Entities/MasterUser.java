package com.playchessgame.chessgame.Entities;


public class MasterUser extends User {

    /**
     * These variables are final so that there will only ever be one master user with a set username and password
     */
    private final String name = "masterusername";
    private final String password = "masteruserpassword";
}