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
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    JwtUtils jwtUtils;

    
}
