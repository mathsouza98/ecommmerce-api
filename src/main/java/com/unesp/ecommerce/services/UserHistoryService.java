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

import java.util.ArrayList;
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

    @Autowired
    UserService userService;

    public void handleUserHistoryAction(String productId, String authorization) {
        Optional<UserHistory> userHistory = getUserHistoryByAuthorization(authorization);

        if (userHistory.isPresent()) {
            UpdateHistory(userHistory.get().getUserId(), productId);
        } else {
            saveNewHistory(productId, authorization);
        }
    }

    public void saveNewHistory(String productId, String authorization) {
        User user = userService.getUserByAuthorization(authorization);
        List<History> history = new ArrayList<History>();

        history.add(new History(productId, 1));

        userHistoryRepository.save(new UserHistory(user.getId(), history));
    }

    public void UpdateHistory(String userId, String productId) {
        boolean isAlreadyOnUserHistory = false;
        Optional<UserHistory> userHistoryToUpdate = userHistoryRepository.findByUserId(userId);
        List<History> history = userHistoryToUpdate.get().getHistory();

        for (History h : history) {
            if (h.getProductId().equals(productId)) {
                long totalVisualization = h.getVisualization();

                h.setVisualization(totalVisualization + 1);
                isAlreadyOnUserHistory = true;
            }
        }

        if (!isAlreadyOnUserHistory) {
            history.add(new History(productId, 1));
        }

        userHistoryToUpdate.get().setHistory(history);
        userHistoryRepository.save(userHistoryToUpdate.get());
    }

    public Optional<UserHistory> getUserHistoryByAuthorization(String authorization) {
        Optional<UserHistory> userHistory = null;
        User user = userService.getUserByAuthorization(authorization);
        String userId = user.getId();

        return userHistoryRepository.findByUserId(userId);
    }
}
