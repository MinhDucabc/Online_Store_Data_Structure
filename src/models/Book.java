package models;

import java.time.LocalDate;

public class Book {
    private static int counter = 1;
    private final int bookId;
    private String title;
    private String author;
    private double price;
    private int quantity;
    private LocalDate publishedDate;
    private String category;
    private String image; // optional: đường dẫn hoặc URL ảnh

    // Constructor với ảnh tùy chọn
    public Book(String title, String author, double price, int quantity, LocalDate publishedDate,
            String category, String image) {
        this.bookId = counter++;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
        this.publishedDate = publishedDate;
        this.category = category;
        this.image = image != null ? image : "No Image Available";
    }

    // Getters
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", publishedDate=" + publishedDate +
                ", category='" + category + '\'' +
                ", image='" + (image != null ? image : "No image available") + '\'' +
                '}';
    }
}
