package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.User;
import com.example.SnakeProyect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for handling user-related requests.
 */
@Controller
public class UserController {

    private final UserService userService;

    /**
     * Constructor that injects the UserService.
     * @param userService the service for managing user operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles user registration.
     * @param user the User object containing registration details.
     * @return a redirect to the login page upon successful registration.
     */
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        // Prefixes the password with "{noop}" to indicate that no password encoding is being applied.
        user.setPassword("{noop}" + user.getPassword());
        userService.save(user);
        return "redirect:/login";
    }
}
