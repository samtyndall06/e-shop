package com.ecommerce.shop;

import com.ecommerce.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Marks this as a configuration class
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // @Lazy prevents a circular dependency between
    // SecurityConfig and UserService
    @Autowired
    @Lazy
    private UserService userService;

    // This is the bean UserService was looking for!
    // BCrypt is a strong password hashing algorithm
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Controls which pages require login and which are public
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // These pages are public — anyone can access them
                .requestMatchers("/", "/product/**", "/register", "/login", "/css/**", "/js/**").permitAll()
                // Everything else requires login
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                // Spring Security will show this page for login
                .loginPage("/login")
                // After login, go to home page
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout(logout -> logout
                // After logout, go to home page
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}