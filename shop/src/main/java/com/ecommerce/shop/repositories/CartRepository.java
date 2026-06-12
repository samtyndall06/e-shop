package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.Cart;
import com.ecommerce.shop.models.User;
import com.ecommerce.shop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Get all cart items for a specific user
    List<Cart> findByUser(User user);

    // Find a specific product in a user's cart
    Optional<Cart> findByUserAndProduct(User user, Product product);

    // Delete all cart items for a user (used after checkout)
    @Transactional
    void deleteByUser(User user);
}