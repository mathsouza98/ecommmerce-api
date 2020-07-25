package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(String name, String category, String categoryByPrice, String price) {
        return productRepository.save(new Product(name, category, categoryByPrice, price));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    public Product updateProduct(long id, String name, String category, String categoryByPrice, String price) {
        Product productToBeUpdated = productRepository.findById(id);

        productToBeUpdated.setName(name);
        productToBeUpdated.setCategory(category);
        productToBeUpdated.setCategoryByPrice(categoryByPrice);
        productToBeUpdated.setPrice(price);

        return productRepository.save(productToBeUpdated);
    }
}
