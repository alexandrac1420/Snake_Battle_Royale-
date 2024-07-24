package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.SnakeGame;
import com.example.SnakeProyect.service.SnakeGameService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class SnakeGameController {

    @Autowired
    private SnakeGameService gameService;

    @GetMapping("/new")
    public SnakeGame startNewGame() {
        return gameService.createNewGame();
    }
    
    @GetMapping("/test")
    public String testEndpoint() {
        return "The Snake Game is running!";
    }
}



