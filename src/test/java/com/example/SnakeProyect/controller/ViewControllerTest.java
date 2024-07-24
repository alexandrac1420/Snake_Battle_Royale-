package com.example.SnakeProyect.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ViewControllerTest {

    private ViewController viewController = new ViewController();

    @Test
    void testLogin() {
        Model model = mock(Model.class);
        String viewName = viewController.login(model);
        assertEquals("login", viewName);
    }

    @Test
    void testRegister() {
        Model model = mock(Model.class);
        String viewName = viewController.register(model);
        assertEquals("register", viewName);
    }

    @Test
    void testHome() {
        String viewName = viewController.home();
        assertEquals("home", viewName);
    }

    @Test
    void testCreateGame() {
        String viewName = viewController.createGame();
        assertEquals("create-game", viewName);
    }

    @Test
    void testJoinGame() {
        String viewName = viewController.joinGame();
        assertEquals("join-game", viewName);
    }

    @Test
    void testLobby() {
        String viewName = viewController.lobby();
        assertEquals("lobby", viewName);
    }
}
