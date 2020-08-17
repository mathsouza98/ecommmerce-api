package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.PhysicalUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhysicalUserRepository extends MongoRepository<PhysicalUser, String> {

    Boolean existsByCpf(String cpf);
}
