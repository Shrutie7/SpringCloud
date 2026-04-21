package com.shrucode.payment_service.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.shrucode.payment_service.entity.Payment;
import com.shrucode.payment_service.service.PaymentService;
import jakarta.ws.rs.Path;
import org.hibernate.query.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {
@Autowired
    private PaymentService paymentService;

@PostMapping("/savePayment")
public Payment doPayment(@RequestBody Payment payment) throws JsonProcessingException {
    return paymentService.doPayment(payment);
}

@GetMapping("/{orderId}")
public List<Payment> findPaymentHistoryByOrderId(@PathVariable int orderId) throws JsonProcessingException {
    return paymentService.findPaymentHistoryByOrderId(orderId);
}




}
