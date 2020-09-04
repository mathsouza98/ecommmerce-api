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

    public Product updateProduct(String id, Product product) {
        Product _product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Product not found"));

        if (productRepository == null) return null;

        _product.setName(product.getName());
        _product.setCategory(product.getCategory());
        _product.setPrice(product.getPrice());
        _product.setBrand(product.getBrand());
        _product.setStockQuantity(product.getStockQuantity());
        _product.setImagePathUrl(product.getImagePathUrl());
        _product.setOrderQuantity(product.getOrderQuantity());

        return productRepository.save(_product);
    }

    public void incrementProductTotalVisualization(String id) {
        long totalVisualization;
        Optional<Product> product = productRepository.findById(id);

        if (!product.isPresent()) return;

        Product productToUpdate = product.get();
        totalVisualization = productToUpdate.getTotalVisualization();

        productToUpdate.setTotalVisualization(totalVisualization + 1);

        productRepository.save(productToUpdate);
    }

    public boolean deleteProduct(String id) {
        Optional<Product> productToBeDeleted = productRepository.findById(id);

        if (!productToBeDeleted.isPresent()) return false;

        productRepository.delete(productToBeDeleted.get());

        return true;
    }
}
