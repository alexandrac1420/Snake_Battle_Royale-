package com.example.SnakeProyect.model;

public class ReadyRequest {
    private String playerName;
    private boolean isReady;

    // Constructor
    public ReadyRequest() {
    }

    public ReadyRequest(String playerName, boolean isReady) {
        this.playerName = playerName;
        this.isReady = isReady;
    }

    // Getters y Setters
    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean isReady) {
        this.isReady = isReady;
    }
}