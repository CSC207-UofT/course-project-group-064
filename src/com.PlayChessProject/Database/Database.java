package Database;


import Entities.PlayerUser;
import Exceptions.UserAlreadyExistsException;


public interface Database {

    public void createNewDatabase();

    public void addUserInfo(PlayerUser user, String password) throws UserAlreadyExistsException;

    public void deleteUserInfo(PlayerUser user);

    public boolean checkUserExistence(String username);

    public void updateUserPassword(PlayerUser user, String newPassword);

    public void updateUserElo(PlayerUser user, Integer newElo);

    public boolean checkUserPassword(String username, String password);

}
