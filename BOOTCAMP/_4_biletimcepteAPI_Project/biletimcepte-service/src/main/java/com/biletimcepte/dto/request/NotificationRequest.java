package com.biletimcepte.dto.request;

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
}
