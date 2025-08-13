package models;

import java.util.ArrayList;

public class Order {
    public static int counter = 1;

    private int orderId;
    private int customerId;      // liên kết tới Customer qua ID
    private String customerName; // lưu tên để hiển thị nhanh
    private ArrayList<Book> products;
    private ArrayList<Integer> quantities;
    private String status; // {Pending, Complete}

    // Constructor từ Customer
    public Order(Customer customer, ArrayList<Book> products, ArrayList<Integer> quantities) {
        this.orderId = counter++;
        this.customerId = customer.getCustomerId(); // lưu ID
        this.customerName = customer.getName();     // lưu tên
        this.products = products;
        this.quantities = quantities;
        this.status = "Pending";
    }

    // Getter
    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public ArrayList<Book> getProducts() {
        return products;
    }

    public ArrayList<Integer> getQuantities() {
        return quantities;
    }

    public String getStatus() {
        return status;
    }

    // Setter
    public void setStatus(String status) {
        this.status = status;
    }

    // In thông tin
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
