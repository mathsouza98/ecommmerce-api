package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Address;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.AddressRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AddressRepository addressRepository;

    public List<Address> listAddress(String authorization) {
        List<Address> AddressList;
        User user = jwtUtils.getUserByAuthorization(authorization);

        AddressList = addressRepository.findAllByUserId(user.getId());

        return AddressList;
    }

    public ResponseEntity<MessageResponse> saveAddress(Address inputAddress, String authorization) {
        List<Address> AddressList = new ArrayList<Address>();
        User user = jwtUtils.getUserByAuthorization(authorization);
        Address address = new Address(
                user.getId(),
                inputAddress.getStreet(),
                inputAddress.getNumber(),
                inputAddress.getNeighborhood(),
                inputAddress.getZip(),
                inputAddress.getCity(),
                inputAddress.getFedUnit()
        );

        if(addressRepository.existsAddressByZip(address.getZip())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Zip code already taken!"));
        }

        addressRepository.save(address);

        return ResponseEntity.ok(new MessageResponse("Address registered successfully!"));
    }

    public ResponseEntity updateAddress(Address inputAddress, String addressId, String authorization) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        Address address;

        if(!optionalAddress.isPresent()) return ResponseEntity
                .badRequest().body(new MessageResponse("Error: Address was not found!"));

        address = optionalAddress.get();

        address.setStreet(inputAddress.getStreet());
        address.setNumber(inputAddress.getNumber());
        address.setNeighborhood(inputAddress.getNeighborhood());
        address.setZip(inputAddress.getZip());
        address.setCity(inputAddress.getCity());
        address.setFedUnit(inputAddress.getFedUnit());

        addressRepository.save(address);

        return ResponseEntity.ok(new MessageResponse("Address successfully updated!"));
    }

    public ResponseEntity deleteAddress(String addressId, String authorization) {
        Optional<Address> addressToBeDeleted = addressRepository.findById(addressId);

        if (!addressToBeDeleted.isPresent()) return ResponseEntity
                .badRequest().body(new MessageResponse("Error: Address was not found!"));

        addressRepository.delete(addressToBeDeleted.get());

        return ResponseEntity.ok(new MessageResponse("Address successfully deleted!"));
    }
}
