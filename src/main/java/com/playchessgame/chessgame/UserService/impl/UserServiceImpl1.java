package com.playchessgame.chessgame.UserService.impl;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Management Class
 */
@Service
public class UserServiceImpl1 implements UserService {

    @Autowired
    private Database database;

    /**
     * Add an User to database
     * @param user
     */
    @Override
    @Transactional
    public void addUser (PlayerUser user) throws UserAlreadyExistsException {
        this.database.addUserInfo(user);
    }

    /**
     * Check if the User exists in the database
     * @param user: a user of the game
     * @return true if the user exists or false if the user does not exist
     */
    @Override
    @Transactional
    public boolean checkUserExistence(PlayerUser user){
        return this.database.checkUserExistence(user);
    }
}
