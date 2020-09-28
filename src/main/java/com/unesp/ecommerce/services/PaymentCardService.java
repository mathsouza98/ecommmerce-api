package com.unesp.ecommerce.services;

import com.unesp.ecommerce.model.PaymentCard;
import com.unesp.ecommerce.model.User;
import com.unesp.ecommerce.payload.response.MessageResponse;
import com.unesp.ecommerce.repository.PaymentCardRepository;
import com.unesp.ecommerce.repository.UserRepository;
import com.unesp.ecommerce.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentCardService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PaymentCardRepository paymentCardRepository;

    public List<PaymentCard> listPaymentCard(String authorization) {
        List<PaymentCard> paymentCardList = new ArrayList<PaymentCard>();
        User user = jwtUtils.getUserByAuthorization(authorization);

        paymentCardList = paymentCardRepository.findByUserId(user.getId());

        return paymentCardList;
    }

    public ResponseEntity<MessageResponse> savePaymentCard(PaymentCard paymentCard, String authorization) {
        List<PaymentCard> paymentCardList = new ArrayList<PaymentCard>();
        User user = jwtUtils.getUserByAuthorization(authorization);
        PaymentCard _paymentCard = new PaymentCard(
          user.getId(),
          paymentCard.getNumber(),
          paymentCard.getExpDate(),
          paymentCard.getBanner(),
          paymentCard.getHolderName(),
          paymentCard.getSecCode()
        );

        if(paymentCardRepository.existsByNumber(paymentCard.getNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Payment card number already taken!"));
        }

        paymentCardRepository.save(_paymentCard);

        return ResponseEntity.ok(new MessageResponse("Payment card registered successfully!"));
    }

    public ResponseEntity updatePaymentCard(PaymentCard paymentCard, String paymentCardId, String authorization) {
        Optional<PaymentCard> optionalPaymentCard = paymentCardRepository.findById(paymentCardId);
        PaymentCard _paymentCard;

        if(!optionalPaymentCard.isPresent()) return ResponseEntity
                .badRequest().body(new MessageResponse("Error: Payment card was not found!"));

        _paymentCard = optionalPaymentCard.get();

        _paymentCard.setNumber(paymentCard.getNumber());
        _paymentCard.setBanner(paymentCard.getBanner());
        _paymentCard.setExpDate(paymentCard.getExpDate());
        _paymentCard.setHolderName(paymentCard.getHolderName());
        _paymentCard.setSecCode(paymentCard.getSecCode());

        paymentCardRepository.save(_paymentCard);

        return ResponseEntity.ok(new MessageResponse("Payment card successfully updated!"));
    }

    public ResponseEntity deletePaymentCard(String paymentCardId, String authorization) {
        Optional<PaymentCard> paymentCardToBeDeleted = paymentCardRepository.findById(paymentCardId);

        if (!paymentCardToBeDeleted.isPresent()) return ResponseEntity
                .badRequest().body(new MessageResponse("Error: Payment card was not found!"));

        paymentCardRepository.delete(paymentCardToBeDeleted.get());

        return ResponseEntity.ok(new MessageResponse("Payment card successfully deleted!"));
    }
}
