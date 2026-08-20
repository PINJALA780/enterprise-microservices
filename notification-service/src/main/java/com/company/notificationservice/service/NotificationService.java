package com.company.notificationservice.service;

import com.company.notificationservice.entity.Notification;
import com.company.notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Notification not found with id: " + id));
    }

    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, Notification details) {
        Notification notification = getNotificationById(id);

        notification.setUserId(details.getUserId());
        notification.setMessage(details.getMessage());
        notification.setType(details.getType());
        notification.setStatus(details.getStatus());

        return notificationRepository.save(notification);
    }

    public void deleteNotification(Long id) {
        Notification notification = getNotificationById(id);
        notificationRepository.delete(notification);
    }
}
