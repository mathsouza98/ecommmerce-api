package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class productController {

    @Autowired
    private ProductService productService;

    @PostMapping("/insert-product")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product insertProduct(@RequestBody Product productToBeInserted) {
        String name = productToBeInserted.getName();
        String category = productToBeInserted.getCategory();
        String categoryByPrice = productToBeInserted.getCategoryByPrice();
        String price = productToBeInserted.getPrice();

        return productService.saveProduct(name, category, categoryByPrice, price);
    }

    @GetMapping("/list-products")
    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }
}
