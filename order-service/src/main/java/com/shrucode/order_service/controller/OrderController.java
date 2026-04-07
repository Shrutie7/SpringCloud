package com.shrucode.order_service.controller;


import com.shrucode.order_service.common.TransactionRequest;
import com.shrucode.order_service.common.TransactionResponse;
import com.shrucode.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/saveorder")
    public TransactionResponse saveOrder(@RequestBody TransactionRequest transactionRequest){
       return orderService.saveOrder(transactionRequest);
    }
}
