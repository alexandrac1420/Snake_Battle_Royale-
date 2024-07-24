package com.example.SnakeProyect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SnakeProyect.model.User;

public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
