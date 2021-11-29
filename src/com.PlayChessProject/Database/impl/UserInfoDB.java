package Database.impl;

import java.sql.*;
import Database.Database;
import Entities.PlayerUser;

public class UserInfoDB implements Database {
    public static final String DATABASE_NAME = "userinfodatabase.db";
    public static final String TABLE_NAME = "userinformation";

    public UserInfoDB() {

    }

    /**
     * Connects to the database with the name of DATABASE_NAME.
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
     * Creates a SQLite database and table with the names of DATABASE_NAME and TABLE_NAME respectively.
     */
    @Override
    public void createNewDatabase() {
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();
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
     * Adds the specified player user's information into the SQLite database and table.
     *
     * @param user The user whose information is being inserted
     */
    @Override
    public void addUserInfo(PlayerUser user) {
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();
            String sql = "INSERT INTO " + TABLE_NAME + " (username,password,elo) " +
                    "VALUES (" + user.getName() + ", " + user.getPassword() + ", " + user.getElo() + " );";
            statement.executeUpdate(sql);
            conn.commit();
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Deletes the specified player user from the SQLite database and table.
     *
     * @param user The user who is being deleted from the SQLite database and table
     */
    @Override
    public void deleteUserInfo(PlayerUser user) {
        String sql = "DELETE FROM" + TABLE_NAME +  "WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            // set the corresponding param
            preparedStatement.setString(1, user.getName());
            // execute the delete statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Checks if the specified player user is already in the SQLite database and table.
     *
     * @param user
     *
     * @return A true or false value based on whether the user is already in the SQLite database and table
     */
    @Override
    public boolean checkUserExistence(PlayerUser user) {
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();

            String sql = "SELECT username FROM " + TABLE_NAME;
            try (ResultSet rs    = statement.executeQuery(sql)) {
                while (rs.next()) {
                    if (this.compareUsernames(user.getPassword(), rs.getString("username"))) {
                        return true;
                    }
                }
            }
            return false;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Determines if the two specified usernames are the same.
     *
     * @param username1 The first username being compared
     * @param username2 The second username being compared
     *
     * @return A true or false value based on whether the two usernames are the same
     */
    private Boolean compareUsernames(String username1, String username2) {
        return (username1.toLowerCase().equals(username2.toLowerCase()));
    }

    /**
     * Updates the specified player user's password to the specified new password.
     *
     * @param user The user whose password is being updated
     * @param newPassword The new password
     */
    @Override
    public void updateUserPassword(PlayerUser user, String newPassword) {
        String sql = "UPDATE " + TABLE_NAME + " SET password = ? "
                + "WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, newPassword);
            preparedStatement.setString(2, user.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates the specified player user's elo rating to the specified new elo rating.
     *
     * @param user The user whose elo rationg is being updated
     * @param newElo The new elo rating
     */
    public void updateUserElo(PlayerUser user, Integer newElo) {
        String sql = "UPDATE " + TABLE_NAME + " SET elo = ? "
                + "WHERE username = ?";

        try (Connection conn = this.connect();
             PreparedStatement preparedstatement = conn.prepareStatement(sql)) {

            preparedstatement.setInt(1, newElo);
            preparedstatement.setString(2, user.getName());
            preparedstatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkUserPassword(String username, String password){
        try(Connection conn = this.connect();) {
            Statement statement = conn.createStatement();

            String sql = "SELECT username FROM " + TABLE_NAME + " WHERE password = " + password;
            try (ResultSet rs    = statement.executeQuery(sql)) {
                if (rs != null) {
                    return true;
                }else{
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
