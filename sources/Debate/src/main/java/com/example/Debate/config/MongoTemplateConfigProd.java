package com.example.Debate.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile("prod")
@Configuration
public class MongoTemplateConfigProd {
    @Value("${app.mongohost}")
    private String mongoHost;

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(mongoHost);
    }
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "debate");
    }
}
