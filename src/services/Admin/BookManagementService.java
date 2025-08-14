package services.Admin;

import models.Book;
import java.util.*;

public class BookManagementService {
    private List<Book> books = new ArrayList<>();

    // Create
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Đã thêm sách: " + book.getTitle());
    }

    // Read all
    public List<Book> getAllBooks() {
        return books;
    }

    // Read by ID
    public Book getBookById(int id) {
        return books.stream().filter(b -> b.getBookId() == id).findFirst().orElse(null);
    }

    // Update
    public boolean updateBook(Book updatedBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == updatedBook.getBookId()) {
                books.set(i, updatedBook);
                System.out.println("Đã cập nhật sách ID: " + updatedBook.getBookId());
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean deleteBook(int id) {
        return books.removeIf(b -> b.getBookId() == id);
    }

    // Search by title
    public List<Book> searchByTitle(String keyword) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Sort by price
    public void sortByPriceAsc() {
        books.sort(Comparator.comparingDouble(Book::getPrice));
    }
}
