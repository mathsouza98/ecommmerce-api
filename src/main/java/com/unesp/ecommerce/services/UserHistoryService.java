package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.History;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.model.UserHistory;
import com.unesp.ecommerce.repository.HistoryRepository;
import com.unesp.ecommerce.repository.UserHistoryRepository;
import com.unesp.ecommerce.repository.UserRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserHistoryService {

    @Autowired
    UserHistoryRepository userHistoryRepository;

    @Autowired
    HistoryRepository historyRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserRepository userRepository;

    public void handleUserHistoryAction(String productId, String authorization) {
        Optional<UserHistory> userHistory = getUserHistoryByAuthToken(authorization);
        if (userHistory.isPresent()) {
            System.out.println(userHistory);
            UpdateHistory(userHistory, productId);
        } else {
            saveNewHistory(productId, authorization);
        }
    }

    public void saveNewHistory(String productId, String authorization) {
        Optional<User> user = getUserByAuthToken(authorization);

        userHistoryRepository.save(new UserHistory(productId, 1, user.get().getId()));
    }

    public void UpdateHistory(Optional<UserHistory> userHistory, String productId) {
        List<History> history = userHistory.get().getHistory();
        System.out.println(history.toString());
        /*for(History h : history) {
            if(productId == h.getProductId()) {
                long totalVisualization = h.getVisualization();
                h.setVisualization(totalVisualization + 1);
            }
        }
        history.add(new History(productId, 1));*/
    }

    public Optional<UserHistory> getUserHistoryByAuthToken(String authorization) {
        Optional<UserHistory> userHistory = null;
        Optional<User> user = getUserByAuthToken(authorization);

        if(user.isPresent()) {
            String userId = user.get().getId();

            userHistory = userHistoryRepository.findByUserId(userId);
        }
        return userHistory;
    }

    public Optional<User> getUserByAuthToken(String authorization) {
        String username = jwtUtils.getUserNameFromJwtToken(authorization);

        return userRepository.findByUsername(username);
    }
}
