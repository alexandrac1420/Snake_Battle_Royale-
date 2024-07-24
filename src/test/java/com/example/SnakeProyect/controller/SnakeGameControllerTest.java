package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.SnakeGame;
import com.example.SnakeProyect.service.SnakeGameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SnakeGameControllerTest {

    @InjectMocks
    private SnakeGameController snakeGameController;

    @Mock
    private SnakeGameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testStartNewGame() {
        // Crear una instancia de SnakeGame con parámetros de ancho y alto del tablero
        SnakeGame mockGame = new SnakeGame(10, 10); // Asumiendo 10x10 como dimensiones de ejemplo
        when(gameService.createNewGame()).thenReturn(mockGame);

        // Llamar al método del controlador
        SnakeGame result = snakeGameController.startNewGame();

        // Verificar el resultado
        assertNotNull(result);
        assertEquals(10, result.getBoardWidth());
        assertEquals(10, result.getBoardHeight());
        verify(gameService, times(1)).createNewGame();
    }

    @Test
    void testTestEndpoint() {
        String result = snakeGameController.testEndpoint();
        assertEquals("The Snake Game is running!", result);
    }
}
