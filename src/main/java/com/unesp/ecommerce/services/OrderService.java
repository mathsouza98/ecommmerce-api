package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.*;
import com.unesp.ecommerce.payload.request.OrderRequest;
import com.unesp.ecommerce.repository.*;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentCardRepository paymentCardRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BillRepository billRepository;

    public List<Order> listOrder(String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);

        return orderRepository.findAllByUserId(user.getId());
    }

    public String saveOrder(OrderRequest orderRequest, String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Error: Cart not found"));
        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Error: Address not found"));
        PaymentCard paymentCard = paymentCardRepository.findById(orderRequest.getPaymentCardId())
                .orElseThrow(() -> new RuntimeException("Error: Payment card not found"));

        Order newOrder = new Order(
          user.getId(),
          address,
          paymentCard,
          cart
        );

        orderRepository.save(newOrder);

        Bill newBill = new Bill(
          newOrder.getId(),
          "credit card",
           orderRequest.getInstallmentNumber(),
           "executed"
        );

        billRepository.save(newBill);

        return newOrder.getId();
    }

    public Bill listBillByOrderId(String id) {
        return billRepository.findByOrderId(id);
    }
}
