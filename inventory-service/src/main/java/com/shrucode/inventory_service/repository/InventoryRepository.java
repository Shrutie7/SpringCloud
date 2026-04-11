package com.shrucode.inventory_service.repository;

import com.shrucode.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory,Integer> {
    Inventory findByProductId(int productId);
}
