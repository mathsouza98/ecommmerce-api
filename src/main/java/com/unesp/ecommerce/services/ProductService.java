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

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Optional<Product> getProductById(String id) { return productRepository.findById(id); }

    public Product updateProduct(Product product) {
       Product persistedProduct = null;

       if(productRepository != null) {
           persistedProduct = productRepository.save(product);
       }
       return persistedProduct;
    }

    public void incrementProductTotalVisualization(String id) {
        long totalVisualization;
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()) {
            Product productToUpdate = product.get();
            totalVisualization = productToUpdate.getTotalVisualization();

            productToUpdate.setTotalVisualization(totalVisualization + 1);

            productRepository.save(productToUpdate);
        }
    }
}
