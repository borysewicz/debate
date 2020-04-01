package com.example.Debate.repository;

import com.example.Debate.model.Argument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArgumentRepository extends MongoRepository<Argument,String> {
}
