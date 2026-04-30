package com.shrucode.order_service.common;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private int paymentId;
    private String transactionId;
    private int orderId;
    private double amount;
    private PaymentStatus paymentStatus;

    private String message;
}