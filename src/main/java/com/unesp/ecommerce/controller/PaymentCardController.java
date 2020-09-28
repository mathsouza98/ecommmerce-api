package com.unesp.ecommerce.controller;

import com.unesp.ecommerce.model.PaymentCard;
import com.unesp.ecommerce.services.PaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/account")
public class PaymentCardController {

    @Autowired
    PaymentCardService paymentCardService;

    @GetMapping("/payment-card")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public List<PaymentCard> listPaymentCardByAuthToken(@RequestHeader(value = "Authorization") String authorization) {
        return paymentCardService.listPaymentCard(authorization);
    }

    @PostMapping("/payment-card")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity savePaymentCard(@RequestBody PaymentCard paymentCard, @RequestHeader(value = "Authorization") String authorization) {
        return paymentCardService.savePaymentCard(paymentCard, authorization);
    }

    @PutMapping("/payment-card/{paymentCardId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity updatePaymentCard(@RequestBody PaymentCard paymentCard, @PathVariable(value = "paymentCardId") String paymentCardId, @RequestHeader(value = "Authorization") String authorization) {
        return paymentCardService.updatePaymentCard(paymentCard, paymentCardId, authorization);
    }

    @DeleteMapping("/payment-card/{paymentCardId}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity deletePaymentCard(@PathVariable(value = "paymentCardId") String paymentCardId, @RequestHeader(value = "Authorization") String authorization) {
        return paymentCardService.deletePaymentCard(paymentCardId, authorization);
    }
}
