package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.OrderItem;
import com.ecommerce.shop.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Get all items in a specific order
    List<OrderItem> findByOrder(Order order);
}