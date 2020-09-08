package com.unesp.ecommerce.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class RecommendProductsService {

    public String callRecommendendApi(String userId) throws IOException {
        // API server address
        String url = "http://127.0.0.1:5000/";

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
        }

        return null;
    }
}
