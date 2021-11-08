package database;


import Exceptions.UserAlreadyExistsException;
import entity.User;

import java.nio.file.attribute.UserPrincipal;

public interface Database {

    public void createNewDatabase();

    public void addUserInfo(User user, String password) throws UserAlreadyExistsException;

    public int readUserInfo(String username, String password);

}
