package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.SnakeGame;
import com.example.SnakeProyect.service.SnakeGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing the Snake game.
 */
@RestController
@RequestMapping("/api/game")
public class SnakeGameController {

    @Autowired
    private SnakeGameService gameService;

    /**
     * Starts a new game of Snake.
     * @return the newly created SnakeGame object.
     */
    @GetMapping("/new")
    public SnakeGame startNewGame() {
        return gameService.createNewGame();
    }
    
    /**
     * Test endpoint to check if the Snake game service is running.
     * @return a message indicating that the Snake game is running.
     */
    @GetMapping("/test")
    public String testEndpoint() {
        return "The Snake Game is running!";
    }
}
