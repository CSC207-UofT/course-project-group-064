package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class UserInfoDB {
    public static final String DATABASE_NAME = "userinfodatabase.db";
    public static final String TABLE_NAME = "userinformation";

    public UserInfoDB() {

    }

    /**
     * Connects to the database with the name of DATABASE_NAME
     *
     * @return the connection object
     */
    private Connection connect(){
        String url = "jdbc:sqlite:C:/sqlite/" + DATABASE_NAME;
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    /**
     * Creates a SQLite database and table with the names of DATABASE_NAME and TABLE_NAME respectively
     */
    public void createNewDatabase() {
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();
            /** add id part to table? or not needed?  */
            String sql = "CREATE TABLE" + TABLE_NAME + " (" +
                    "username" + " TEXT PRIMARY KEY, " +
                    "password" + " TEXT, " +
                    "elo" + " INTEGER" +
                    ");";
            statement.execute(sql);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds the specified user's information into the SQLite database and table
     *
     * @param user The user whose information is being inserted
     * @param password The user's password
     */
    public void addUserInfo(User user, String password) {
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();
            //TODO: check if user is already in table
            String sql = "INSERT INTO " + TABLE_NAME + " (username,password,elo) " +
                    "VALUES (" + user.getName() + ", " + password + ", " + user.getElo() + " );";
            statement.executeUpdate(sql);
            conn.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
