package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart/{productId}")
    //@PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Product buyProduct(@PathVariable String productId, @RequestHeader(value = "Authorization") String authorization) {
        return cartService.addProductOnCart(productId, authorization);
    }
}
