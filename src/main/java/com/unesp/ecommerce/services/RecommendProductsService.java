package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.Product;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendProductsService {

    @Autowired
    JwtUtils jwtUtils;

    public StringBuffer callRecommendendApi(String authorization) throws IOException {
        User user;
        String userId = null;

        // API server address
        String url = "http://127.0.0.1:5000/";

        user = jwtUtils.getUserByAuthorization(authorization);
        userId = user.getId();

        if (userId == null) {
            System.out.println("\n Requisição GET para a URL: " + url);

            // Instantiating URL object and establishing connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // GET request for local server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            // Getting server response
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Shows recommended products on screen
            System.out.println(response.toString());

            return response;
        }
        else {
            System.out.println("\n Requisição GET para a URL: " + url + userId);

            // Instantiating URL object for passed userId and establishing connection
            URL obj = new URL(url + userId);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // GET request for local server
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer response = new StringBuffer();

            // Getting server response
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Shows recommended products on screen
            System.out.println(response.toString());

            return response;
        }
    }
}
