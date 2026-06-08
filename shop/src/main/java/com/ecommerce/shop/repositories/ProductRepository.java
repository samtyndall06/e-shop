package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.Product;
import com.ecommerce.shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Get all products in a category
    List<Product> findByCategory(Category category);

    // Search products by name (case insensitive)
    // %name% means contains the search term anywhere
    List<Product> findByNameContainingIgnoreCase(String name);

    // Get all products with stock greater than 0
    List<Product> findByStockGreaterThan(Integer stock);
}