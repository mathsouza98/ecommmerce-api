package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Cart;
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
    public String buyProduct(@PathVariable String productId, @RequestHeader(required = false, value = "CartId") String cartId, @RequestHeader(value = "Authorization") String authorization) {
        return cartService.addProductOnCart(productId, cartId, authorization);
    }

    @GetMapping("/cart")
    //@PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') hasRole('USER')")
    public Cart listCartByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return cartService.getCart(authorization)
    }
}
