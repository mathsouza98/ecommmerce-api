package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.History;
import com.unesp.ecommerce.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHistoryService {

    @Autowired
    HistoryRepository historyRepository;

    public boolean saveHistory(String productId) {

        History history = historyRepository.save(new History(productId, 1));

        if (history.getProductId() != null) {
            return true;
        }
        return false;
    }
}
