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

    @PostMapping("/insert-product")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product insertProduct(@RequestBody Product productToBeInserted) {

        return productService.saveProduct(productToBeInserted);
    }

    @PutMapping("/update-product/{id}")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public boolean updateProduct(@PathVariable String id, @RequestBody Product product) {
        boolean update = false;

        Optional<Product> productUpdate = productService.getProductById(id);

        if(productUpdate.isPresent()) {

            Product _productToBeUpdated = productUpdate.get();

            _productToBeUpdated.setName(product.getName());
            _productToBeUpdated.setCategory(product.getCategory());
            _productToBeUpdated.setPrice(product.getPrice());
            _productToBeUpdated.setBrand(product.getBrand());
            _productToBeUpdated.setStockQuantity(product.getStockQuantity());


            Product productUpdated = productService.updateProduct(_productToBeUpdated);

            if(productUpdated != null) {
                update = true;
            }
        }
        return update;
    }

    @GetMapping("/get-product/{productId}")
    public Optional<Product> listProductById(@PathVariable String productId, @RequestHeader(required = false, value = "Authorization") String authorization) {

        userHistoryService.handleUserHistoryAction(productId, authorization);

        productService.incrementProductTotalVisualization(productId);

        return productService.getProductById(productId);
    }

    @GetMapping("/list-products")
    public List<Product> listAllProducts() {
        return productService.getAllProducts();
    }

    @DeleteMapping("/delete-product/{id}")
    public boolean deleteProduct(@PathVariable String id) {
        return productService.deleteProduct(id);
    }
}
