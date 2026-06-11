package com.ecommerce.shop.controllers;

import com.ecommerce.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Show the login page
    // Handles GET http://localhost:8081/login
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid email or password.");
        }
        if (logout != null) {
            model.addAttribute("successMessage", "You have been logged out.");
        }
        // Returns src/main/resources/templates/login.html
        return "login";
    }

    // Show the register page
    // Handles GET http://localhost:8081/register
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Handle register form submission
    // Handles POST http://localhost:8081/register
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam(required = false) String address,
                           @RequestParam(required = false) String phone,
                           Model model) {
        try {
            userService.registerUser(name, email, password, address, phone);
            // Registration successful — redirect to login
            return "redirect:/login?registered";
        } catch (RuntimeException e) {
            // Email already exists
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}