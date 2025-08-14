package models;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 1;

    private final int orderId;
    private final int customerId;
    private final String customerName;
    private final List<OrderItem> items;
    private String status; // Pending, Completed

    public Order(Customer customer, List<OrderItem> items) {
        this.orderId = counter++;
        this.customerId = customer.getUserId();
        this.customerName = customer.getName();
        this.items = new ArrayList<>(items); // copy tránh sửa ngoài
        this.status = "Pending";
    }

    // Getter
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public List<OrderItem> getItems() { return items; }
    public String getStatus() { return status; }

    // Setter
    public void setStatus(String status) { this.status = status; }

    // Tính tổng tiền
    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    // In chi tiết đơn hàng
    public void printOrderDetails() {
        System.out.println("===== Order #" + orderId + " =====");
        System.out.println("Customer: " + customerName + " (ID: " + customerId + ")");
        System.out.println("Status: " + status);
        for (OrderItem item : items) {
            System.out.println("- " + item);
        }
        System.out.println("Total: " + getTotalAmount());
    }
}
