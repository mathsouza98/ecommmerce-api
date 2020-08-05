package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.repository.ProductRepository;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryService {
    
    private ProductRepository productRepository;
        
    public void updateUserHistory(String id, String authorization) {
        Product product = productRepository.findById(id).get();
        product.setTotalVisualization(product.getTotalVisualization() + 1);

        JSONObject jo = new JSONObject();
        try {
            jo.put(authorization, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jo);
    }
}
