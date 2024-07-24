package com.example.SnakeProyect.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SnakeProyect.model.User;

/**
 * Repository interface for managing User entities in MongoDB.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Finds a user by their username.
     * @param username the username of the user to find.
     * @return the User object associated with the given username.
     */
    User findByUsername(String username);
}
