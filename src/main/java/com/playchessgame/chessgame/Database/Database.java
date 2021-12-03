package com.playchessgame.chessgame.Database;

import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;

public interface Database {

    public void createNewDatabase();

    public void addUserInfo(PlayerUser user) throws UserAlreadyExistsException;

    public void deleteUserInfo(PlayerUser user);

    public boolean checkUserExistence(PlayerUser user);

    public void updateUserPassword(PlayerUser user) throws UsernameDoesNotExist;

    public void updateUserElo(PlayerUser user, Integer newElo);

    public boolean checkUserPassword(PlayerUser user, String password);

    public PlayerUser getPlayerUserByName(String username);

}
