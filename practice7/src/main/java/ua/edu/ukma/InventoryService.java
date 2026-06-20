package ua.edu.ukma;

public interface InventoryService {
    // інтерфейс сервісу управління складом
    boolean checkStock(String productId, int quantity);
    void decreaseStock(String productId, int quantity);
}
