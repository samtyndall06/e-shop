package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring automatically writes the SQL for this!
    // SELECT * FROM users WHERE email = ?
    Optional<User> findByEmail(String email);

    // SELECT * FROM users WHERE name = ?
    Optional<User> findByName(String name);

    // Check if an email is already registered
    // SELECT COUNT(*) FROM users WHERE email = ?
    boolean existsByEmail(String email);
}