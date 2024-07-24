package com.example.SnakeProyect.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.example.SnakeProyect.SnakeGameHandler;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSocket
public class BBConfigurator implements WebSocketConfigurer {

    private final SnakeGameHandler snakeGameHandler;

    @Autowired
    public BBConfigurator(SnakeGameHandler snakeGameHandler) {
        this.snakeGameHandler = snakeGameHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(snakeGameHandler, "/game").setAllowedOrigins("*");
    }
}
