package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Address;
import com.unesp.ecommerce.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/account")
public class AddressController {

    @Autowired
    AddressService addressService;

    @GetMapping("/address")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public List<Address> listAddressByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return addressService.listAddress(authorization);
    }

    @PostMapping("/address")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity saveAddress(@RequestBody Address address, @RequestHeader(value = "Authorization") String authorization) {
        return addressService.saveAddress(address, authorization);
    }

    @PutMapping("/address/{addressId}")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity updateAddress(@RequestBody Address address, @PathVariable(value = "addressId") String addressId, @RequestHeader(value = "Authorization") String authorization) {
        return addressService.updateAddress(address, addressId, authorization);
    }

    @DeleteMapping("/address/{addressId}")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity deleteAddress(@PathVariable(value = "addressId") String addressId, @RequestHeader(value = "Authorization") String authorization) {
        return addressService.deleteAddress(addressId, authorization);
    }
}
