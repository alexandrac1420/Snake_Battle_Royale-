package com.example.SnakeProyect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SnakeProyect.model.User;
import com.example.SnakeProyect.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public User findByUsername(String username) {
        System.out.println("Buscando usuario en la base de datos: " + username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("Usuario no encontrado en la base de datos: " + username);
        } else {
            System.out.println("Usuario encontrado en la base de datos: " + user.getUsername());
        }
        return user;
    }
}
