package com.example.Debate.repository;

import com.example.Debate.model.Debate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DebateRepository extends MongoRepository<Debate,String> {
}
