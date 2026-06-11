package com.ecommerce.shop.controllers;

import com.ecommerce.shop.models.Cart;
import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.models.User;
import com.ecommerce.shop.services.CartService;
import com.ecommerce.shop.services.ProductService;
import com.ecommerce.shop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    // View cart page
    // Handles GET http://localhost:8080/cart
    @GetMapping("/cart")
    public String viewCart(Model model, Principal principal) {
        User user = userService.getUserByEmail(principal.getName());
        List<Cart> cartItems = cartService.getCartItems(user);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.getCartTotal(user));

        // Returns src/main/resources/templates/cart.html
        return "cart";
    }

    // Add item to cart
    // Handles POST http://localhost:8080/cart/add
    @PostMapping("/cart/add")
    public String addToCart(
        @RequestParam Long productId,
        @RequestParam(defaultValue = "1") Integer quantity,
        Principal principal
    ) {
        User user = userService.getUserByEmail(principal.getName());
        Optional<Product> product = productService.getProductById(productId);

        product.ifPresent(p -> cartService.addToCart(user, p, quantity));

        // Redirect back to the page they came from
        return "redirect:/cart";
    }

    // Remove item from cart
    // Handles POST http://localhost:8080/cart/remove
    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }
}