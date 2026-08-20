package com.company.notificationservice;

import com.company.notificationservice.dto.CreateNotificationRequest;
import com.company.notificationservice.entity.Notification;
import com.company.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of(
                "service", "notification-service",
                "status", "UP"
        );
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                notificationService.getNotificationById(id)
        );
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @Valid @RequestBody CreateNotificationRequest request) {

        Notification notification = new Notification();
        notification.setUserId(request.getUserId());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notificationService.createNotification(notification));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notification> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody CreateNotificationRequest request) {

        Notification details = new Notification();
        details.setUserId(request.getUserId());
        details.setMessage(request.getMessage());
        details.setType(request.getType());

        return ResponseEntity.ok(
                notificationService.updateNotification(id, details)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Notification deleted successfully",
                        "id", String.valueOf(id)
                )
        );
    }
}
