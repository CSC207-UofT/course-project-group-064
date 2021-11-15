package Database;


import Exceptions.UserAlreadyExistsException;
import Entities.User;

public interface Database {

    public void createNewDatabase();

    public void addUserInfo(User user, String password) throws UserAlreadyExistsException;

    public void deleteUserInfo(User user);

    public boolean checkUserExistence(String username);

    public void updateUserPassword(User user, String newPassword);

    public void updateUserElo(User user, Integer newElo);

    public boolean checkUserPassword(String username, String password);

}
