package com.example.Debate.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile("dev")
@Configuration
public class MongoTemplateConfig {
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "debate");
}
}
