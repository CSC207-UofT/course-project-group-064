package Database;

import Entities.PlayerUser;
import Exceptions.UserAlreadyExistsException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserInfoDB2 implements Database{

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

    /**
     * Adds the specified player user's information into the database. Throws a UserAlreadyExistsException if the user
     * is already in the database.
     *
     * @param user The user whose information is being inserted.
     * @param password The password of the user.
     */
    @Override
    public void addUserInfo(PlayerUser user, String password) throws UserAlreadyExistsException {

        MongoCollection mongoCollection = getCollection();

        // create the document
        Document document = new Document("name", user.getName());

        // check if the document with the same username exists already
        boolean res = checkUserExistence(user.getName());

        if (res){
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

    /**
     * Deletes the specified player user's information from the database. Throws a UserDoesNotExistException if the user
     * is not in the database.
     *
     * @param user The user whose information is being deleted from the database
     */
    @Override
    public void deleteUserInfo(PlayerUser user) throws UserDoesNotExistException {
        boolean res = checkUserExistence(user.getName());

        if (!res){
            // the user with the username is not in the database
            throw new UserDoesNotExistException();
        }

        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        mongoCollection.deleteOne(filter);
    }

    /**
     * Checks if the specified player user is already in the database (by looking at their username specifically).
     *
     * @param username The user whose existence is being checked
     *
     * @return A true or false value reflecting whether the user is in the database. True if they are, false if they aren't
     */
    @Override
    public boolean checkUserExistence(String username) {
        MongoClient mongoClient = connect();

        MongoDatabase database = mongoClient.getDatabase("MongoDB");
        MongoCollection collection = database.getCollection("ChessGameUsers");

        Document document = new Document("name", username);

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
     * UserDoesNotExistException if the user is not in the database.
     *
     * @param user The player whose password is being updated
     * @param newPassword The player's new password
     */
    @Override
    public void updateUserPassword(PlayerUser user, String newPassword) throws UserDoesNotExistException {
        boolean res = checkUserExistence(user.getName());

        if (!res){
            // the user with the username is not in the database
            throw new UserDoesNotExistException();
        }

        MongoCollection mongoCollection = getCollection();

        Bson username = eq("name", user.getName());
        Bson password = eq("password", user.getPassword());

        mongoCollection.updateOne(username, password);
    }

    /**
     * Updates the Elo rating of the specified player in the database to the specified new Elo rating. Throws a 
     * UserDoesNotExistException if the user is not in the database.
     *
     * @param user The player whose Elo rating is being updated
     * @param newElo The player's new Elo rating
     */
    @Override
    public void updateUserElo(PlayerUser user, Integer newElo) throws UserDoesNotExistException {
        boolean res = checkUserExistence(user.getName());

        if (!res){
            // the user with the username is not in the database
            throw new UserDoesNotExistException();
        }

        MongoCollection mongoCollection = getCollection();

        Bson filter = eq("name", user.getName());
        Bson eloUpdate = set("elo", newElo);

        mongoCollection.updateOne(filter, eloUpdate);
    }

    /**
     * Returns the PlayerUser matching the given username. Throws a UserDoesNotExistException if the user 
     * is not in the database.
     *
     * @param username the username of the PlayerUser

     * @return the PlayerUser matching the given username 
     */
    @Override
    public PlayerUser getPlayerUser(String username) throws UserDoesNotExistException {
        boolean res = checkUserExistence(username);

        if (!res){
            // the user with the username is not in the database
            throw new UserDoesNotExistException();
        }

        MongoCollection mongoCollection = getCollection();

        Document document = new Document("name", username);
        FindIterable<Document> results = mongoCollection.find(document);

        String password = "";
        int elo = 0;
        for (Document res: results) {
            password = (String) res.get("password");
            elo = Integer.valueOf((String) res.get("elo"));
        }

        return new PlayerUser(username, elo);
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

}
