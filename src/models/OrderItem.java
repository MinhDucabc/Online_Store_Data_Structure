package models;

import models.Book;

public class OrderItem {
    private final Book book;
    private final int quantity;
    private final double price; // giá tại thời điểm đặt

    public OrderItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.price = book.getPrice(); // lưu giá lúc tạo đơn
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public double getTotal() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return book.getTitle() + " x" + quantity + " = " + getTotal();
    }
}
