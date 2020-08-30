package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Cart;
import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    public Cart getCart(String cartId) {
        Cart cart = cartRepository.findById(cartId)
            .orElseThrow(() -> new RuntimeException("Error: Cart is not found."));

        return cart;
    }

    public String addProductOnCart(String productId, String authorization, String cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        List<Product> cartProductList = null;

        User user = userService.getUserByAuthToken(authorization)
            .orElseThrow(() -> new RuntimeException("Error: User not found"));

        Product product = productService.getProductById(productId)
            .orElseThrow(() -> new RuntimeException("Error: User not found"));

        if (!cart.isPresent()) {
            cartProductList.add(product);
            cart = Optional.of(new Cart(user.getId(), product.getPrice(), cartProductList));

            cartRepository.save(cart.get());
            return cart.get().getId();
        }

        if (isProductAlreadyOnCart(cart.get(), product.getId())) {
            incrementProductQuantityOnCart(cart.get(), productId);
        } else {
            appendProductOnCart(cart.get(), product);
        }

        return cart.get().getId();
    }

    public boolean isProductAlreadyOnCart(Cart cart, String productId) {
        boolean condition = false;
        String cartProductId;
        List<Product> cartProductsList = cart.getProductList();

        for (Product cartProduct : cartProductsList) {
            cartProductId = cartProduct.getId();

            if (cartProductId.equals(productId)) {
                condition = true;
            }
        }

        return condition;
    }

    public void incrementProductQuantityOnCart(Cart cart, String productId) {
        String cartProductId = null;
        long orderQuantity = 0;
        List<Product> cartProductsList = cart.getProductList();

        for (Product cartProduct : cartProductsList) {
            cartProductId = cartProduct.getId();

            if (cartProductId.equals(productId)) {
                orderQuantity = cartProduct.getOrderQuantity();

                cartProduct.setOrderQuantity(orderQuantity + 1);
                cart.setProductList(cartProductsList);

                cartRepository.save(cart);
            }
        }
    }

    public void appendProductOnCart(Cart cart, Product product) {
        List<Product> cartProductsList = cart.getProductList();

        cartProductsList.add(product);
        cart.setProductList(cartProductsList);

        cartRepository.save(cart);
    }
}
