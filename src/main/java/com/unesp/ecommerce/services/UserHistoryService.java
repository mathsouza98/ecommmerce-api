package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.repository.ProductRepository;

public class UserHistoryService {
    
    private ProductRepository productRepository;
        
    public Product updateUserHistory(String id, String authorization) {
        Product product = productRepository.findById(id).get();
        product.setTotalVisualization(product.getTotalVisualization() + 1);

        return productRepository.save(product);
    }
}
