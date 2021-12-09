package com.playchessgame.chessgame.Config;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A configuration class for registering MongoDB connection into the Spring LOC container when the project is
 * initialized
 */
@Configuration
public class MongoDBConfig {

    private String uri = "mongodb+srv://kaixinrongzi:kaixinrongzi123456@cluster0.c8qyn.mongodb.net";

    /**
     * Returns a MongoCollection instance to the Spring LOC container
     *
     * @return a MongoCollection instance
     */
    @Bean(name = "mongoclient")
    public MongoClient getMongoClient() {

        MongoClientURI mongoClientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        System.out.println("MongoDB connected");

        return mongoClient;

    }

}
