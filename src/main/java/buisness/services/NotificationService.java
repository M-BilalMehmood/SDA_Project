package buisness.services;

import buisness.models.User;

public class NotificationService {
    public void sendNotification(User recipient, String message) {
        // Logic to send notification (e.g., email, SMS, push notification)
        System.out.println("Sending notification to " + recipient.getUsername() + ": " + message);
    }
}