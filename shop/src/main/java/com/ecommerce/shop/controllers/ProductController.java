package com.ecommerce.shop.controllers;

import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.models.Category;
import com.ecommerce.shop.services.ProductService;
import com.ecommerce.shop.services.CategoryService;
import com.ecommerce.shop.services.ReviewService;
import com.ecommerce.shop.services.UserService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserService userService;

    // Home page — shows all products
    // Handles GET http://localhost:8080/
    @GetMapping("/")
    public String home(
        @RequestParam(required = false) String search,
        @RequestParam(required = false) Long categoryId,
        Model model
    ) {
        List<Product> products;
        List<Category> categories = categoryService.getAllCategories();

        if (search != null && !search.isEmpty()) {
            // User searched for something
            products = productService.searchProducts(search);
        } else if (categoryId != null) {
            // User clicked a category filter
            Optional<Category> category = categoryService.getCategoryById(categoryId);
            products = category.map(productService::getProductsByCategory)
                .orElse(productService.getAllProducts());
        } else {
            // No filter — show all products
            products = productService.getAllProducts();
        }

        // Pass data to the HTML page
        // "products" and "categories" become variables in Thymeleaf
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("search", search);
        model.addAttribute("selectedCategory", categoryId);

        // Returns src/main/resources/templates/index.html
        return "index";
    }

    // Product detail page
    // Handles GET http://localhost:8080/product/1
    @GetMapping("/product/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Optional<Product> product = productService.getProductById(id);

        if (product.isEmpty()) {
            // Product not found — redirect to home
            return "redirect:/";
        }

        // Get reviews and average rating for this product
        model.addAttribute("product", product.get());
        model.addAttribute("reviews", reviewService.getReviewsByProduct(product.get()));
        model.addAttribute("averageRating", reviewService.getAverageRating(product.get()));

        // Returns src/main/resources/templates/product.html
        return "product";
    }
    
    // Handle review form submission
    // Handles POST http://localhost:8081/product/1/review
    @PostMapping("/product/{id}/review")
    public String addReview(@PathVariable Long id,
            @RequestParam Integer rating,
            @RequestParam String comment,
            Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        productService.getProductById(id).ifPresent(product -> {
            com.ecommerce.shop.models.User user
                    = userService.getUserByEmail(principal.getName());
            reviewService.addReview(user, product, rating, comment);
        });
        return "redirect:/product/" + id;
    }
}

