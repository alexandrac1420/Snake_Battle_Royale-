package com.example.SnakeProyect.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "game_rooms")
public class GameRoom {
    @Id
    private String id;
    private String name;
    private int maxPlayers;
    private List<String> players;
    private List<Boolean> readyStatus;
    private boolean started;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<Boolean> getReadyStatus() {
        return readyStatus;
    }

    public void setReadyStatus(List<Boolean> readyStatus) {
        this.readyStatus = readyStatus;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
