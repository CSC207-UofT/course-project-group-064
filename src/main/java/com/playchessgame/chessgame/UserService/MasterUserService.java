package com.playchessgame.chessgame.UserService;

import com.playchessgame.chessgame.Entities.PlayerUser;

/**
 * An interface providing the service the MasterUser can provide
 */
public interface MasterUserService {

    /**
     * reset the password for the given PlayerUser
     *
     * @param user: PlayerUser who is applying to reset her password
     * @return the result to the MasterUser about the password reset
     */
    String resetPassword(PlayerUser user);

    /**
     * delete the given PlayerUser from the database
     *
     * @param user: the PlayerUser who is no long active for the game
     * @return the result to the MasterUser when the deletion is executed
     */
    String deleteUser(PlayerUser user);

    PlayerUser getPlayerUserByName(String username);

}
