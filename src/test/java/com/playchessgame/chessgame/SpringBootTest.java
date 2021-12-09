package com.playchessgame.chessgame;

import com.playchessgame.chessgame.ClassesInTestVersion.UserServiceImpl1TestVersion;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.UserService.MasterUserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@org.springframework.boot.test.context.SpringBootTest
public class SpringBootTest {

    @Autowired
    UserServiceImpl1TestVersion userService;

    @Autowired
    MasterUserService masterUserService;

    @Test
    public void RegisterUserAlreadyExistTest() {
        PlayerUser player = new PlayerUser("kaixinrongzi", "abcdef");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(player));
    }

    @Test
    public void RegisterUserNotExistTest() {
        PlayerUser player = new PlayerUser("kaixinrongzi99999", "abcdef");
        assertDoesNotThrow(() -> userService.addUser(player));

        // to delete the player for future test
        assertEquals("The player has been successfully deleted", masterUserService.deleteUser(player));
    }

    @Test
    public void LoginUserFailureTest() {
        PlayerUser player = new PlayerUser("Mary0218", "123456");
        assertFalse(userService.checkUserExistence(player));
    }

    @Test
    public void LoginUserSuccessTest() {
        PlayerUser player = new PlayerUser("kaixinrongzi", "123456");
        assertTrue(userService.checkUserExistence(player));
    }

    @Test
    public void resetPasswordSuccessTest() {
        PlayerUser player = new PlayerUser("Snow Zhou01", "999999");
        assertEquals("Your Password Has Been Reset Successfully!", masterUserService.resetPassword(player));
    }

    @Test
    public void resetPasswordFailureTest() {
        PlayerUser player = new PlayerUser("Liu Hua", "Ilove6");
        assertEquals("The username does not exist in the system.><", masterUserService.resetPassword(player));
    }

    @Test
    public void deletePlayerSuccessTest() {
        PlayerUser player = new PlayerUser("tiantian666", "666");
        assertEquals("The player has been successfully deleted", masterUserService.deleteUser(player));

        // to add back the player for future test
        assertDoesNotThrow(() -> userService.addUser(player));
    }

    @Test
    public void deletePlayerFailure1Test() {     // password is not correct
        PlayerUser player = new PlayerUser("qq", "1111");
        assertEquals("The player fails to be deleted, please try later", masterUserService.deleteUser(player));
    }

    @Test
    public void deletePlayerFailure2Test() {     // username does not exist
        PlayerUser player = new PlayerUser("tiantian66", "666");
        assertEquals("The username does not exist in the system.><", masterUserService.deleteUser(player));
    }

    @Test
    public void checkMateUpdateUserEloTest() {
        PlayerUser kaixinrongzi = new PlayerUser("kaixinrongzi", "123456");
        PlayerUser Mary = new PlayerUser("Mary", "999999");

        System.setProperty("java.awt.headless", "false");

        assertEquals("Checkmate! Both players are updated elo", userService.play(kaixinrongzi, Mary, "white", 2));
    }

    @Test
    public void staleMateUpdateUserEloTest() {
        PlayerUser kaixinrongzi = new PlayerUser("kaixinrongzi", "123456");
        PlayerUser Mary = new PlayerUser("Mary", "999999");

        System.setProperty("java.awt.headless", "false");

        assertEquals("Stalemate! Both players are updated elo", userService.play(kaixinrongzi, Mary, "white", 3));
    }

    @Test
    public void getPlayerByNameSuccessTest() {
        assertEquals("kaixinrongzi", masterUserService.getPlayerUserByName("kaixinrongzi").getName());
        assertEquals("123456", masterUserService.getPlayerUserByName("kaixinrongzi").getPassword());
    }

    @Test
    public void getPlayerByNameSuccessFailure() {
        assertNull(masterUserService.getPlayerUserByName("lemonTree"));
    }

}
