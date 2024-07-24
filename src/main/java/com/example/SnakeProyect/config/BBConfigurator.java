package com.example.SnakeProyect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.SnakeProyect.SnakeGameHandler;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * WebSocket configuration class that enables and registers the WebSocket handler for the game.
 */
@Configuration
@EnableWebSocket
public class BBConfigurator implements WebSocketConfigurer {

    private final SnakeGameHandler snakeGameHandler;

    /**
     * Constructor that injects the SnakeGameHandler.
     * @param snakeGameHandler the WebSocket handler for the game.
     */
    @Autowired
    public BBConfigurator(SnakeGameHandler snakeGameHandler) {
        this.snakeGameHandler = snakeGameHandler;
    }

    /**
     * Registers the WebSocket handler at the "/game" endpoint and allows all origins.
     * @param registry the WebSocket handler registry.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(snakeGameHandler, "/game").setAllowedOrigins("*");
    }
}
