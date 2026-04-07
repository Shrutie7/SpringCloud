package com.shrucode.payment_service.controller;


import com.shrucode.payment_service.entity.Payment;
import com.shrucode.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
@Autowired
    private PaymentService paymentService;

@PostMapping("/savePayment")
public Payment doPayment(@RequestBody Payment payment){
    return paymentService.doPayment(payment);
}
}
