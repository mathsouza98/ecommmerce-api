package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Cart;
import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.repository.CartRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    JwtUtils jwtUtils;

    public Optional<Cart> getCart(String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);
        Optional<Cart> cart = cartRepository.findByUserId(user.getId());

        return cart;
    }

    public Product addProductOnCart(String productId, String authorization) {
        Cart newCart;
        List<Product> cartProductList = new ArrayList<Product>();

        Optional<Cart> cart = getCart(authorization);

        Product product = productService.getProductById(productId)
            .orElseThrow(() -> new RuntimeException("Error: Product not found"));

        if (!cart.isPresent()) {
            User user = jwtUtils.getUserByAuthorization(authorization);
            cartProductList.add(product);
            newCart = new Cart(user.getId(), product.getPrice(), cartProductList);

            cartRepository.save(newCart);

            return product;
        }

        if (isProductAlreadyOnCart(cart.get(), product.getId())) {
            incrementProductOrderQuantityOnCart(cart.get(), productId);
        } else {
            appendProductOnCart(cart.get(), product);
        }

        return product;
    }

    public boolean isProductAlreadyOnCart(Cart cart, String productId) {
        List<Product> cartProductsList = cart.getProductList();

        Optional<Product> cartProduct  = cartProductsList.stream().filter(product -> product.getId().equals(productId)).findFirst();

        if (cartProduct.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void incrementProductOrderQuantityOnCart(Cart cart, String productId) {
        long orderQuantity;
        float cartFinalPrice = cart.getFinalPrice();
        List<Product> cartProductsList = cart.getProductList();

        Product cartProduct  = cartProductsList.stream().filter(product -> product.getId().equals(productId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Error: Product not found on cart"));

        orderQuantity = cartProduct.getOrderQuantity();

        cartProduct.setOrderQuantity(orderQuantity + 1);
        cart.setProductList(cartProductsList);
        cart.setFinalPrice(cartFinalPrice + cartProduct.getPrice());

        cartRepository.save(cart);
    }

    public void decrementProductOrderQuantityOnCart(Cart cart, String productId) {
        long orderQuantity;
        float cartFinalPrice = cart.getFinalPrice();
        List<Product> cartProductsList = cart.getProductList();

        Product cartProduct  = cartProductsList.stream().filter(product -> product.getId().equals(productId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Error: Product not found on cart"));

        orderQuantity = cartProduct.getOrderQuantity();

        cartProduct.setOrderQuantity(orderQuantity - 1);
        cart.setProductList(cartProductsList);
        cart.setFinalPrice(cartFinalPrice + cartProduct.getPrice());

        cartRepository.save(cart);
    }

    public void appendProductOnCart(Cart cart, Product product) {
        List<Product> cartProductsList = cart.getProductList();
        float cartFinalPrice = cart.getFinalPrice();

        cartProductsList.add(product);
        cart.setProductList(cartProductsList);
        cart.setFinalPrice(cartFinalPrice + product.getPrice());

        cartRepository.save(cart);
    }

    public void deleteAllProductsOnCart(String userId) {
        Cart cart = getCart(userId)
                .orElseThrow(() -> new RuntimeException("Error: Cart not found"));
        cart.setProductList(new ArrayList<Product>());
    }

    public void deleteProductOnCart(String authorization, String productId) {
        Optional<Cart> cart = getCart(authorization);
        Cart _cart = cart.get();
        List<Product> cartProductsList = _cart.getProductList();

        Product cartProduct  = cartProductsList.stream().filter(product -> product.getId().equals(productId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Error: Product not found on cart"));

        cartProductsList.remove(cartProduct);
        _cart.setProductList(cartProductsList);
        _cart.setFinalPrice(_cart.getFinalPrice() - (cartProduct.getPrice() * cartProduct.getOrderQuantity()));

        cartRepository.save(_cart);
    }
}
