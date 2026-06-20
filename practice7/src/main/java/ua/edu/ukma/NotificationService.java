package ua.edu.ukma;

public interface NotificationService {
    // інтерфейс сервісу повідомлень
    void sendEmail(String orderId, String message);
}