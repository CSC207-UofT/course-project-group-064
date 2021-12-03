package com.playchessgame.chessgame.Entities;

public class User{
    private String name;
    private String password;

    public User(){};

    public User(String username, String password){
        this.name = username;
        this.password = password;
    }

    //TODO: do we need this constructor?
    public User(String username){
        this.name = username;
    }

    public String getName(){
        return this.name;
    }

    public String getPassword(){
        return this.password;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }


//    public boolean passwordMatch(String password) {return this.password.matches(password);}

}
