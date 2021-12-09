package com.playchessgame.chessgame.ClassesInTestVersion;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the UserServiceImpl1 class in test version
 */
@Service
public class UserServiceImpl1TestVersion{

    @Autowired
    private Database database;

    /**
     * Add an User to database
     * @param user: the PlayerUser who is applying to register for the game
     */

    @Transactional
    public void addUser (PlayerUser user) throws UserAlreadyExistsException {
        this.database.addUserInfo(user);
    }

    /**
     * Check if the Player exists in the database
     * @param user: the PlayerUser of the game
     * @return true if the player exists or false if the player does not exist
     */

    @Transactional
    public boolean checkUserExistence(PlayerUser user){
        return this.database.checkUserExistence(user);
    }

    public String play(PlayerUser user1, PlayerUser user2, String role, int testMoveRes) {

        String errorMsg = "elo have been updated for both players";
        switch (role.toLowerCase()){
            case "white":
                // user1 is white and user2 is black
                try {
                    GameGuiTestVersion.run(user1, user2, database, testMoveRes);
                } catch (UsernameDoesNotExist e) {
                    errorMsg = user1.getName() + " is not in the system ><, we cannot update her elo";
                }
                break;

            case "black":
                // user1 is black and user2 is white
                try {GameGuiTestVersion.run(user2, user1, database, testMoveRes);
                } catch (UsernameDoesNotExist e) {
                    errorMsg = user1.getName() + " is not in the system ><, we cannot update her elo";
                }
                break;
        }

        return errorMsg;

    }

}


