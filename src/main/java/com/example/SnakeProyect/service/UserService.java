package com.example.SnakeProyect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SnakeProyect.model.User;
import com.example.SnakeProyect.repository.UserRepository;

/**
 * Service class for handling operations related to User entities.
 */
@Service
public class UserService {

    // Repository for performing CRUD operations on User entities
    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     * 
     * @param userRepository The repository used for interacting with User data.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a User entity to the database.
     * 
     * @param user The User entity to be saved.
     * @return The saved User entity.
     */
    public User save(User user) {
        // Save the user to the database and return the saved user
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    /**
     * Find a User entity by its username.
     * 
     * @param username The username of the User to be found.
     * @return The User entity with the given username, or null if not found.
     */
    public User findByUsername(String username) {
        // Log the search operation
        System.out.println("Searching for user in the database: " + username);
        // Find the user by username using the repository
        User user = userRepository.findByUsername(username);
        // Log the result of the search
        if (user == null) {
            System.out.println("User not found in the database: " + username);
        } else {
            System.out.println("User found in the database: " + user.getUsername());
        }
        return user;
    }
}
