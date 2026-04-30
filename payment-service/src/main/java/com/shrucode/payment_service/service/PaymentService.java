package com.shrucode.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrucode.payment_service.common.*;
import com.shrucode.payment_service.entity.Payment;
import com.shrucode.payment_service.repository.PaymentRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private NotificationFeignClient notificationFeignClient;
    private Logger log = LoggerFactory.getLogger(PaymentService.class); // coming from Slf4j


    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setTransactionId(UUID.randomUUID().toString());
        PaymentStatus status = fetchPaymentStatus();
        payment.setPaymentStatus(status);
        log.info("PaymentServiceRequest: {} ", new ObjectMapper().writeValueAsString(payment));
        if(PaymentStatus.SUCCESS.equals(status)){
            NotificationRequest notificationRequest = new NotificationRequest();
            notificationRequest.setType(NotificationType.EMAIL);
            notificationRequest.setStatus(PaymentStatus.SUCCESS);
            notificationRequest.setMessage("Payment done successfully");
            notificationRequest.setOrderId(payment.getOrderId());
            notificationRequest.setTransactionId(payment.getTransactionId());
            notificationRequest.setAmount(payment.getAmount());
            notificationFeignClient.sendPaymentNotification(notificationRequest);
        }
        return paymentRepository.save(payment);
    }
    public PaymentStatus fetchPaymentStatus(){
        //now its random but this api call happens from 3rd party payment gateway(paypal/phonepe/gpay)
        return new Random().nextBoolean()? PaymentStatus.SUCCESS :PaymentStatus.FAILURE;
    }

    public List<Payment> findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
         List<Payment> payment  = paymentRepository.findByOrderId(orderId);
        log.info("PaymentService findPaymentHistoryByOrderId : {} ", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
