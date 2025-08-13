package models;

public class Book {
    private static int counter = 1;
    private int bookId;
    private String title;
    private String author;
    private int quantity;
    private String publishedDate; // Ngày xuất bản (dùng String hoặc LocalDate)

    public Book(String title, String author, int quantity, String publishedDate) {
        this.bookId = counter++;
        this.title = title;
        this.author = author;
        this.quantity = quantity;
        this.publishedDate = publishedDate;
    }
    @Override
    public String toString() {
        return "Book {" +
                    "title=" + title + '\'' +
                    ", author=" + author + '\'' +
                    ", quantity=" + quantity +
                    ", publishedDate=" + publishedDate + '\'' +
                '}';
    }

    // Getter & Setter (nếu cần)
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPublishedDate() {
        return publishedDate;
    }
}
