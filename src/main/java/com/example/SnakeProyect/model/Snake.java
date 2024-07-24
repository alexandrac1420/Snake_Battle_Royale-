package com.example.SnakeProyect.model;

import java.util.List;

public class Snake {
    private String id;
    private List<Point> body;
    private String direction;
    private String lastValidDirection;
    private String activePower;
    private String activePowerMessage;
    private int originalSnakeSize;
    private int score;
    private String collisionType;
    private int speed;
    private boolean immune;
    private int powerDuration;
    private long powerStartTime;
    private boolean gameOver;

    public Snake(String id, List<Point> body) {
        this.id = id;
        this.body = body;
        this.direction = "RIGHT";
        this.lastValidDirection = "RIGHT";
        this.activePower = null;
        this.powerStartTime = 0;
        this.powerDuration = 0;
        this.score = 0;
        this.gameOver = false;
        this.speed = 100; // Default speed
        this.immune = false;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Point> getBody() {
        return body;
    }

    public void setBody(List<Point> body) {
        this.body = body;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getLastValidDirection() {
        return lastValidDirection;
    }

    public void setLastValidDirection(String lastValidDirection) {
        this.lastValidDirection = lastValidDirection;
    }

    public String getActivePower() {
        return activePower;
    }

    public void setActivePower(String activePower) {
        this.activePower = activePower;
    }

    public String getActivePowerMessage() {
        return activePowerMessage;
    }

    public void setActivePowerMessage(String activePowerMessage) {
        this.activePowerMessage = activePowerMessage;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getPowerDuration() {
        return powerDuration;
    }

    public void setPowerDuration(int powerDuration) {
        this.powerDuration = powerDuration;
    }

    public int getOriginalSnakeSize() {
        return originalSnakeSize;
    }

    public void setOriginalSnakeSize(int originalSnakeSize) {
        this.originalSnakeSize = originalSnakeSize;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getCollisionType() {
        return collisionType;
    }

    public void setCollisionType(String collisionType) {
        this.collisionType = collisionType;
    }

    public long getPowerStartTime() {
        return powerStartTime;
    }

    public void setPowerStartTime(long powerStartTime) {
        this.powerStartTime = powerStartTime;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }
}
