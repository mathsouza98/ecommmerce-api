package com.unesp.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {

    @Id
    String id;
    String name;
    String category;
    String price;
    String brand;
    long stockQuantity;
    long totalVisualization;

    public Product(String name, String category, String price, String brand, long stockQuantity) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.totalVisualization = 0;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public long getTotalVisualization() {
        return totalVisualization;
    }

    public void setTotalVisualization(long totalVisualization) {
        this.totalVisualization = totalVisualization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
