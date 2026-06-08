package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.Review;
import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Get all reviews for a product
    List<Review> findByProduct(Product product);

    // Get all reviews by a user
    List<Review> findByUser(User user);

    // Check if a user has already reviewed a product
    boolean existsByUserAndProduct(User user, Product product);
}