package com.unesp.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collections;
import java.util.List;

@Document
public class UserHistory {

    @Id
    String userId;
    List<History> history;

    public UserHistory(String productId, long visualization, String userId) {
        History history = new History(productId, visualization);
        this.userId = userId;
        this.history = Collections.singletonList(history);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String clientId) {
        this.userId = clientId;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }
}
