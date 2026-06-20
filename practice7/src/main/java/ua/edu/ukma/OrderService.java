package ua.edu.ukma;

import java.util.List;

public class OrderService {
    // сервіс
    private final InventoryService inventoryService;
    // оплата
    private final PaymentGateway paymentGateway;
    // повідомлення
    private final NotificationService notificationService;

    public OrderService(InventoryService inventoryService, PaymentGateway paymentGateway, NotificationService notificationService) {
        this.inventoryService = inventoryService;
        this.paymentGateway = paymentGateway;
        this.notificationService = notificationService;
    }

    // розмістити замовлення
    public boolean placeOrder(Order order) {
        // Перевірка складу
        if (inventoryService.checkStock(order.getProductId(), order.getQuantity())) {
            // перевірка оплати
            if (paymentGateway.processPayment(order.getAmount())) {
                // фіксація на складі та сповіщення
                inventoryService.decreaseStock(order.getProductId(), order.getQuantity());
                notificationService.sendEmail(order.getId(), "Payment successful");
                return true;
            }
        }
        return false;
    }

    // отримати список останніх замовлень
    public List<Order> getRecentOrders() {
        return List.of(
                new Order("1", "PROD_A", 2, 100.0),
                new Order("2", "PROD_B", 1, 50.0),
                new Order("3", "PROD_C", 5, 500.0)
        );
    }

    // розрахунок знижки
    public double calculateDiscount(double totalAmount) {
        if (totalAmount >= 1000.0) {
            return totalAmount * 0.9;
        }
        return totalAmount;
    }
}