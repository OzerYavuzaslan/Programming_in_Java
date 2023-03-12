package com.biletimceptenotification.service;

import com.biletimceptenotification.model.Notification;
import com.biletimceptenotification.repository.INotificationRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class NotificationService implements INotificationService{
    private INotificationRepository iNotificationRepository;

    public NotificationService(INotificationRepository iNotificationRepository) {
        setINotificationRepository(iNotificationRepository);
    }

    @Override
    public void save(Notification notification) {
        getINotificationRepository().save(notification);
    }
}
