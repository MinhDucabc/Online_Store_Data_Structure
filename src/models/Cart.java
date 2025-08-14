package models;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;
    private double totalPrice;

    public Cart() {
        items = new ArrayList<>();
        totalPrice = 0;
    }

    public void addItem(Book book, int quantity) {
        if (quantity <= 0) return; // bỏ qua số lượng <= 0

        for (CartItem item : items) {
            if (item.getBook().equals(book)) {
                item.setQuantity(item.getQuantity() + quantity);
                recalculateTotal();
                return;
            }
        }
        items.add(new CartItem(book, quantity));
        recalculateTotal();
    }

    public void removeItem(Book book) {
        items.removeIf(item -> item.getBook().equals(book));
        recalculateTotal();
    }

    public void updateQuantity(Book book, int quantity) {
        if (quantity <= 0) {
            removeItem(book);
            return;
        }
        for (CartItem item : items) {
            if (item.getBook().equals(book)) {
                item.setQuantity(quantity);
                break;
            }
        }
        recalculateTotal();
    }

    private void recalculateTotal() {
        totalPrice = 0;
        for (CartItem item : items) {
            totalPrice += item.getBook().getPrice() * item.getQuantity();
        }
    }

    public List<CartItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }

    public void displayCart() {
        System.out.println("===== CART =====");
        for (CartItem item : items) {
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity() +
                               " = " + (item.getBook().getPrice() * item.getQuantity()));
        }
        System.out.println("Total: " + totalPrice);
    }
}
