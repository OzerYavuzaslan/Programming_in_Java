package com.biletimceptenotification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String notificationMessage;
    private String notificationType;
    private String contactInfo;

    @Override
    public String toString() {
        return "NotificationRequest{" +
                "notificationMessage='" + getNotificationMessage() + '\'' +
                ", notificationType='" + getNotificationType() + '\'' +
                ", contactInfo='" + getContactInfo() + '\'' +
                '}';
    }
}
