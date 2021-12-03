package com.playchessgame.chessgame.UserService;

import com.playchessgame.chessgame.Entities.PlayerUser;

import java.util.Map;

public interface MasterUserService {

    public String resetPassword(PlayerUser user);

    public String deleteUser(PlayerUser user);

}
