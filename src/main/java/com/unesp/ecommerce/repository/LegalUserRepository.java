package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.LegalUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LegalUserRepository extends MongoRepository<LegalUser, String> {

    boolean existsByCnpj(String cpf);
}
