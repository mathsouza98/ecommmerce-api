package com.unesp.ecommerce.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class History {

    String productId;
    long visualization;

    public History(String productId, long visualization) {
        this.productId = productId;
        this.visualization = visualization;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getVisualization() {
        return visualization;
    }

    public void setVisualization(long visualization) {
        this.visualization = visualization;
    }
}
