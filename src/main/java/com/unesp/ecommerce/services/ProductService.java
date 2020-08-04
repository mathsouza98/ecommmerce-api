package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(String name, String category, String price, String brand, Long stockQuantity) {
        return productRepository.save(new Product(name, category, price, brand, stockQuantity));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Optional<Product> getProductById(String id) { return productRepository.findById(id); }

    public Product updateProduct(String id, String name, String category, String price, String brand, Long stockQuantity, Long totalVisualization) {
        Product productToBeUpdated = productRepository.findById(id).get();

        productToBeUpdated.setName(name);
        productToBeUpdated.setCategory(category);
        productToBeUpdated.setPrice(price);
        productToBeUpdated.setBrand(brand);
        productToBeUpdated.setStockQuantity(stockQuantity);
        productToBeUpdated.setTotalVisualization(totalVisualization);

        return productRepository.save(productToBeUpdated);
    }
}
