package com.example.Debate.repository;

import com.example.Debate.model.Argument;
import com.example.Debate.model.Attitude;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArgumentRepository extends MongoRepository<Argument,String> {
    Page<Argument> getArgumentsByAttitudeAndDebateId(Attitude attitude,String debateId, Pageable pageable);
}
