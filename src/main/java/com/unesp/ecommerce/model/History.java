package com.unesp.ecommerce.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class History {

    @Id
    private String id;
    private final String productId;
    private long visualization;

    public History(String productId, long visualization) {
        this.productId = productId;
        this.visualization = visualization;
    }

    public String getProductId() {
        return productId;
    }

    public long getVisualization() {
        return visualization;
    }

    public String getId() {
        return id;
    }

    public void setVisualization(long visualization) {
        this.visualization = visualization;
    }
}
