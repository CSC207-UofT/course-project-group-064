package com.playchessgame.chessgame.Entities;

import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Entities.User;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

public class MasterUser extends User {

    private String name = "masterusername";
    private String password = "masteruserpassword";

    /** The idea here is that there will only ever be one master user with a set username and password*/

    public MasterUser() {
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;
    }

    public String getPassword() {return password;}


}