package com.shrucode.order_service.common;

import com.shrucode.order_service.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    private Order order;
    private Payment payment;
}
