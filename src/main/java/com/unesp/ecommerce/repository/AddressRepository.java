package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends MongoRepository<Address, String> {

    boolean existsAddressByZip(String zip);
    List<Address> findAllByUserId(String userId);
}
