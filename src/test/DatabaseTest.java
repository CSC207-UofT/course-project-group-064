
import ClassesInTestVersion.UserInfoDB2;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {
    UserInfoDB2 database = new UserInfoDB2();

    @Test
    public void TestAddUserInfo() {
        PlayerUser player = new PlayerUser("testplayer");
        assertDoesNotThrow(() -> database.addUserInfo(player));
        assertTrue(database.checkUserExistence(player));
        assertDoesNotThrow(() -> database.deleteUserInfo(player)); //removes the player made for the purpose of the test from the database
        assertFalse(database.checkUserExistence(player));
    }

    @Test
    public void TestAddUserInfoException() {
        PlayerUser player = new PlayerUser("testplayer");
        assertDoesNotThrow(() -> database.addUserInfo(player));
        assertThrows(UserAlreadyExistsException.class, () -> database.addUserInfo(player));
        assertDoesNotThrow(() -> database.deleteUserInfo(player)); //removes the player made for the purpose of the test from the database
        assertFalse(database.checkUserExistence(player));
    }

    @Test
    public void TestDeleteUserInfo() {
        PlayerUser player = new PlayerUser("testplayer");
        assertDoesNotThrow(() -> database.addUserInfo(player));
        assertDoesNotThrow(() -> database.deleteUserInfo(player));
        assertFalse(database.checkUserExistence(player));
    }

    @Test
    public void TestDeleteUserInfoException() {
        PlayerUser player = new PlayerUser("testplayer");
        assertThrows(UsernameDoesNotExist.class, () -> database.deleteUserInfo(player)); //attempts to delete a player not already in the database
    }

    @Test
    public void TestUpdateUserPassword() throws UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword");
        assertDoesNotThrow(() -> database.addUserInfo(player));
        player.setPassword("newpassword");
        assertDoesNotThrow(() -> database.updateUserPassword(player));
        assertEquals("newpassword", database.getPlayerUserByName(player.getName()).getPassword());
        assertDoesNotThrow(() -> database.deleteUserInfo(player)); //removes the player made for the purpose of the test from the database
        assertFalse(database.checkUserExistence(player));
    }

    @Test
    public void TestUpdateUserPasswordException() {
        PlayerUser player = new PlayerUser("testplayer");
        player.setPassword("newpassword");
        assertThrows(UsernameDoesNotExist.class, () -> database.updateUserPassword(player)); //attempts to update a player not already in the database
    }

    @Test
    public void TestUpdateUserElo() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        database.addUserInfo(player);
        database.updateUserElo(player, 1500);
        PlayerUser playerFromDB = database.getPlayerUserByName(player.getName());
        assertEquals(1500, playerFromDB.getElo());
        assertDoesNotThrow(() -> database.deleteUserInfo(player)); //removes the player made for the purpose of the test from the database
        assertFalse(database.checkUserExistence(player));
    }

    //@Test(expected = UsernameDoesNotExist.class)
    @Test
    public void TestUpdateUserEloException() {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        assertThrows(UsernameDoesNotExist.class, () -> database.updateUserElo(player, 1500)); //attempts to update a player not already in the database
    }

    @Test
    public void TestGetPlayerUserByName() throws UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        assertDoesNotThrow(() -> database.addUserInfo(player));
        PlayerUser playerFromDB = database.getPlayerUserByName(player.getName());
        assertEquals(player.getName(), playerFromDB.getName());
        assertDoesNotThrow(() -> database.deleteUserInfo(player));  //removes the player made for the purpose of the test from the database
        assertFalse(database.checkUserExistence(player));
    }

    @Test
    public void TestGetPlayerUserByNameException() {
        assertThrows(UsernameDoesNotExist.class, () -> database.getPlayerUserByName("testplayer")); //attempts to get a player not in the database
    }
}