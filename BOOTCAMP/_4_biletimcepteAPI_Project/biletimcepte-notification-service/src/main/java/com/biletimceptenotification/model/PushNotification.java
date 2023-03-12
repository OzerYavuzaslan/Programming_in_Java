package com.biletimceptenotification.model;

import com.biletimceptenotification.model.enums.NotificationType;

import javax.persistence.Entity;

@Entity
public class PushNotification extends Notification {
    private String username;

    public PushNotification() {
        this.setNotificationType(NotificationType.PUSH);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}