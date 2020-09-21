package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Contact;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.repository.UserRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    public Contact getUserContact(String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);

        return user.getContact();
    }

    public void deleteUserContact(String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);

        user.setContact(null);
        userRepository.save(user);
    }

    public Contact updateUserContact(String authorization, Contact contact) {
        User user = jwtUtils.getUserByAuthorization(authorization);

        user.setContact(contact);
        userRepository.save(user);

        return user.getContact();
    }
}
