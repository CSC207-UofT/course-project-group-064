package database;

import java.sql.*;

import LogInUserCase.LoginUseCase;
import entity.User;

import LogInUserCase.LoginUseCase.*;

public class UserInfoDB implements Database{
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
    @Override
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
    @Override
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

    /**
     * returns if the user with <username><password> exists in the database
     * @param username
     * @param password
     * @return true if the user exists and false if not
     */
    @Override
    public int readUserInfo(String username, String password) {

        int res = -1;

        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();
            String readQuery1 = "SELECT * FROM " + TABLE_NAME + " WHERE (username = " + username + ")";
            String readQuery2 = "SELECT * FROM " + TABLE_NAME + " WHERE (username = " + username +
            " AND password = " + password + ")";

            ResultSet resultSet1 = statement.executeQuery(readQuery2);     // execute readQuery1
            ResultSet resultSet2 = statement.executeQuery(readQuery2);     // execute readQuery2

            if (resultSet1.wasNull()) {
                res =  0;
            }else if(resultSet2.wasNull()){
                res = 1;
            }else {
                res = 2;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res; // if res ==0, then there is either an error or the username has not been found

    }
}
