package com.playchessgame.chessgame.UserService;

import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;

public interface UserService {

    /**
     * Add a User to database
     */
    void addUser(PlayerUser user) throws UserAlreadyExistsException;

    /**
     * Check if the User exists in the database
     * @param user: a user of the game
     * @return true if the user exists or false if the user does not exist
     */
    boolean checkUserExistence(PlayerUser user);

    String play(PlayerUser user1, PlayerUser user2, String role);

}
