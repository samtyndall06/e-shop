package com.ecommerce.shop.services;

import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.models.Category;
import com.ecommerce.shop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get one product by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Get all products in a category
    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    // Search products by name
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Get only products that are in stock
    public List<Product> getInStockProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    // Save a new or updated product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Delete a product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}