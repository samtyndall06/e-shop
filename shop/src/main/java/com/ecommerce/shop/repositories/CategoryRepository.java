package com.ecommerce.shop.repositories;

import com.ecommerce.shop.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find a category by its name e.g. "Electronics"
    Optional<Category> findByName(String name);
}