package com.playchessgame.chessgame.Database.impl;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
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
    private FindIterable result;

    @Autowired
    private ApplicationContext applicationContext;

//    public static void main(String[] args) throws UserAlreadyExistsException {
//
////        // connect to mongoDB
////        String uri = "mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net/test";
////        MongoClientURI mongoClientURI = new MongoClientURI(uri);
////        MongoClient mongoClient = new MongoClient(mongoClientURI);
////
////        // get into the MongoDB database
////        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
////        MongoCollection mongoCollection = mongoDatabase.getCollection("ChessGameUsers");
////
////        // create a document
////        Document document = new Document();
////        document.append("name", "Jason");
////        document.append("password", "123456");
////        document.append("elo", 55);
////
////        // insert the document to the mongodb
////        mongoCollection.insertOne(document);
////
////        // find data
////        Document found = (Document) mongoCollection.find(new Document("name", "Jason")).first();
//
//        UserInfoDB2 userInfoDB2 = new UserInfoDB2();
//
//        // create a mongoDB collection
//        // userInfoDB2.createNewDatabase();
//
//        // add user into it
//        Scanner reader = new Scanner(System.in);
//
//        while (true) {
//
//            System.out.println("Enter your username or 0 to exist");
//            String username = reader.nextLine();
//
//            if (username.equals("0")){break;}
//
//            System.out.println("Enter your password ");
//            String password = reader.nextLine();
//
//            userInfoDB2.addUserInfo(new User(username, "0"), password);
//
//        }
//
//        // read user from it
//        while(true) {
//            System.out.println("Enter your username or 0 to exist");
//            String username2 = reader.nextLine();
//
//            if (username2.equals("0")){break;}
//
//            System.out.println("Enter your password ");
//            String password2 = reader.nextLine();
//
//            int res = userInfoDB2.readUserInfo(username2, password2);
//            System.out.println(res);
//        }
//
//    }

    /**
     *
     * @return MongoClient which is the connection to MongoDB
     */
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
     * Returns a new MongoDB databse
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
     * Deletes the specified player use's information from the database.
     *
     * @param user The user whose information being deleted from the database
     */
    @Override
    public void deleteUserInfo(PlayerUser user) {
        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        mongoCollection.deleteOne(filter);
    }

    /**
     * Checks if the specified player user is already in the database (by looking at their username specifically).
     *
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
     * Updates the password of the specified player in the database to the specified new password.
     *
     * @param user The user whose password is being updated
     */
    @Override
    public void updateUserPassword(PlayerUser user) throws UsernameDoesNotExist{

        MongoCollection mongoCollection = getCollection();

        checkUserNameExistence(user);
        Bson username = eq("name", user.getName());
        Bson password = eq("password", user.getPassword());
        mongoCollection.updateOne(username, password);

    }

    /**
     * Updates the Elo rating of the specified player in the database to the specified new Elo rating.
     *
     * @param user The user whose Elo rating is being updated
     * @param newElo The new Elo rating
     */
    @Override
    public void updateUserElo(PlayerUser user, Integer newElo) {

        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        Bson eloUpdate = set("elo", newElo);

        mongoCollection.updateOne(filter, eloUpdate);
    }

    /**
     * check if the given password matches the one of the given user
     * @param user
     * @param password
     * @return true if the given password matches th user's password stored in the MongoDB
     */
    @Override
    public boolean checkUserPassword(PlayerUser user, String password) {

        MongoCollection mongoCollection = getCollection();

        Document document = new Document("name", user.getName());
        document.append("password", password);

        Object res = mongoCollection.find(document).first();

        if (res != null) {
            // the username exists
            return true;
        }

        // the username does not exist
        return false;
    }

    /**
     * return the playeruser matching the given username
     * @param username
     * @return the playeruser matching the given username
     */
    @Override
    public PlayerUser getPlayerUserByName(String username){
        MongoCollection mongoCollection = getCollection();

        Document document = new Document("name", username);
        FindIterable<Document> results = mongoCollection.find(document);

        String password = "";
        int elo = 0;
        for (Document res: results) {
            password = (String) res.get("password");
            elo = Integer.valueOf((String) res.get("elo"));
        }

        return new PlayerUser();

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
