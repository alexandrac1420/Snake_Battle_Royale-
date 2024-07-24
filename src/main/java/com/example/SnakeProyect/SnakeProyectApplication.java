package com.example.SnakeProyect;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The entry point for the Spring Boot application.
 */
@SpringBootApplication
public class SnakeProyectApplication {
    
    /**
     * The main method to launch the Spring Boot application.
     * 
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Create a SpringApplication instance for the SnakeProyectApplication class
        SpringApplication app = new SpringApplication(SnakeProyectApplication.class);

        // Set the default server port using the getPort method
        app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));

        // Run the application with the provided arguments
        app.run(args);
    }

    /**
     * Retrieves the server port from the environment variable "PORT" if set,
     * otherwise returns the default port (8080).
     * 
     * @return The server port number.
     */
    static int getPort() {
        // Check if the "PORT" environment variable is set
        if (System.getenv("PORT") != null) {
            // Parse and return the port number from the environment variable
            return Integer.parseInt(System.getenv("PORT"));
        }
        // Return the default port number
        return 8080;
    }
}
