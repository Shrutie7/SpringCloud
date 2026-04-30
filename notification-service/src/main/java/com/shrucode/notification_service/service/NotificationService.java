package com.shrucode.notification_service.service;


import com.shrucode.notification_service.common.NotificationType;
import com.shrucode.notification_service.entity.Notification;
import com.shrucode.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService {

    @Autowired
    public NotificationRepository notificationRepository;


    public Notification sendPaymentNotification(Notification request){
        Notification notification = new Notification();

        notification.setOrderId(request.getOrderId());
        notification.setTransactionId(request.getTransactionId());
        notification.setAmount(request.getAmount());
        notification.setStatus(request.getStatus());
        notification.setMessage(request.getMessage());
        notification.setType(NotificationType.EMAIL);
        notification.setCreatedAt(LocalDateTime.now());

        return notificationRepository.save(notification);
    }
}
