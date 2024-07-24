package com.example.SnakeProyect.model;

import java.util.ArrayList;
import java.util.List;

public class SnakeGame {
    private int boardWidth;
    private int boardHeight;
    private Point[][] board;
    private List<Snake> snakes;
    private List<Snake> eliminatedSnakes;
    private Point apple;
    private Point powerFood;
    private boolean gameOver;
    private String winner;

    public SnakeGame(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.board = new Point[boardWidth][boardHeight];
        this.snakes = new ArrayList<>();
        this.eliminatedSnakes = new ArrayList<>();
        this.gameOver = false;
        this.winner = null;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public Point[][] getBoard() {
        return board;
    }

    public void setBoard(Point[][] board) {
        this.board = board;
    }

    public List<Snake> getSnakes() {
        return snakes;
    }

    public void setSnakes(List<Snake> snakes) {
        this.snakes = snakes;
    }

    public List<Snake> getEliminatedSnakes() {
        return eliminatedSnakes;
    }

    public void setEliminatedSnakes(List<Snake> eliminatedSnakes) {
        this.eliminatedSnakes = eliminatedSnakes;
    }

    public Point getApple() {
        return apple;
    }

    public void setApple(Point apple) {
        this.apple = apple;
    }

    public Point getPowerFood() {
        return powerFood;
    }

    public void setPowerFood(Point powerFood) {
        this.powerFood = powerFood;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void checkGameOver() {
        long activeSnakes = snakes.stream().filter(snake -> !snake.isGameOver()).count();
        if (activeSnakes <= 1) {
            this.gameOver = true;
            this.winner = snakes.stream().filter(snake -> !snake.isGameOver()).map(Snake::getId).findFirst().orElse(null);
            System.out.println("Game Over! Winner is " + this.winner);
        }
    }
}
