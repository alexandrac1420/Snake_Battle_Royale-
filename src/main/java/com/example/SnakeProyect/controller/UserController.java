package com.example.SnakeProyect.controller;

import com.example.SnakeProyect.model.User;
import com.example.SnakeProyect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword("{noop}" + user.getPassword());
        userService.save(user);
        return "redirect:/login";
    }

}

