package com.example.SnakeProyect.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Spring configuration class that defines a bean for the password encoder.
 */
@Configuration
public class AppConfig {

    /**
     * Creates a BCryptPasswordEncoder bean to be used for password encoding.
     * @return a new BCryptPasswordEncoder object.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
