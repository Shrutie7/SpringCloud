package com.shrucode.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrucode.payment_service.entity.Payment;
import com.shrucode.payment_service.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    private Logger log = LoggerFactory.getLogger(PaymentService.class); // coming from Slf4j


    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(fetchPaymentStatus());
        log.info("PaymentServiceRequest: {} ", new ObjectMapper().writeValueAsString(payment));
        return paymentRepository.save(payment);
    }

    public String fetchPaymentStatus(){
        //now its random but this api call happens from 3rd party payment gateway(paypal/phonepe/gpay)
        return new Random().nextBoolean()?"success":"failure";
    }

    public List<Payment> findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
         List<Payment> payment  = paymentRepository.findByOrderId(orderId);
        log.info("PaymentService findPaymentHistoryByOrderId : {} ", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
