package com.shrucode.notification_service.entity;


import com.shrucode.notification_service.common.NotificationType;
import com.shrucode.notification_service.common.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int orderId;
    private String transactionId;
    private double amount;
    private PaymentStatus status;   // SUCCESS / FAILURE
    private String message;
    private NotificationType type;     // EMAIL / SMS
    private LocalDateTime createdAt;
}
