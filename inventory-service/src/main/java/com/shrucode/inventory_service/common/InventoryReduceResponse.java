package com.shrucode.inventory_service.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReduceResponse {

    private String message;
    private InventoryStatus Status;
    private int remainingQuantity;
}
