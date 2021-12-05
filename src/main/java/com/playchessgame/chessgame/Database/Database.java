package com.playchessgame.chessgame.Database;

import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;

public interface Database {

    /**
     * create a new database in the MongoDB
     */
    public void createNewDatabase();

    /**
     * Adds the specified player user's information into the database. Throws a UserAlreadyExistsException if the user
     * is already in the database.
     *
     * @param user The player whose information is being inserted.
     */
    public void addUserInfo(PlayerUser user) throws UserAlreadyExistsException;

    /**
     * Deletes the specified player use's information from the database.
     *
     * @param user The player whose information being deleted from the database
     */
    public void deleteUserInfo(PlayerUser user);

    /**
     * Checks if the specified player is already in the database (by looking at their username specifically).
     *
     *  @return A true or false value reflecting whether the player is in the database. True if they are, false if they aren't
     */
    public boolean checkUserExistence(PlayerUser user);

    /**
     * Updates the password of the specified player in the database to the specified new password.
     *
     * @param user The player whose password is being updated
     */
    public void updateUserPassword(PlayerUser user) throws UsernameDoesNotExist;

    /**
     * Updates the specified player user's elo rating to the specified new elo rating.
     *
     * @param user The user whose elo rationg is being updated
     * @param newElo The new elo rating
     */
    public void updateUserElo(PlayerUser user, Integer newElo);

    /**
     * check if the given password matches the one of the given user
     * @param user: the PlayerUser whose password needs to be compared to the database
     * @param password: the password provided by the Player
     * @return true if the given password matches th user's password stored in the MongoDB
     */
    public boolean checkUserPassword(PlayerUser user, String password);

    /**
     * return the PlayerUser matching the given username
     * @param username: the username of the PlayerUser
     * @return the PlayerUser matching the given username
     */
    public PlayerUser getPlayerUserByName(String username);

}
