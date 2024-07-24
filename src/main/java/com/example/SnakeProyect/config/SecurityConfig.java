package com.example.SnakeProyect.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.example.SnakeProyect.model.User;
import com.example.SnakeProyect.service.UserService;

import java.util.ArrayList;

/**
 * Security configuration class for the application.
 */
@Configuration
public class SecurityConfig {

    private final UserService userService;

    /**
     * Constructor that injects the UserService.
     * @param userService the service for managing users.
     */
    @Autowired
    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    /**
     * Creates a bean for the password encoder that performs no encoding.
     * Note: For production, a more secure encoder like BCrypt is recommended.
     * @return the NoOpPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = NoOpPasswordEncoder.getInstance();
        System.out.println("Using NoOpPasswordEncoder: " + encoder);
        return encoder;
    }

    /**
     * Creates a bean for the UserDetailsService.
     * @return a UserDetailsService that loads the user by username.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            User user = userService.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException(username);
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        };
    }

    /**
     * Configures the security filter chain.
     * @param http the HttpSecurity object to configure security.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register", "/login", "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout((logout) -> logout
                        .permitAll());
        return http.build();
    }

    /**
     * Creates a bean for the AuthenticationManager.
     * @param authenticationConfiguration the authentication configuration.
     * @return the configured AuthenticationManager.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        System.out.println("Configuring AuthenticationManager");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Creates a bean for the authentication success handler that redirects the user to the home page.
     * @return the configured AuthenticationSuccessHandler.
     */
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/home");
    }
}
