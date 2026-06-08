package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.Order;
import com.ecommerce.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Get all orders for a specific user
    List<Order> findByUser(User user);

    // Get orders by status e.g. "PENDING", "SHIPPED"
    List<Order> findByStatus(String status);

    // Get all orders for a user with a specific status
    List<Order> findByUserAndStatus(User user, String status);
}