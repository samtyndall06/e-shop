package com.ecommerce.shop.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// Tells Spring Boot this class maps to a database table
@Entity
// Names the table "users" in PostgreSQL
@Table(name = "users")
public class User {

    // Primary key - auto increments (1, 2, 3...)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each field below becomes a column in the table

    // nullable = false means this column is required
    @Column(nullable = false)
    private String name;

    // unique = true means no two users can have the same email
    @Column(nullable = false, unique = true)
    private String email;

    // We'll store an encrypted version of the password later
    @Column(nullable = false)
    private String password;

    // Optional fields
    private String address;
    private String phone;

    // Automatically set when the user is created
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Runs automatically before saving to the database
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ---- GETTERS AND SETTERS ----
    // These let other classes read and write the fields

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}