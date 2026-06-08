package com.ecommerce.shop.services;

import com.ecommerce.shop.models.Cart;
import com.ecommerce.shop.models.User;
import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Get all cart items for a user
    public List<Cart> getCartItems(User user) {
        return cartRepository.findByUser(user);
    }

    // Add a product to the cart
    // If it's already there, just increase the quantity
    public Cart addToCart(User user, Product product, Integer quantity) {
        Optional<Cart> existingItem = cartRepository.findByUserAndProduct(user, product);

        if (existingItem.isPresent()) {
            // Product already in cart — increase quantity
            Cart cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartRepository.save(cartItem);
        } else {
            // New cart item
            Cart cartItem = new Cart();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            return cartRepository.save(cartItem);
        }
    }

    // Remove a single item from the cart
    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    // Clear the entire cart (used after checkout)
    public void clearCart(User user) {
        cartRepository.deleteByUser(user);
    }

    // Calculate the total price of all items in the cart
    public BigDecimal getCartTotal(User user) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        BigDecimal total = BigDecimal.ZERO;

        for (Cart item : cartItems) {
            // price × quantity for each item
            BigDecimal itemTotal = item.getProduct().getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }

        return total;
    }

    // Get total number of items in cart (for the cart badge in navbar)
    public Integer getCartCount(User user) {
        List<Cart> cartItems = cartRepository.findByUser(user);
        return cartItems.stream()
            .mapToInt(Cart::getQuantity)
            .sum();
    }
}