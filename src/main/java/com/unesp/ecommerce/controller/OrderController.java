package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.Bill;
import com.unesp.ecommerce.model.Order;
import com.unesp.ecommerce.payload.request.OrderRequest;
import com.unesp.ecommerce.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/account")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/orders/{orderId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public Order listOrderById(@PathVariable (value = "orderId") String orderId) {
        return orderService.listOrder(orderId);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public String requestOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(value = "Authorization") String authorization) {
        return orderService.saveOrder(orderRequest, authorization);
    }

    @GetMapping("/bill/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public Bill listBillsByOrderId(@PathVariable(value = "id") String id) {
        return orderService.listBillByOrderId(id);
    }
}
