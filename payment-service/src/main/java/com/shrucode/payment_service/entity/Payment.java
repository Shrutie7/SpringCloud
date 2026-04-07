package com.shrucode.payment_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payment_table")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Payment {
@Id
@GeneratedValue
private int paymentId;
private String transactionId;
private int orderId;
private double amount;
private String paymentStatus;
}
