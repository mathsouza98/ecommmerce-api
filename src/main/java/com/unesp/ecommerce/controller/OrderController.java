package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Bill;
import com.unesp.ecommerce.model.Order;
import com.unesp.ecommerce.payload.request.OrderRequest;
import com.unesp.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/account")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public List<Order> listOrderByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return orderService.listOrder(authorization);
    }

    @PostMapping("/orders")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public String requestOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(value = "Authorization") String authorization) {
        return orderService.saveOrder(orderRequest, authorization);
    }

    @GetMapping("/bill/{id}")
    @PostAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public Bill listBillsByOrderId(@PathVariable(value = "id") String id) {
        return orderService.listBillByOrderId(id);
    }
}
