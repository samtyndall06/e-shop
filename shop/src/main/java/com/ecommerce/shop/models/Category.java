package com.ecommerce.shop.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Category name e.g. "Electronics", "Fashion"
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    // One category can have many products
    // mappedBy = "category" refers to the field name in the Product class
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    // ---- GETTERS AND SETTERS ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }
}