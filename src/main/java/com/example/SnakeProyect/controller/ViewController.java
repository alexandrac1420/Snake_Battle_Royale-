package com.example.SnakeProyect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.SnakeProyect.model.User;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login"; // This should match the name of your login.html file
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register"; // This should match the name of your register.html file
    }

    @GetMapping("/home")
    public String home() {
        return "home"; // Assuming you have a home.html file to redirect to after successful login
    }

    @GetMapping("/create-game")
    public String createGame() {
        return "create-game"; // Ensure you have a create-game.html file in src/main/resources/templates
    }

    @GetMapping("/join-game")
    public String joinGame() {
        return "join-game"; // Ensure you have a join-game.html file in src/main/resources/templates
    }

    @GetMapping("/lobby")
    public String lobby() {
        return "lobby"; // Ensure you have a join-game.html file in src/main/resources/templates
    }

}

