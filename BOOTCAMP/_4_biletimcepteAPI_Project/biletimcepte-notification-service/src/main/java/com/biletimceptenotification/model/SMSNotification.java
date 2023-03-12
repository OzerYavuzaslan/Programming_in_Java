package com.biletimceptenotification.model;

import com.biletimceptenotification.model.enums.NotificationType;

import javax.persistence.Entity;

@Entity
public class SMSNotification extends Notification {
    private String phoneNumber;

    public SMSNotification() {
        this.setNotificationType(NotificationType.SMS);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}