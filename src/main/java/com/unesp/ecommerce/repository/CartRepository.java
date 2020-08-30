package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {
}
