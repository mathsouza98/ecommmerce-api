package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import com.unesp.ecommerce.services.ProductService;
import com.unesp.ecommerce.services.UserHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserHistoryService userHistoryService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/products")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product insertProduct(@RequestBody Product productToBeInserted) {
        return productService.saveProduct(productToBeInserted);
    }

    @GetMapping("/products/{id}")
    public Optional<Product> listProductById(@PathVariable String id, @RequestHeader(required = false, value = "Authorization") String authorization) {

        userHistoryService.handleUserHistoryAction(id, authorization);

        productService.incrementProductTotalVisualization(id);

        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/products/{id}")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product updateProduct(@PathVariable String id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/products/{id}")
    public boolean deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
