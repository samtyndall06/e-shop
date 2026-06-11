package com.ecommerce.shop.controllers;

import com.ecommerce.shop.models.Cart;
import com.ecommerce.shop.models.Order;
import com.ecommerce.shop.models.User;
import com.ecommerce.shop.services.CartService;
import com.ecommerce.shop.services.OrderService;
import com.ecommerce.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    // Order history page
    // Handles GET http://localhost:8080/orders
    @GetMapping("/orders")
    public String viewOrders(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Order> orders = orderService.getOrdersByUser(user);

        model.addAttribute("orders", orders);

        // Returns src/main/resources/templates/orders.html
        return "orders";
    }

    // Checkout page
    // Handles GET http://localhost:8080/checkout
    @GetMapping("/checkout")
    public String viewCheckout(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Cart> cartItems = cartService.getCartItems(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.getCartTotal(user));

        // Returns src/main/resources/templates/checkout.html
        return "checkout";
    }

    // Place order — called when user confirms checkout
    // Handles POST http://localhost:8080/checkout/confirm
    @PostMapping("/checkout/confirm")
    public String placeOrder(Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Cart> cartItems = cartService.getCartItems(user);

        if (cartItems.isEmpty()) {
            return "redirect:/cart";
        }

        Order order = orderService.placeOrder(user, cartItems);

        // Redirect to order confirmation page
        return "redirect:/orders/" + order.getId();
    }

    // Order confirmation page
    // Handles GET http://localhost:8080/orders/1
    @GetMapping("/orders/{id}")
    public String orderConfirmation(@PathVariable Long id, Model model) {
        orderService.getOrderById(id).ifPresent(order ->
            model.addAttribute("order", order)
        );
        return "order-confirmation";
    }
}