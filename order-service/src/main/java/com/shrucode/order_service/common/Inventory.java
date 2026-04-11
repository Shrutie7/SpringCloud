package com.shrucode.order_service.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    private int id;

    private int productId;

    private String productName;

    private int quantity;

    private double price;

    private LocalDateTime lastUpdated;
}
