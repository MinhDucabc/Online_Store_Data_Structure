package models;

import models.OrderItem;
import structures.orderstack.OrderStack;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 1;

    private final int orderId;
    private final int customerId;
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStack statusStack;

    public Order(Customer customer, List<OrderItem> items) {
        this.orderId = counter++;
        this.customerId = customer.getUserId();
        this.customerName = customer.getName();
        this.items = new ArrayList<>(items); 
        this.statusStack = new OrderStack(); 
    }

    // Getter
    public int getOrderId() { return orderId; }
    public int getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public List<OrderItem> getItems() { return items; }

    // Tính tổng tiền
    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getTotal).sum();
    }

    // Status Management
    public void updateStatus(String newStatus) {
        statusStack.pushStatus(newStatus);
    }

    public String getCurrentStatus() {
        return statusStack.currentStatus();
    }

    public void undoStatus() {
        statusStack.popStatus();
    }

    // In chi tiết đơn hàng
    public void printOrderDetails() {
        System.out.println("===== Order #" + orderId + " =====");
        System.out.println("Customer: " + customerName + " (ID: " + customerId + ")");
        System.out.println("Status: " + getCurrentStatus());
        for (OrderItem item : items) {
            System.out.println("- " + item);
        }
        System.out.println("Total: " + getTotalAmount());
        System.out.println("\n");
    }

    

}
