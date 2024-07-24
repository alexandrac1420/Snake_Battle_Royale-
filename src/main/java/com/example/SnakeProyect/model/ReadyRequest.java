package com.example.SnakeProyect.model;

public class ReadyRequest {
    private String snakeId;
    private boolean isReady;

    // Constructor
    public ReadyRequest() {
    }

    public ReadyRequest(String snakeId, boolean isReady) {
        this.snakeId = snakeId;
        this.isReady = isReady;
    }

    // Getters y Setters
    public String getSnakeId() {
        return snakeId;
    }

    public void setSnakeId(String snakeId) {
        this.snakeId = snakeId;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
}