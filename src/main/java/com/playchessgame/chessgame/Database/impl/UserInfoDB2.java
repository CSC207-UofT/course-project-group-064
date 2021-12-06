package com.playchessgame.chessgame.Database.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.playchessgame.chessgame.Database.Database;
import com.playchessgame.chessgame.Entities.PlayerUser;
import com.playchessgame.chessgame.Exceptions.UserAlreadyExistsException;
import com.playchessgame.chessgame.Exceptions.UsernameDoesNotExist;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

@Repository
public class UserInfoDB2 implements Database {

    @Autowired
    private ApplicationContext applicationContext;

    private MongoClient connect(){
        return (MongoClient) applicationContext.getBean("mongoclient");
    }

    private MongoCollection getCollection(){
        MongoClient mongoClient = connect();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        MongoCollection mongoCollection = mongoDatabase.getCollection("ChessGameUsers");
        return mongoCollection;
    }

    /**
     * create a new database in the MongoDB
     */
    @Override
    public void createNewDatabase() {
        MongoClient mongoClient = connect();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        mongoDatabase.createCollection("ChessGameUsers");

        System.out.println("Collection created successfully");
    }


    /**
     * Adds the specified player user's information into the database. Throws a UserAlreadyExistsException if the user
     * is already in the database.
     *
     * @param user The player whose information is being inserted.
     * @param user The user whose information is being inserted.
     */
    @Override
    public void addUserInfo(PlayerUser user) throws UserAlreadyExistsException {

        try {checkUserNameExistence(user);
            System.out.println("The Username Has Been Used!");
            throw new UserAlreadyExistsException();}
        catch(UsernameDoesNotExist e){
            MongoCollection mongoCollection = getCollection();

            Document document = new Document();
            document.append("name", user.getName());
            document.append("password", user.getPassword());
            document.append("elo", 0);
            document.append("master", 0);

            mongoCollection.insertOne(document);

            System.out.println("The User Was Inserted Successfully!");
        }

    }

    /**
     * Deletes the specified player user's information from the database. Throws a UsernameDoesNotExist Exception if the
     * user is not in the database.
     *
     * @param user The player whose information is being deleted from the database
     */
    @Override
    public void deleteUserInfo(PlayerUser user) throws UsernameDoesNotExist{

        boolean res = checkUserNameExistence(user);

        if (!res){
            // the user with the username is not in the database
            throw new UsernameDoesNotExist();
        }

        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        Bson filter2 = eq("password", user.getPassword());

        mongoCollection.deleteOne(Filters.and(filter, filter2));

    }

    /**
     * Checks if the specified player user is already in the database (by looking at their username specifically).
     *
     * @param user The player whose existence is being checked
     *
     * @return A true or false value reflecting whether the user is in the database. True if they are, false if they aren't
     */
    @Override
    public boolean checkUserExistence(PlayerUser user) {

        MongoCollection collection = getCollection();

        Document document = new Document("name", user.getName());
        document.append("password", user.getPassword());

        Object res = collection.find(document).first();

        if (res != null) {
            // the username exists
            return true;
        }

        // the username does not exist
        return false;

    }

    /**
     * Updates the password of the specified player user in the database to the specified new password. Throws a
     * UsernameDoesNotExist Exception if the user is not in the database.
     *
     * @param user The player whose password is being updated
     */
    @Override
    public void updateUserPassword(PlayerUser user) throws UsernameDoesNotExist{

        MongoCollection mongoCollection = getCollection();

        checkUserNameExistence(user);

        Bson username = eq("name", user.getName());
        Bson password = set("password", user.getPassword());
        mongoCollection.updateOne(username, password);
    }

    /**
     * Updates the Elo rating of the specified player in the database to the specified new Elo rating. Throws a
     * UsernameDoesNotExist Exception if the user is not in the database.
     *
     * @param user The player whose Elo rating is being updated
     * @param newElo The player's new Elo rating
     */
    @Override
    public void updateUserElo(PlayerUser user, Integer newElo) throws UsernameDoesNotExist {
        boolean res = checkUserNameExistence(user);

        if (!res){
            // the user with the username is not in the database
            throw new UsernameDoesNotExist();
        }

        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        Bson eloUpdate = set("elo", newElo);

        mongoCollection.updateOne(filter, eloUpdate);
    }

    /**
     * check if the given password matches the one of the given user
     * @param user: the PlayerUser whose password needs to be compared to the database
     * @return true if the given password matches th user's password stored in the MongoDB
     */
    @Override
    public boolean checkUserPassword(PlayerUser user) {

        MongoCollection mongoCollection = getCollection();

        Document document = new Document("name", user.getName());
        document.append("password", user.getPassword());

        Object res = mongoCollection.find(document).first();

        if (res != null) {
            // the username exists
            return true;
        }

        // the username does not exist
        return false;
    }

    /**
     * return the PlayerUser matching the given username
     * @param username: the username of the PlayerUser
     * @return the PlayerUser matching the given username
     */
    @Override
    public PlayerUser getPlayerUserByName(String username) throws UsernameDoesNotExist{

        MongoCollection mongoCollection = getCollection();

        Document document = new Document("name", username);
        Document res = (Document)mongoCollection.find(document).first();

        if (res == null) {
            throw new UsernameDoesNotExist();
        }

        String password = (String) res.get("password");
        int elo = Integer.valueOf((String) res.get("elo"));

        return new PlayerUser(password, password, elo);

    }

    private boolean checkUserNameExistence(PlayerUser user) throws UsernameDoesNotExist{
        MongoCollection mongoCollection = getCollection();
        Document document = new Document();
        document.append("name", user.getName());
        Object res = mongoCollection.find(document).first();

        if (res != null){
            return true;
        }

        throw new UsernameDoesNotExist();
    }

}