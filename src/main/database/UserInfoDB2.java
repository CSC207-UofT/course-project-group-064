package database;

import Exceptions.UserAlreadyExistsException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import entity.User;
import org.bson.Document;

import java.util.Scanner;


public class UserInfoDB2 implements Database{

    public static void main(String[] args) throws UserAlreadyExistsException {

//        // connect to mongoDB
//        String uri = "mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net/test";
//        MongoClientURI mongoClientURI = new MongoClientURI(uri);
//        MongoClient mongoClient = new MongoClient(mongoClientURI);
//
//        // get into the MongoDB database
//        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
//        MongoCollection mongoCollection = mongoDatabase.getCollection("ChessGameUsers");
//
//        // create a document
//        Document document = new Document();
//        document.append("name", "Jason");
//        document.append("password", "123456");
//        document.append("elo", 55);
//
//        // insert the document to the mongodb
//        mongoCollection.insertOne(document);
//
//        // find data
//        Document found = (Document) mongoCollection.find(new Document("name", "Jason")).first();

        UserInfoDB2 userInfoDB2 = new UserInfoDB2();

        // create a mongoDB collection
        // userInfoDB2.createNewDatabase();

        // add user into it
        Scanner reader = new Scanner(System.in);

        while (true) {

            System.out.println("Enter your username or 0 to exist");
            String username = reader.nextLine();

            if (username.equals("0")){break;}

            System.out.println("Enter your password ");
            String password = reader.nextLine();

            userInfoDB2.addUserInfo(new User(username, "0"), password);

        }

        // read user from it
        while(true) {
            System.out.println("Enter your username or 0 to exist");
            String username2 = reader.nextLine();

            if (username2.equals("0")){break;}

            System.out.println("Enter your password ");
            String password2 = reader.nextLine();

            int res = userInfoDB2.readUserInfo(username2, password2);
            System.out.println(res);
        }

    }

    // get connection to mongodb
    private MongoClient connect(){
        String uri = "mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net/test";
        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);
        System.out.println("MongoDB connected");
        return mongoClient;
    }

    private MongoCollection getCollection(){
        MongoClient mongoClient = connect();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");

        return mongoDatabase.getCollection("ChessGameUsers");

    }

    @Override
    //TODO: should we pass the argument of the collection name?
    public void createNewDatabase() {
        MongoClient mongoClient = connect();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("MongoDB");
        mongoDatabase.createCollection("ChessGameUsers");

        System.out.println("Collection created successfully");
    }

    @Override
    public void addUserInfo(User user, String password) throws UserAlreadyExistsException {

        MongoCollection mongoCollection = getCollection();

        // create the document
        Document document = new Document("name", user.getName());

        // check if the document with the same username exists already
        int res = readUserInfo(user.getName(), password);

        if (res != -1){
            // the user with the username already exists
            throw new UserAlreadyExistsException();
        }

        String name = user.getName();
        int elo = user.getElo();

        document.append("name", name);
        document.append("password", password);
        document.append("elo", elo);

        // insert the document to the mongodb
        mongoCollection.insertOne(document);

        System.out.println("The User Was Inserted Successfully!");

    }

    @Override
    public int readUserInfo(String username, String password) {

        int res = -1;

        MongoCollection mongoCollection = getCollection();

        Document found = (Document) mongoCollection.find(new Document("name", username)).first();

        if (found == null) {
            res = -1;           // no matched user info.
        }else {
            if (found.get("password").equals(password)){
                res = 2;        // the password not match
            }else {res = 1;}    // the password match
        }

        return res;
    }
}
