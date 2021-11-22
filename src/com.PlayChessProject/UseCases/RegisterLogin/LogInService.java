package UseCases.RegisterLogin;

import Entities.User;

import java.util.List;

public interface LogInService {

    public User getPlayerByName(String username);

}
