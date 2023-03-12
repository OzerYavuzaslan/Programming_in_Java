package com.biletimceptenotification.model.factory;

import com.biletimceptenotification.model.EmailNotification;
import com.biletimceptenotification.model.Notification;
import com.biletimceptenotification.model.PushNotification;
import com.biletimceptenotification.model.SMSNotification;
import org.springframework.stereotype.Component;

@Component
public class NotificationFactory {
    public Notification getNotification(String notificationType, String contactInfo) {
        if (notificationType == null)
            return null;

        if (notificationType.equalsIgnoreCase("SMS")) {
            SMSNotification smsNotification=new SMSNotification();
            smsNotification.setPhoneNumber(contactInfo);
            return smsNotification;
        } else if (notificationType.equalsIgnoreCase("EMAIL")) {
            EmailNotification emailNotification=new EmailNotification();
            emailNotification.setEmailAddress(contactInfo);
            return emailNotification;
        } else if (notificationType.equalsIgnoreCase("PUSH")) {
            PushNotification pushNotification=new PushNotification();
            pushNotification.setUsername(contactInfo);
            return pushNotification;
        }

        return null;
    }
}