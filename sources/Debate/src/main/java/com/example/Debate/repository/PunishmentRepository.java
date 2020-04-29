package com.example.Debate.repository;

import com.example.Debate.model.Punishment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PunishmentRepository extends MongoRepository<Punishment,String> {
}
