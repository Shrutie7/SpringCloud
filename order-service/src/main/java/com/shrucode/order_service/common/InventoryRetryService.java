package com.shrucode.order_service.common;


import com.shrucode.order_service.entity.Order;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;

//making seperate inventory service for retry
//Because your @Retry likely is not active due to same-class invocation.
//Instead, Feign exception bubbles out directly.
@Service
public class InventoryRetryService {
    @Autowired
    private InventoryFeignClient inventoryFeignClient;

    private int attempt = 1;
    @Retry(name="inventoryService", fallbackMethod="orderFallBackInventory")
    public InventoryReduceResponse callInventory(Order order) {
        InventoryReduceRequest inventoryReduceRequest = new InventoryReduceRequest();
        inventoryReduceRequest.setProductId(order.getProductId());
        inventoryReduceRequest.setQuantity(order.getQuantity());

        System.out.println("retry method called "+attempt++ +" times at "+new Date());
        return inventoryFeignClient.reduceInventory(inventoryReduceRequest);
    }
    public InventoryReduceResponse orderFallBackInventory(Order order,Throwable t){
        //keep same return type in fallBackmethod also as in saveOrder same exact signature

        InventoryReduceResponse response = new InventoryReduceResponse();
        response.setStatus(InventoryStatus.Failure);
        response.setMessage("Inventory service unavailable after retries");

        return response;
    }
}

