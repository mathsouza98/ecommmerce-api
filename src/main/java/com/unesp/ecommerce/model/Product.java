package com.unesp.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Product {

    @Id
    String id;
    String name;
    String category;
    String categoryByPrice;
    String price;

    public Product(String name, String category, String categoryByPrice, String price) {
        this.name = name;
        this.category = category;
        this.categoryByPrice = categoryByPrice;
        this.price = price;
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

    public String getCategoryByPrice() {
        return categoryByPrice;
    }

    public void setCategoryByPrice(String categoryByPrice) {
        this.categoryByPrice = categoryByPrice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
