package com.shrucode.inventory_service.controller;


import com.shrucode.inventory_service.common.InventoryReduceRequest;
import com.shrucode.inventory_service.common.InventoryReduceResponse;
import com.shrucode.inventory_service.entity.Inventory;
import com.shrucode.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("{productId}")
    public Inventory getInventoryDetails(@PathVariable int productId){
        return inventoryService.getInventoryDetails(productId);
    }

    @PostMapping("/addInventory")
    public Inventory addInventory(@RequestBody Inventory inventory){
        return inventoryService.addInventory(inventory);
    }

    @PutMapping("/reduce")
    public InventoryReduceResponse reduceInventory(@RequestBody InventoryReduceRequest inventoryReduceRequest){
        return inventoryService.reduceInventory(inventoryReduceRequest);
    }
}
