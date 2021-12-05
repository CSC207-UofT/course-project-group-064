package com.playchessgame.chessgame.UserService.impl;

import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

/**
 * A use case class providing services for MasterUser implementing MasterUserService
 */
@Service
public class MasterUserServiceImpl1 implements MasterUserService {

    private final String PASSWORD_RESET_SUCCESS = "Your Password Has Been Reset Successfully!";
    private final String PASSWORD_RESET_FAIL = "Your Password Fails to be Reset... Please Try Again!";

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

//        try {this.database.updateUserPassword(user);
//
//
//            return PASSWORD_RESET_SUCCESS;
//        }catch (UsernameDoesNotExist e){
//            return e.getMessage();
//        }catch (Exception e){
//            return PASSWORD_RESET_FAIL;
//        }
//        receiveEmail("To Update Password", message);

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
            return "The User Has Been Deleted Successfully!";
        }catch (Exception e){
            return "The User Fails to be Deleted...";
        }
    };
}
