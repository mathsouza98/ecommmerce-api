package com.unesp.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserHistory {

    @Id
    String userId;
    List<History> history;

    public UserHistory(String userId, List<History> history) {
        this.userId = userId;
        this.history = history;
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
