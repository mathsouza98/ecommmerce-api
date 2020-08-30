package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/buy-product/{productId}")
    //@PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String buyProduct(@PathVariable String productId, @RequestHeader(required = false, value = "CartId") String cartId, @RequestHeader(value = "Authorization") String authorization) {
        return cartService.addProductOnCart(productId, cartId, authorization);
    }
}
