package com.shrucode.order_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_table")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Order {
    @Id
    private int OrderId;
    private String name;
    private int quantity;
    private double price;
}
