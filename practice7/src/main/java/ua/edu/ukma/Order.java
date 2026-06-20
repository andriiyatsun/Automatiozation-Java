package ua.edu.ukma;

/**
 * @author Andrii Yatsun
 */

public class Order {
    private String id;
    private String productId;
    private int quantity;
    private double amount;

    //дефолтний конструктор
    public Order(String id, String productId, int quantity, double amount) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }

    // гетери
    public String getId() { return id; }
    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getAmount() { return amount; }
}