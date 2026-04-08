package com.shrucode.payment_service.repository;

import com.shrucode.payment_service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    List<Payment> findByOrderId(int orderId);
}
