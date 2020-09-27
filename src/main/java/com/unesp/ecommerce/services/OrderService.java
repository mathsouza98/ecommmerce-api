package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.*;
import com.unesp.ecommerce.payload.request.OrderRequest;
import com.unesp.ecommerce.repository.*;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    CartService cartService;

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

    public Order listOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Error: Order not found"));

        return order;
    }

    public String saveOrder(OrderRequest orderRequest, String authorization) {
        User user = jwtUtils.getUserByAuthorization(authorization);
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Error: Cart not found"));
        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new RuntimeException("Error: Address not found"));
        PaymentCard paymentCard = paymentCardRepository.findById(orderRequest.getPaymentCardId())
                .orElseThrow(() -> new RuntimeException("Error: Payment card not found"));

        cart.setCloseDate(new Date());

        cartRepository.save(cart);

        Order newOrder = new Order(
          user.getId(),
          address,
          paymentCard,
          cart
        );

        orderRepository.save(newOrder);

        Bill newBill = new Bill(
          newOrder.getId(),
          "cartão de crédito",
           orderRequest.getInstallmentNumber(),
           "executed"
        );

        billRepository.save(newBill);

        cartService.deleteAllProductsOnCart(user.getId());

        return newOrder.getId();
    }

    public Bill listBillByOrderId(String id) {
        return billRepository.findByOrderId(id);
    }
}
