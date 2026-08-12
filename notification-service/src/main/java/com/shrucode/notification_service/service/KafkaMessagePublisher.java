package com.shrucode.notification_service.service;

import com.shrucode.notification_service.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {
    @Autowired
    private KafkaTemplate<String, Object> template;

    public void sendMessageToTopic(String message) {
        CompletableFuture<SendResult<String, Object>> future = template.send("shruti-topic7", message);

        //offset - position of message inside partition
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println(ex.getMessage());
            }
        });
    }
    public void sendJsonMessageToTopic(Customer customer) {
        try{
            CompletableFuture<SendResult<String, Object>> future = template.send("shruti-topic9", customer);

            //offset - position of message inside partition
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + customer.toString() + "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println(ex.getMessage());
                }
            });
        }catch(Exception e){
            System.out.println("Error: "+ e.getMessage());
        }

    }

}
