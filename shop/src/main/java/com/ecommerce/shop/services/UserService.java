package com.ecommerce.shop.services;

import com.ecommerce.shop.models.User;
import com.ecommerce.shop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get user by email (used for login)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Register a new user
    public User registerUser(String name, String email, String password, String address, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        // Encrypt the password before saving
        user.setPassword(passwordEncoder.encode(password));
        user.setAddress(address);
        user.setPhone(phone);
        return userRepository.save(user);
    }

    // Update user details
    public User updateUser(User user) {
        return userRepository.save(user);
    }
}