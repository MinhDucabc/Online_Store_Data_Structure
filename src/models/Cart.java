package models;

import models.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        items = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
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
        totalPrice = BigDecimal.ZERO;
        for (CartItem item : items) {
            BigDecimal price = BigDecimal.valueOf(item.getBook().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            totalPrice = totalPrice.add(price.multiply(quantity));
        }
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public List<CartItem> getItems() { return items; }
    
    public double getTotalPrice() { 
        return totalPrice.doubleValue(); 
    }

    public void displayCart() {
        System.out.println("===== CART =====");
        for (CartItem item : items) {
            BigDecimal price = BigDecimal.valueOf(item.getBook().getPrice());
            BigDecimal quantity = BigDecimal.valueOf(item.getQuantity());
            BigDecimal subtotal = price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity() +
                             " = $" + subtotal);
        }
        System.out.println("Total: $" + totalPrice);
    }
}
