package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Contact;
import com.unesp.ecommerce.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/account")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contact")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public Contact listContactByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return contactService.getUserContact(authorization);
    }

    @DeleteMapping("/contact")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public void deleteUserContact(@RequestHeader(value = "Authorization") String authorization) {
        contactService.deleteUserContact(authorization);
    }

    @PutMapping("/contact")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public Contact updateUserContact(@RequestHeader(value = "Authorization") String authorization, @RequestBody Contact contact) {
        return contactService.updateUserContact(authorization, contact);
    }
}
