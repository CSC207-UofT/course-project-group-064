import Database.UserInfoDB;
import Entities.PlayerUser;

public class BoardTest {
    UserInfoDB database = new UserInfoDB2();
    @Test(timeout = 50)
    public void TestAddUserInfo(){
        PlayerUser player = new PlayerUser("testplayer");
        database.addUser(player)
        assertTrue(database.checkUserExistence(player));
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(timeout = 50)
    public void TestDeleteUserInfo(){
        PlayerUser player = new PlayerUser("testplayer");
        database.addUser(player)
        database.deleteUserInfo(player);
        assertFalse(database.checkUserExistence(player));
    }

    @Test(timeout = 50)
    public void TestUpdateUserPassword(){
        PlayerUser player = new PlayerUser("testplayer", "testpassword");
        database.addUser(player);
        database.updateUserPassword(player, "newpassword");
        assertEquals("newpassword", database.getPlayerUserByName(player.getName()).getPassword());
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }

    @Test(timeout = 50)
    public void TestUpdateUserElo(){
        PlayerUser player = new PlayerUser("testplayer", 1000);
        database.addUser(player);
        database.updateUserElo(player, 1500);
        assertEquals(1500, database.getPlayerUserByName(player.getName()).getElo());
        database.deleteUserInfo(player); //removes the player made for the purpose of the test from the database
    }
}