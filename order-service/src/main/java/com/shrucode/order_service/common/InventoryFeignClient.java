package com.shrucode.order_service.common;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

//Feign = same as Controller, but inside interface
//translate controller APIs into Feign methods.
@FeignClient(name = "INVENTORY-SERVICE")
public interface InventoryFeignClient {
    @GetMapping("/inventory/{productId}")
    Inventory getInventoryDetails(@PathVariable int productId);

    @PutMapping("/inventory/reduce")
    InventoryReduceResponse reduceInventory(@RequestBody InventoryReduceRequest request);

//    @PostMapping("/inventory/add")
//    Inventory addInventory(@RequestBody Inventory inventory);

}
