package com.example.SnakeProyect.service;

import com.example.SnakeProyect.model.Point;
import com.example.SnakeProyect.model.Snake;
import com.example.SnakeProyect.model.SnakeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SnakeGameServiceTest {

    @InjectMocks
    private SnakeGameService snakeGameService;

    @Mock
    private Random random;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateNewGame() {
        SnakeGame game = snakeGameService.createNewGame();

        assertNotNull(game);
        assertEquals(SnakeGameService.BOARD_WIDTH, game.getBoard().length);
        assertEquals(SnakeGameService.BOARD_HEIGHT, game.getBoard()[0].length);
        assertNotNull(game.getApple());
        assertNotNull(game.getPowerFood());
        assertTrue(game.getSnakes().isEmpty());
    }

    @Test
    void testAddSnakeToGame() {
        SnakeGame game = snakeGameService.createNewGame();
        snakeGameService.addSnakeToGame("snake1");

        assertEquals(1, game.getSnakes().size());
        Snake snake = game.getSnakes().get(0);
        assertEquals("snake1", snake.getId());
        assertEquals(5, snake.getBody().size());
    }

    @Test
    void testRemoveSnakeFromGame() {
        SnakeGame game = snakeGameService.createNewGame();
        snakeGameService.addSnakeToGame("snake1");
        snakeGameService.removeSnakeFromGame("snake1");

        assertTrue(game.getSnakes().isEmpty());
    }

    @Test
    void testMoveSnake() {
        SnakeGame game = snakeGameService.createNewGame();
        snakeGameService.addSnakeToGame("snake1");

        Snake snake = game.getSnakes().get(0);
        snake.setLastValidDirection("RIGHT");
        snake.setBody(createSnakeBody());

        game.setApple(new Point(7, 0, "apple"));

        SnakeGame resultGame = snakeGameService.moveSnake("snake1", "RIGHT");

        assertNotNull(resultGame);
        // Snake resultSnake = resultGame.getSnakes().get(0);
        // assertEquals(5, resultSnake.getBody().size());
        // assertEquals(new Point(5, 0, "snake"), resultSnake.getBody().get(0));
    }

    private List<Point> createSnakeBody() {
        List<Point> body = new ArrayList<>();
        body.add(new Point(5, 0, "snake"));
        body.add(new Point(4, 0, "snake"));
        body.add(new Point(3, 0, "snake"));
        body.add(new Point(2, 0, "snake"));
        body.add(new Point(1, 0, "snake"));
        return body;
    }
}
