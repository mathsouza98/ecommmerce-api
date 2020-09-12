package com.unesp.ecommerce.controller;

<<<<<<< HEAD
import com.unesp.ecommerce.model.Cart;
=======
import com.unesp.ecommerce.model.Product;
>>>>>>> c78fe29a8a96e6a4fd037022a972461747571675
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

    @GetMapping("/cart")
    //@PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') hasRole('USER')")
    public Cart listCartByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return cartService.getCart(authorization)
    }
}
