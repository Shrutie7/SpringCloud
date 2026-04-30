package com.shrucode.payment_service.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE")
public interface NotificationFeignClient {
    @PostMapping("/notification/payment")
    void sendPaymentNotification(@RequestBody NotificationRequest notificationRequest);
}
