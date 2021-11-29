package Database.impl;

import Database.Database;
import Entities.PlayerUser;
import Exceptions.UserAlreadyExistsException;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

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

    // get connection to mongodb
    private MongoClient connect(){

        return (MongoClient) applicationContext.getBean("mongoclient");
    }

    private MongoCollection getCollection(){

        MongoClient mongoClient = connect();

        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        MongoCollection mongoCollection = mongoDatabase.getCollection("ChessGameUsers");

        return mongoCollection;

    }

    @Override
    //TODO: should we pass the argument of the collection name?
    public void createNewDatabase() {
        MongoClient mongoClient = connect();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        mongoDatabase.createCollection("ChessGameUsers");

        System.out.println("Collection created successfully");
    }


    /**
     * Add an User to database: an User instance
     * @param user
     */
    @Override
    public void addUserInfo(PlayerUser user) throws UserAlreadyExistsException {

        if (checkUserExistence(user)){
            throw new UserAlreadyExistsException();
        }

        MongoCollection mongoCollection = getCollection();

        Document document = new Document();
        document.append("name", user.getName());
        document.append("password", user.getPassword());
        document.append("elo", 0);

        mongoCollection.insertOne(document);

        System.out.println("The User Was Inserted Successfully!");

    }

    @Override
    public void deleteUserInfo(PlayerUser user) {

    }

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

    @Override
    public void updateUserPassword(PlayerUser user, String newPassword) {

    }

    @Override
    public void updateUserElo(PlayerUser user, Integer newElo) {

    }

    @Override
    public boolean checkUserPassword(String username, String password) {
        MongoClient mongoClient = connect();

        MongoDatabase database = mongoClient.getDatabase("MongoDB");
        MongoCollection collection = database.getCollection("ChessGameUsers");

        Document document = new Document("name", username);
        document.append("password", password);

        Object res = collection.find(document).first();

        if (res != null) {
            // the username exists
            return true;
        }

        // the username does not exist
        return false;
    }

    public PlayerUser getUser(String username){
        MongoCollection mongoCollection = getCollection();

        Document document = new Document("username", username);
        FindIterable<Document> results = mongoCollection.find(document);

        String password = "";
        int elo = 0;
        for (Document res: results) {
            password = (String) res.get("password");
            elo = Integer.valueOf((String) res.get("elo"));
        }

        //TODO: to implement
        return new PlayerUser();

    }

}
