package com.shrucode.inventory_service.common;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReduceRequest {
    private int productId;

    private int quantity ;
}
