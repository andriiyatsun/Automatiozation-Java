package ua.edu.ukma;

public interface PaymentGateway {
    // інтерфейс сервісу шлюзів оплати
    boolean processPayment(double amount);
}