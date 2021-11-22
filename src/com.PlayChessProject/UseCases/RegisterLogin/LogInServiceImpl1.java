package UseCases.RegisterLogin;

import Database.Database;
import Database.impl.UserInfoDB2;
import Entities.User;

import java.util.List;

public class LogInServiceImpl1 implements LogInService{

    private UserInfoDB2 database;

    public LogInServiceImpl1(UserInfoDB2 database){
        this.database = database;
    }

    @Override
    public User getPlayerByName(String username) {
        return database.getUser(username);
    }

}
