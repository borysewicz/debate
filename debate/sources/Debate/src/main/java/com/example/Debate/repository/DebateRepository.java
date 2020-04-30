package com.example.Debate.repository;

import com.example.Debate.model.Debate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DebateRepository extends MongoRepository<Debate,String> {

    public List<Debate> findByTitleRegex(String reqName);

}
