package com.shrucode.notification_service.controller;


import com.shrucode.notification_service.entity.Notification;
import com.shrucode.notification_service.repository.NotificationRepository;
import com.shrucode.notification_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    public NotificationService notificationService;

    @Autowired
    public NotificationRepository notificationRepository;
    @PostMapping("/payment")
    public Notification sendPaymentNotification(@RequestBody Notification notification){
        return notificationService.sendPaymentNotification(notification);
    }

    @GetMapping("/all")
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

}
