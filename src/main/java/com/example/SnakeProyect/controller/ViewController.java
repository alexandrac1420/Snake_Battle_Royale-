package com.example.SnakeProyect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SnakeProyect.model.User;

/**
 * Controller for handling view-related requests.
 */
@Controller
public class ViewController {

    /**
     * Displays the login page.
     * @param model the model to be used by the view.
     * @return the name of the view template for the login page.
     */
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login"; // This should match the name of your login.html file
    }

    /**
     * Displays the registration page.
     * @param model the model to be used by the view.
     * @return the name of the view template for the registration page.
     */
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register"; // This should match the name of your register.html file
    }

    /**
     * Displays the home page.
     * @return the name of the view template for the home page.
     */
    @GetMapping("/home")
    public String home() {
        return "home"; // Assuming you have a home.html file to redirect to after successful login
    }

    /**
     * Displays the page for creating a new game.
     * @return the name of the view template for the create game page.
     */
    @GetMapping("/create-game")
    public String createGame() {
        return "create-game"; // Ensure you have a create-game.html file in src/main/resources/templates
    }

    /**
     * Displays the page for joining an existing game.
     * @return the name of the view template for the join game page.
     */
    @GetMapping("/join-game")
    public String joinGame() {
        return "join-game"; // Ensure you have a join-game.html file in src/main/resources/templates
    }

    /**
     * Displays the lobby page.
     * @return the name of the view template for the lobby page.
     */
    @GetMapping("/lobby")
    public String lobby() {
        return "lobby"; // Ensure you have a lobby.html file in src/main/resources/templates
    }

}
