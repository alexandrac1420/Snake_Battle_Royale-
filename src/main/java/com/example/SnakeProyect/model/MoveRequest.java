package com.example.SnakeProyect.model;

public class MoveRequest {
    private String snakeId;
    private String direction; // Nueva propiedad para la dirección
    private SnakeGame game;

    // Getters y setters
    public String getSnakeId() {
        return snakeId;
    }

    public void setSnakeId(String snakeId) {
        this.snakeId = snakeId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public SnakeGame getGame() {
        return game;
    }

    public void setGame(SnakeGame game) {
        this.game = game;
    }
}
