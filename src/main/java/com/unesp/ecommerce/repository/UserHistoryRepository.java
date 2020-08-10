package com.unesp.ecommerce.repository;

import com.unesp.ecommerce.model.UserHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserHistoryRepository extends MongoRepository<UserHistory, String> {

    Optional<UserHistory> findByUserId(String userId);
}
