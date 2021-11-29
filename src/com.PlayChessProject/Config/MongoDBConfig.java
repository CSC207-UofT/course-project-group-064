package Config;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoDBConfig {

    private String uri = "mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net/test";

    @Bean(name="mongoclient")
    /**
     * Returns a MongoCollection instance to the Spring LC container
     */
    public MongoClient getMongoClient(){


        ConnectionString connectionString = new ConnectionString("mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net");
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
        MongoClient mongoClient = MongoClients.create(settings);
//        MongoDatabase database = mongoClient.getDatabase("test");


//        MongoClientURI mongoClientURI = new MongoClientURI(uri);
//        MongoClient mongoClient = new MongoClient(mongoClientURI);
        //MongoDatabase database = mongoClient.getDatabase("test");

        System.out.println("MongoDB connected");

//        MongoCollection mongocollection = mongoClient.getDatabase("MongoDB").getCollection("ChessGameUsers");
//
//        Document document = new Document();
//        document.append("name", "Snow Zhou");
//        document.append("age", "17");
//        document.append("elo", 0);
//
//        mongocollection.insertOne(document);

        return mongoClient;

    }

}
