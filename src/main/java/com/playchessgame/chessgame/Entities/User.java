package com.playchessgame.chessgame.Entities;

public class User{
    private String name;
    private String password;

    public User(){};

    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    public User(String username){
        this.name = username;
    }

    /**
     * Returns the user's username.
     *
     * @return the user's username
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the user's password.
     *
     * @return the user's password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Sets the user's username to the specified name.
     *
     * @param name The specified name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the user's password to the specified password.
     *
     * @param password The specified password
     */
    public void setPassword(String password){
        this.password = password;
    }
}
