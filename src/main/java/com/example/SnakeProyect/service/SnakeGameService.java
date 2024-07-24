package com.example.SnakeProyect.service;

import org.springframework.stereotype.Service;
import com.example.SnakeProyect.model.Point;
import com.example.SnakeProyect.model.Snake;
import com.example.SnakeProyect.model.SnakeGame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SnakeGameService {
    private static final int CANVAS_WIDTH = 400;
    private static final int CANVAS_HEIGHT = 400;
    private static final int DEFAULT_SPEED = 100;
    private static final int SPEED_POWER_SPEED = 50; // Speed when speed power is active
    static final int BOARD_WIDTH = CANVAS_WIDTH / 10;
    static final int BOARD_HEIGHT = CANVAS_HEIGHT / 10;
    private Random random = new Random();
    private AtomicInteger snakeIdCounter = new AtomicInteger(1);
    private SnakeGame currentGame;

    public SnakeGame createNewGame() {
        SnakeGame game = new SnakeGame(BOARD_WIDTH, BOARD_HEIGHT);
        initializeBoard(game.getBoard());
        game.setSnakes(new ArrayList<>());
        game.setApple(generateApple(game.getBoard()));
        game.setPowerFood(generatePowerFood(game.getBoard()));
        updateBoard(game);
        currentGame = game;
        return game;
    }

    private void initializeBoard(Point[][] board) {
        for (int i = 0; i < BOARD_WIDTH; i++) {
            for (int j = 0; j < BOARD_HEIGHT; j++) {
                board[i][j] = new Point(i, j, null);
            }
        }
    }

    public void addSnakeToGame(String snakeId) {
        if (currentGame == null) {
            createNewGame();
        }
        Snake newSnake = createNewSnake(snakeId, currentGame);
        newSnake.setOriginalSnakeSize(newSnake.getBody().size());
        currentGame.getSnakes().add(newSnake);
        updateBoard(currentGame);
    }

    public void removeSnakeFromGame(String snakeId) {
        if (currentGame != null) {
            Snake snake = findSnakeById(snakeId, currentGame);
            if (snake != null) {
                currentGame.getSnakes().remove(snake);
            }
        }
    }

    private Snake createNewSnake(String snakeId, SnakeGame game) {
        List<Point> initialBody = initializeSnake(game);
        Snake snake = new Snake(snakeId, initialBody);
        snake.setSpeed(DEFAULT_SPEED); // Set default speed
        return snake;
    }

    private Point generatePowerFood(Point[][] board) {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (board[x][y].getType() != null);

        return new Point(x, y, "powerFood");
    }

    private Point generateApple(Point[][] board) {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (board[x][y].getType() != null);

        return new Point(x, y, "apple");
    }

    private List<Point> initializeSnake(SnakeGame game) {
        List<Point> body = new ArrayList<>();
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH - 5) + 2; // Leave margin for the snake length
            y = random.nextInt(BOARD_HEIGHT - 5) + 2;
        } while (isPositionOccupied(x, y, game));

        for (int i = 0; i < 5; i++) {
            body.add(new Point(x - i, y, "snake"));
        }
        return body;
    }

    private boolean isPositionOccupied(int x, int y, SnakeGame game) {
        for (Snake snake : game.getSnakes()) {
            for (Point part : snake.getBody()) {
                if (part.getX() == x && part.getY() == y) {
                    return true;
                }
            }
        }
        return false;
    }

    private void updateSnakePosition(Snake snake, Point[][] board, String direction) {
        List<Point> body = snake.getBody();
        Point head = body.get(0);
        Point newHead = null;

        switch (direction) {
            case "UP":
                newHead = new Point(head.getX(), head.getY() - 1, "snake");
                break;
            case "DOWN":
                newHead = new Point(head.getX(), head.getY() + 1, "snake");
                break;
            case "LEFT":
                newHead = new Point(head.getX() - 1, head.getY(), "snake");
                break;
            case "RIGHT":
                newHead = new Point(head.getX() + 1, head.getY(), "snake");
                break;
        }

        // Check for out of bounds or collision
        if (newHead.getX() < 0 || newHead.getX() >= BOARD_WIDTH || newHead.getY() < 0 || newHead.getY() >= BOARD_HEIGHT
                || isPositionOccupied(newHead.getX(), newHead.getY(), currentGame)) {
            if (snake.isImmune()) {
                handleCollisionWithInertia(snake, direction);
                return;
            } else {
                snake.setGameOver(true);
                return;
            }
        }

        if (newHead != null) {
            body.add(0, newHead);
            body.remove(body.size() - 1);
            snake.setBody(body);
        }

        // Check for collision with apple
        Point apple = currentGame.getApple();
        if (newHead.getX() == apple.getX() && newHead.getY() == apple.getY()) {
            snake.getBody().add(new Point(apple.getX(), apple.getY(), "snake"));
            currentGame.setApple(generateApple(currentGame.getBoard()));
            snake.setScore(snake.getScore() + 10); // Increment score by 10
        }

        // Check for collision with power food
        Point powerFood = currentGame.getPowerFood();
        if (newHead.getX() == powerFood.getX() && newHead.getY() == powerFood.getY()) {
            activatePower(snake, powerFood);
            currentGame.setPowerFood(generatePowerFood(currentGame.getBoard()));
        }

        updateBoard(currentGame);
    }

    private void activatePower(Snake snake, Point powerFood) {
        int powerType = random.nextInt(3);
        switch (powerType) {
            case 0:
                snake.setActivePower("speed");
                snake.setPowerDuration(5000);
                snake.setSpeed(SPEED_POWER_SPEED); // Increase speed significantly
                break;
            case 1:
                snake.setActivePower("shrink");
                snake.setPowerDuration(5000);
                while (snake.getBody().size() > 3) { // Shrink to 3 segments
                    snake.getBody().remove(snake.getBody().size() - 1);
                }
                break;
            case 2:
                snake.setActivePower("immune");
                snake.setPowerDuration(5000);
                snake.setImmune(true); // Make snake immune
                break;
        }
        snake.setPowerStartTime(System.currentTimeMillis());
    }

    private void updateBoard(SnakeGame game) {
        Point[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setType(null);
            }
        }

        for (Snake snake : game.getSnakes()) {
            for (Point part : snake.getBody()) {
                if (part.getX() >= 0 && part.getX() < BOARD_WIDTH && part.getY() >= 0 && part.getY() < BOARD_HEIGHT) {
                    board[part.getX()][part.getY()].setType("snake");
                }
            }
        }

        Point apple = game.getApple();
        board[apple.getX()][apple.getY()].setType("apple");

        Point powerFood = game.getPowerFood();
        if (powerFood.getX() >= 0 && powerFood.getY() >= 0) {
            board[powerFood.getX()][powerFood.getY()].setType("powerFood");
        }
    }

    public SnakeGame moveSnake(String snakeId, String direction) {
        Snake snake = findSnakeById(snakeId, currentGame);
        if (snake != null && !snake.isGameOver()) {
            if (isValidDirectionChange(snake.getLastValidDirection(), direction)) {
                snake.setLastValidDirection(direction);
                updateSnakePosition(snake, currentGame.getBoard(), direction);
                updatePowerEffects(snake); // Update power effects after movement
            }
        }
        return currentGame;
    }

    private void updatePowerEffects(Snake snake) {
        if (snake.getActivePower() != null) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - snake.getPowerStartTime() > snake.getPowerDuration()) {
                switch (snake.getActivePower()) {
                    case "speed":
                        snake.setSpeed(DEFAULT_SPEED); // Reset speed to default
                        break;
                    case "shrink":
                        while (snake.getBody().size() < snake.getOriginalSnakeSize()) { // Restore original size
                            Point tail = snake.getBody().get(snake.getBody().size() - 1);
                            snake.getBody().add(new Point(tail.getX(), tail.getY(), "snake"));
                        }
                        break;
                    case "immune":
                        snake.setImmune(false); // Remove immunity
                        break;
                }
                snake.setActivePower(null);
            }
        }
    }

    private void handleCollisionWithInertia(Snake snake, String direction) {
        String newDirection = direction;
        while (newDirection.equals(direction)) {
            newDirection = getRandomDirection();
        }
        if (isValidDirectionChange(snake.getLastValidDirection(), newDirection)) {
            snake.setLastValidDirection(newDirection);
            updateSnakePosition(snake, currentGame.getBoard(), newDirection);
        }
    }

    private String getRandomDirection() {
        String[] directions = { "UP", "DOWN", "LEFT", "RIGHT" };
        return directions[random.nextInt(directions.length)];
    }

    private boolean isValidDirectionChange(String currentDirection, String newDirection) {
        return !((currentDirection.equals("UP") && newDirection.equals("DOWN")) ||
                (currentDirection.equals("DOWN") && newDirection.equals("UP")) ||
                (currentDirection.equals("LEFT") && newDirection.equals("RIGHT")) ||
                (currentDirection.equals("RIGHT") && newDirection.equals("LEFT")));
    }

    private Snake findSnakeById(String snakeId, SnakeGame game) {
        return game.getSnakes().stream()
                .filter(snake -> snake.getId().equals(snakeId))
                .findFirst()
                .orElse(null);
    }

    public SnakeGame getGameState() {
        if (currentGame == null) {
            createNewGame();
        }
        return currentGame;
    }

    public void setPlayerReady(String snakeId) {
        // TODO: Implement logic for setting player ready
    }

    public Map<String, Object> getOptimizedGameState() {
        Map<String, Object> gameState = new HashMap<>();
        List<Map<String, Object>> snakes = new ArrayList<>();
    
        for (Snake snake : currentGame.getSnakes()) {
            if (snake.getBody().size() > 0) {
                Map<String, Object> snakeData = new HashMap<>();
                snakeData.put("id", snake.getId());
                snakeData.put("x", snake.getBody().get(0).getX()); // Solo la cabeza
                snakeData.put("y", snake.getBody().get(0).getY());
                snakeData.put("dir", snake.getLastValidDirection());
                snakeData.put("s", snake.getScore());
                snakeData.put("p", snake.getActivePower());
                snakeData.put("pd", snake.getPowerDuration());
                snakes.add(snakeData);
            }
        }
    
        gameState.put("snakes", snakes);
        gameState.put("a", Map.of("x", currentGame.getApple().getX(), "y", currentGame.getApple().getY()));
        gameState.put("pf", Map.of("x", currentGame.getPowerFood().getX(), "y", currentGame.getPowerFood().getY(), "t", currentGame.getPowerFood().getType()));
        gameState.put("go", currentGame.isGameOver());
        gameState.put("w", currentGame.getWinner());
    
        return gameState;
    }
    

}
