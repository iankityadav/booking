package com.bistro.booking.controller;

import com.bistro.booking.dto.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @MessageMapping("/notify")
    @SendTo("/topic/notifications")
    public Notification notify(Notification notification) {
        System.out.println("notification = " + notification);
        return new Notification("Called");
    }
}
