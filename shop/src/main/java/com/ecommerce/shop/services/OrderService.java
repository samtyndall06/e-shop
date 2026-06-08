package com.ecommerce.shop.services;

import com.ecommerce.shop.models.*;
import com.ecommerce.shop.repositories.OrderRepository;
import com.ecommerce.shop.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartService cartService;

    // Get all orders for a user
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    // Get one order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Place an order from the user's cart
    public Order placeOrder(User user, List<Cart> cartItems) {

        // Create the order
        Order order = new Order();
        order.setUser(user);

        // Calculate total from cart items
        BigDecimal total = cartService.getCartTotal(user);
        order.setTotal(total);

        // Save the order first to get an ID
        Order savedOrder = orderRepository.save(order);

        // Create an OrderItem for each cart item
        for (Cart cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            // Store the price at time of purchase
            orderItem.setPrice(cartItem.getProduct().getPrice());
            orderItemRepository.save(orderItem);
        }

        // Clear the cart after order is placed
        cartService.clearCart(user);

        return savedOrder;
    }

    // Update order status e.g. to "SHIPPED" or "DELIVERED"
    public Order updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}