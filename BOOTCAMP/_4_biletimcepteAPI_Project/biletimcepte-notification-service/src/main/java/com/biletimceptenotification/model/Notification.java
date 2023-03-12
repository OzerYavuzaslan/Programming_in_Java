package com.biletimceptenotification.model;

import com.biletimceptenotification.model.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
@DiscriminatorColumn(name = "notificationClass")
public abstract class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String notificationMessage;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationType=" + notificationType +
                '}';
    }
}
