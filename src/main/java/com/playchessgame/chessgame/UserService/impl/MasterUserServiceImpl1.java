package com.playchessgame.chessgame.UserService.impl;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A use case class providing services for MasterUser implementing MasterUserService
 */
@Service
public class MasterUserServiceImpl1 implements MasterUserService {

    private final String PASSWORD_RESET_SUCCESS = "Your Password Has Been Reset Successfully!";
    private final String PASSWORD_RESET_FAIL = "Your Password Fails to be Reset... Please Try Again!";
    private final String DELETE_USER_SUCCESS = "The player has been successfully deleted";
    private final String DELETE_USER_FAIL = "The player fails to be deleted, please try later";

    @Autowired
    private Database database;

    /**
     * reset the password for the given PlayerUser
     * @param user: PlayerUser who is applying to reset her password
     * @return the result to the MasterUser about the password reset
     */
    @Override
    @Transactional
    public String resetPassword(PlayerUser user){

        try {this.database.updateUserPassword(user);
            return PASSWORD_RESET_SUCCESS;
        }catch (UsernameDoesNotExist e){
            return e.getMessage();
        }catch (Exception e){
            return PASSWORD_RESET_FAIL;
        }

    };

    /**
     * delete the given PlayerUser from the database
     * @param user: the PlayerUser who is no long active for the game
     * @return the result to the MasterUser when the deletion is executed
     */
    @Override
    @Transactional
    public String deleteUser(PlayerUser user){
        try {this.database.deleteUserInfo(user);
            // check if the user is still in database
            try {
                PlayerUser player = database.getPlayerUserByName(user.getName());
                return DELETE_USER_FAIL;
            }catch (UsernameDoesNotExist e){
                return DELETE_USER_SUCCESS;
            }
        }catch (UsernameDoesNotExist e){
            return e.getMessage();
        }
    };
}
