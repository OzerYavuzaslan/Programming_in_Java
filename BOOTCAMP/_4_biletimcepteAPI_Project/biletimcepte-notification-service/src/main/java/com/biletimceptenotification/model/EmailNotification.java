package com.biletimceptenotification.model;

import com.biletimceptenotification.model.enums.NotificationType;

import javax.persistence.Entity;

@Entity
public class EmailNotification extends Notification{
    private String emailAddress;

    public EmailNotification() {
        this.setNotificationType(NotificationType.EMAIL);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
