import database.UserInfoDB;
import game.User;
import org.junit.before;
import org.junit.test;

public class DatabaseTest {
    private UserInfoDB database;

    @Before
    public void setup() {
        database = new UserInfoDB();
    }

    @Test
    public void testDatabaseCreation() {
        database.createNewDatabase();
        //TODO: complete
    }

    @Test
    public void  testAddUserInfo() {
        User user = new User("testusername", 3);
        database.addUserInfo(user, "testpassword");

        assert(database.checkUserExistence(user));
        //TODO: complete
    }

    @Test
    public void  testDeleteUserInfo() {
        User user = new User("testusername", 3);
        database.addUserInfo(user, "testpassword");
        database.deleteUserInfo(user);

        assert(!database.checkUserExistence(user));
    }

    @Test
    public void  testCheckUserExistence() {
        User user = new User("testusername", 3);
        database.addUserInfo(user, "testpassword");

        assert(database.checkUserExistence(user));
        //TODO: complete
    }

    @Test
    public void  testUpdateUserPassword() {
        User user = new User("testusername", 3);
        database.addUserInfo(user, "testpassword");
        database.updateUserPassword(user, "newtestpassword");

        assert(false);
        //TODO: complete
    }

    @Test
    public void  testUpdateUserElo() {
        User user = new User("testusername", 3);
        database.addUserInfo(user, "testpassword");
        database.updateUserElo(user, 5);

        assert(false);
        //TODO: complete
    }
}
