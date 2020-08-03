package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;

import java.util.Optional;

public class UserHistoryService {
    
    private ProductService productService;
        
    public void updateUserHistory(String id, String authorization) {
        Product product = productService.getProductById(id);
        product.setTotalVisualization(product.getTotalVisualization() + 1);
    }
}
