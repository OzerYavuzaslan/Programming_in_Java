package com.biletimceptenotification.listener;


import com.biletimceptenotification.dto.NotificationRequest;
import com.biletimceptenotification.model.Notification;
import com.biletimceptenotification.model.factory.NotificationFactory;
import com.biletimceptenotification.service.INotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    private final NotificationFactory notificationFactory;
    private final INotificationService iNotificationService;

    public NotificationListener(NotificationFactory notificationFactory, INotificationService iNotificationService) {
        this.notificationFactory = notificationFactory;
        this.iNotificationService = iNotificationService;
    }

    @RabbitListener(queues = "notification")
    public void notificationListener(NotificationRequest notificationRequest) {
        System.out.println("Notification has been received: " + notificationRequest);

        Notification notification = notificationFactory.getNotification(notificationRequest.getNotificationType(), notificationRequest.getContactInfo());
        notification.setNotificationMessage(notificationRequest.getNotificationMessage());
        iNotificationService.save(notification);

        System.out.println(notification.getNotificationType() + " notification has been sent.\n" + notification);
    }
}