package com.shrucode.inventory_service.service;

import com.shrucode.inventory_service.common.InventoryReduceRequest;
import com.shrucode.inventory_service.common.InventoryReduceResponse;
import com.shrucode.inventory_service.common.InventoryStatus;
import com.shrucode.inventory_service.entity.Inventory;
import com.shrucode.inventory_service.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory getInventoryDetails(int productId){
        return inventoryRepository.findByProductId(productId); //findById() searches using primary key (id)
    }

    public Inventory addInventory(Inventory inventory){
        return inventoryRepository.save(inventory);
    }

    public InventoryReduceResponse reduceInventory(InventoryReduceRequest inventoryReduceRequest){
        InventoryReduceResponse inventoryReduceResponse = new InventoryReduceResponse();
//        Inventory inventory = new Inventory(); // will create a new row if i create a new object of entity we need to update existing row

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
        return inventoryReduceResponse;
    }


}
