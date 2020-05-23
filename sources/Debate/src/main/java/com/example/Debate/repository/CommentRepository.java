package com.example.Debate.repository;

import com.example.Debate.model.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment,String> {

    List<Comment> findByParentActivityId(String parentActivityId);

}
