package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {

    Bill findByOrderId(String id);
}
