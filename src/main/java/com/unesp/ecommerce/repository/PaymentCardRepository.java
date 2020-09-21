package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.PaymentCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentCardRepository extends MongoRepository<PaymentCard, String> {

    Boolean existsByNumber(String number);

    List<PaymentCard> findByUserId(String userId);
}
