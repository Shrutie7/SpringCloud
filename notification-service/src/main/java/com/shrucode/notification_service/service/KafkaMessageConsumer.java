package com.shrucode.notification_service.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


// we can create more consumer instance to verify that all 3 consumer are pointing to different partition
@Service
public class KafkaMessageConsumer {
    Logger log = LoggerFactory.getLogger(KafkaMessageConsumer.class);
    @KafkaListener(topics = "shruti-topic9",groupId = "notification-group")
    public void consume(String message){
        log.info("Consumer consume the message {} ",message);
    }
    @KafkaListener(topics = "shruti-topic7",groupId = "notification-group")
    public void consume1(String message){
        log.info("Consumer1 consume the message {} ",message);
    }
    @KafkaListener(topics = "shruti-topic7",groupId = "notification-group")
    public void consume2(String message){
        log.info("Consumer2 consume the message {} ",message);
    }
    @KafkaListener(topics = "shruti-topic7",groupId = "notification-group")
    public void consume3(String message){
        log.info("Consumer3 consume the message {} ",message);
    }
    //if msg comes to 2 partition then 2 consumer instance should read msg from partition but which consumer receievs the message is decided by zookeeper coordinator not in our hand
    @KafkaListener(topics = "shruti-topic7",groupId = "notification-group")
    public void consume4(String message){
        //extra consumer for backup we have 3 partitions in topic and 3 consumer instance above
        log.info("Consumer4 consume the message {} ",message);
    }



}
