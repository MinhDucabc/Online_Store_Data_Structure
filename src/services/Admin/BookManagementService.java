package services.Admin;

import models.Book;

import java.util.*;
import data.BookData;

public class BookManagementService {
    private List<Book> books;

    public BookManagementService() {
        if (BookData.BOOKS == null) {
            books = new ArrayList<>();
            System.out.println("Khong co sach trong danh sach.");
        } else {
            books = BookData.BOOKS;
            if (books.isEmpty()) {
                System.out.println("Khong co sach trong danh sach.");
            } else {
                System.out.println("Da tai du lieu sach tu BookData.");
            }
        }
    }

    // Create
    public void addBook(Book book) {
        books.add(book);
        System.out.println("Da them sach: " + book.getTitle());
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
                System.out.println("Da cap nhat sach ID: " + updatedBook.getBookId());
                return true;
            }
        }
        return false;
    }

    // Delete
    public boolean deleteBook(int id) {
        boolean removed = books.removeIf(b -> b.getBookId() == id);
        return removed;
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
