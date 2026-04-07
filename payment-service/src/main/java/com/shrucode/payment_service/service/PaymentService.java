package com.shrucode.payment_service.service;

import com.shrucode.payment_service.entity.Payment;
import com.shrucode.payment_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment doPayment(Payment payment){
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setPaymentStatus(fetchPaymentStatus());
        return paymentRepository.save(payment);
    }

    public String fetchPaymentStatus(){
        //now its random but this api call happens from 3rd party payment gateway(paypal/phonepe/gpay)
        return new Random().nextBoolean()?"success":"failure";
    }
}
