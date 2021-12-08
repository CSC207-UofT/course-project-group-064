import com.playchessgame.chessgame.Database.impl.UserInfoDB2;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import org.junit.Test;
import static org.junit.Assert.*;

public class DatabaseTest {
    UserInfoDB2 database = new UserInfoDB2();
    @Test(timeout = 50)
    public void TestAddUserInfo() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer");
        database.addUserInfo(player);
        assertTrue(database.checkUserExistence(player));
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(expected = UserAlreadyExistsException.class, timeout = 50)
    public void TestAddUserInfoException() throws UserAlreadyExistsException {
        PlayerUser player = new PlayerUser("testplayer");
        database.addUserInfo(player);
        database.addUserInfo(player);
    }

    @Test(timeout = 50)
    public void TestDeleteUserInfo() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer");
        database.addUserInfo(player);
        database.deleteUserInfo(player);
        assertFalse(database.checkUserExistence(player));
    }

    @Test(expected = UsernameDoesNotExist.class, timeout = 50)
    public void TestDeleteUserInfoException() throws UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer");
        database.deleteUserInfo(player); //attempts to delete a player not already in the database
    }

    @Test(timeout = 50)
    public void TestUpdateUserPassword() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword");
        database.addUserInfo(player);
        player.setPassword("newpassword");
        database.updateUserPassword(player);
        assertEquals("newpassword", database.getPlayerUserByName(player.getName()).getPassword());
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(expected = UsernameDoesNotExist.class, timeout = 50)
    public void TestUpdateUserPasswordException() throws UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer");
        player.setPassword("newpassword");
        database.updateUserPassword(player); //attempts to update a player not already in the database
    }

    @Test(timeout = 50)
    public void TestUpdateUserElo() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        database.addUserInfo(player);
        database.updateUserElo(player, 1500);
        assertEquals(1500, database.getPlayerUserByName(player.getName()).getElo());
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(expected = UsernameDoesNotExist.class, timeout = 50)
    public void TestUpdateUserEloException() throws UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        database.updateUserElo(player, 1500); //attempts to update a player not already in the database
    }

    @Test(timeout = 50)
    public void TestGetPlayerUserByName() throws UserAlreadyExistsException, UsernameDoesNotExist {
        PlayerUser player = new PlayerUser("testplayer", "testpassword", 1000);
        database.addUserInfo(player);
        assertEquals(player, database.getPlayerUserByName(player.getName()));
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(expected = UsernameDoesNotExist.class, timeout = 50)
    public void TestGetPlayerUserByNameException() throws UsernameDoesNotExist {
        database.getPlayerUserByName("testplayer"); //attempts to get a player not in the database
    }
}