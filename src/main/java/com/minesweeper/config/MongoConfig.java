package com.minesweeper.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.minesweeper.repository")
public class MongoConfig {

    public static final String HOST = "localhost";
    public static final String DB_NAME = "minesweeper";

    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(HOST);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), DB_NAME);
    }

}