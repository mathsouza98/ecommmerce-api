package com.unesp.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "user-history")
public class userHistory extends User {
    private List<String> history;

    public userHistory(String username, String password) {
        super(username, password);
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
