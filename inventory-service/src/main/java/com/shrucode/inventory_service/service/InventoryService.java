package com.shrucode.inventory_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shrucode.inventory_service.common.InventoryReduceRequest;
import com.shrucode.inventory_service.common.InventoryReduceResponse;
import com.shrucode.inventory_service.common.InventoryStatus;
import com.shrucode.inventory_service.entity.Inventory;
import com.shrucode.inventory_service.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;
    private Logger log = LoggerFactory.getLogger(InventoryService.class); // coming from Slf4j


    public ResponseEntity<Inventory> getInventoryDetails(int productId){
        Inventory inventory = inventoryRepository.findByProductId(productId);//findById() searches using primary key (id)
        if (inventory == null) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(inventory);
    }

    public Inventory addInventory(Inventory inventory){
        Inventory byProductId = inventoryRepository.findByProductId(inventory.getProductId());
        if(byProductId !=null){
             throw new RuntimeException("Product already exists in inventory");
        }else{
            return inventoryRepository.save(inventory);
        }
    }

    public InventoryReduceResponse reduceInventory(InventoryReduceRequest inventoryReduceRequest) throws JsonProcessingException {
        InventoryReduceResponse inventoryReduceResponse = new InventoryReduceResponse();
//        Inventory inventory = new Inventory(); // will create a new row if i create a new object of entity we need to update existing row

        //use redis cache here for db call -- ttl...........
        Inventory byProductId = inventoryRepository.findByProductId(inventoryReduceRequest.getProductId());//db call ek bar krna pura object inventory ka yhi miljaiga
        if(byProductId==null ){
            inventoryReduceResponse.setMessage("Product Not found!");
            inventoryReduceResponse.setStatus(InventoryStatus.Failure);
        }
        else{
            int quantity = byProductId.getQuantity();
            if(quantity>=inventoryReduceRequest.getQuantity()){
                int newQuantity =  quantity - inventoryReduceRequest.getQuantity();
                byProductId.setQuantity(newQuantity);
                inventoryRepository.save(byProductId);
                inventoryReduceResponse.setRemainingQuantity(newQuantity);
                inventoryReduceResponse.setStatus(InventoryStatus.Success);
                inventoryReduceResponse.setMessage("Stock reduced");
            }else{
                inventoryReduceResponse.setRemainingQuantity(quantity);
                inventoryReduceResponse.setStatus(InventoryStatus.Failure);
                inventoryReduceResponse.setMessage("Insufficient stock");
            }
        }
        log.info("InventoryService Response: {}",new ObjectMapper().writeValueAsString(inventoryReduceResponse));//to view it in JSON MODE use ObjectMapper().writeValueAsString

        return inventoryReduceResponse;
    }


}
