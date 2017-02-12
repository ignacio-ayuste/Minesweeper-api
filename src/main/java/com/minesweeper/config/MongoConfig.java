package com.minesweeper.config;

import com.mongodb.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

@Configuration
@EnableMongoRepositories(basePackages = "com.minesweeper.repository")
public class MongoConfig {

    //@Value(value = "${MONGODB_URI}")
    //@Value("#{systemProperties['MONGODB_URI'] ?: ''}")
    public static String HOST;
    //public static final String DB_NAME = "minesweeper";
    public static final String DB_NAME = "heroku_dd13ms86";

    @Bean
    public Mongo mongo() throws Exception {

        //return new MongoClient();
        //new MongoURI("mongodb://minesweeper:minesweeper@ds149059.mlab.com:49059/minesweeper");
        //return new MongoClient(HOST);

        return new MongoClient(singletonList(new ServerAddress("ds149059.mlab.com", 49059)),
                singletonList(MongoCredential.createCredential("minesweeper","heroku_dd13ms86", "minesweeper".toCharArray())));

    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(),DB_NAME);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigIn() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}