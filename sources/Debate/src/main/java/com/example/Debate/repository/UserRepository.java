package com.example.Debate.repository;

import com.example.Debate.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByLogin(String login);
}
