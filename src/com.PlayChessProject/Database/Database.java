package Database;


import Entities.PlayerUser;
import Exceptions.UserAlreadyExistsException;
import Exceptions.UserDoesNotExistException;


public interface Database {
    /**
     * Creates a new database in the MongoDB
     */
    public void createNewDatabase();

    /**
     * Adds the specified player user's information into the database. Throws a UserAlreadyExistsException if the user
     * is already in the database.
     *
     * @param user The user whose information is being inserted.
     * @param password The password of the user.
     */
    public void addUserInfo(PlayerUser user, String password) throws UserAlreadyExistsException;

    /**
     * Deletes the specified player user's information from the database. Throws a UserDoesNotExistException if the user
     * is not in the database.
     *
     * @param user The user whose information is being deleted from the database
     */
    public void deleteUserInfo(PlayerUser user) throws UserDoesNotExistException;

    /**
     * Checks if the specified player user is already in the database (by looking at their username specifically).
     *
     * @param username The user whose existence is being checked
     *
     * @return A true or false value reflecting whether the user is in the database. True if they are, false if they aren't
     */
    public boolean checkUserExistence(String username);

    /**
     * Updates the password of the specified player user in the database to the specified new password. Throws a 
     * UserDoesNotExistException if the user is not in the database.
     *
     * @param user The player whose password is being updated
     * @param newPassword The player's new password
     */
    public void updateUserPassword(PlayerUser user, String newPassword) throws UserDoesNotExistException;

    /**
     * Updates the Elo rating of the specified player in the database to the specified new Elo rating. Throws a 
     * UserDoesNotExistException if the user is not in the database.
     *
     * @param user The player whose Elo rating is being updated
     * @param newElo The player's new Elo rating
     */
    public void updateUserElo(PlayerUser user, Integer newElo) throws UserDoesNotExistException;

    /**
     * check if the given password matches the one of the given user
     * @param user: the PlayerUser whose password needs to be compared to the database
     * @param password: the password provided by the Player
     * 
     * @return true if the given password matches th user's password stored in the MongoDB
     */
    public boolean checkUserPassword(String username, String password);

    /**
     * Returns the PlayerUser matching the given username. Throws a UserDoesNotExistException if the user 
     * is not in the database.
     *
     * @param username the username of the PlayerUser

     * @return the PlayerUser matching the given username
     */
    public PlayerUser getPlayerUser(String username) throws UserDoesNotExistException;
}
