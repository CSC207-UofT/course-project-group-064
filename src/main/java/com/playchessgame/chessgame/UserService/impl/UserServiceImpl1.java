package com.playchessgame.chessgame.UserService.impl;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import com.playchessgame.chessgame.GameService.GameGui;
import com.playchessgame.chessgame.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A use case class providing services for PlayerUser implementing UserService
 */
@Service
public class UserServiceImpl1 implements UserService {

    @Autowired
    private Database database;

    /**
     * Add an User to database
     * @param user: the PlayerUser who is applying to register for the game
     */
    @Override
    @Transactional
    public void addUser (PlayerUser user) throws UserAlreadyExistsException {
        this.database.addUserInfo(user);
    }

    /**
     * Check if the Player exists in the database
     * @param user: the PlayerUser of the game
     * @return true if the player exists or false if the player does not exist
     */
    @Override
    @Transactional
    public boolean checkUserExistence(PlayerUser user){
        return this.database.checkUserExistence(user);
    }

    public String play(PlayerUser user1, PlayerUser user2, String role) {

        String errorMsg = "";
        switch (role.toLowerCase()){
            case "white":
                // user1 is white and user2 is black
                try {GameGui.run(user1, user2, database);
                } catch (UsernameDoesNotExist e) {
                    errorMsg = user1.getName() + " is not in the system ><, we cannot update her elo";
                }
                break;

            case "black":
                // user1 is black and user2 is white
                try {GameGui.run(user2, user1, database);
                } catch (UsernameDoesNotExist e) {
                    errorMsg = user1.getName() + " is not in the system ><, we cannot update her elo";
                }
                break;
        }

        return errorMsg;

    }


}
