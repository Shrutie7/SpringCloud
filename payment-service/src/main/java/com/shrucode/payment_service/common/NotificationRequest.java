package com.shrucode.payment_service.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequest {
    private int id;
    private int orderId;
    private String transactionId;
    private double amount;
    private PaymentStatus status;   // SUCCESS / FAILURE
    private String message;
    private NotificationType type;     // EMAIL / SMS
    private LocalDateTime createdAt;
}
