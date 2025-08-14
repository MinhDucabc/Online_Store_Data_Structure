package test.Book;

import data.BookData;
import models.Book;
import services.BookService;

import java.time.LocalDate;
import java.util.List;

public class BookServiceTest {
    public static void main(String[] args) {

        // ===== 1. Khoi tao BookService voi du lieu BookData =====
        BookService bookService = new BookService(BookData.BOOKS);

        // 1a. In tat ca sach
        System.out.println("--- Tat ca sach ---");
        bookService.getAllBooks().forEach(b -> 
            System.out.println(b.getBookId() + " - " + b.getTitle() + " - " + b.getAuthor() + " - " + b.getPrice())
        );

        // 1b. Tim kiem sach theo title
        System.out.println("\n--- Tim sach voi tu khoa 'Clean' ---");
        List<Book> foundByTitle = bookService.searchByTitle("Clean");
        foundByTitle.forEach(b -> System.out.println(b.getBookId() + " - " + b.getTitle()));

        // 1c. Tim kiem sach theo author
        System.out.println("\n--- Tim sach theo tac gia 'Robert' ---");
        List<Book> foundByAuthor = bookService.searchByAuthor("Robert");
        foundByAuthor.forEach(b -> System.out.println(b.getBookId() + " - " + b.getTitle() + " - " + b.getAuthor()));

        // 1d. Tim kiem sach theo category
        System.out.println("\n--- Tim sach theo danh muc 'Computer' ---");
        List<Book> foundByCategory = bookService.searchByCategory("Computer");
        foundByCategory.forEach(b -> System.out.println(b.getBookId() + " - " + b.getTitle() + " - " + b.getCategory()));

        // ===== 2. Sap xep sach =====
        // 2a. Theo title tang dan
        System.out.println("\n--- Sap xep theo title tang dan ---");
        bookService.sortBooksByTitle(true);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle()));

        // 2b. Theo title giam dan
        System.out.println("\n--- Sap xep theo title giam dan ---");
        bookService.sortBooksByTitle(false);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle()));

        // 2c. Theo gia tang dan
        System.out.println("\n--- Sap xep theo gia tang dan ---");
        bookService.sortBooksByPrice(true);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle() + " - " + b.getPrice()));

        // 2d. Theo gia giam dan
        System.out.println("\n--- Sap xep theo gia giam dan ---");
        bookService.sortBooksByPrice(false);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle() + " - " + b.getPrice()));

        // 2e. Theo ngay xuat ban tang dan
        System.out.println("\n--- Sap xep theo ngay xuat ban tang dan ---");
        bookService.sortBooksByPublishedDate(true);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle() + " - " + b.getPublishedDate()));

        // 2f. Theo ngay xuat ban giam dan
        System.out.println("\n--- Sap xep theo ngay xuat ban giam dan ---");
        bookService.sortBooksByPublishedDate(false);
        bookService.getAllBooks().forEach(b -> System.out.println(b.getTitle() + " - " + b.getPublishedDate()));

        // ===== 3. Lay sach theo ID =====
        int testId = BookData.BOOKS.get(0).getBookId(); // lay id cua sach dau tien
        System.out.println("\n--- Lay sach theo ID = " + testId + " ---");
        Book bookById = bookService.getBookById(testId);
        System.out.println(bookById != null ? (bookById.getTitle() + " - " + bookById.getAuthor()) : "Khong tim thay sach");
    }
}
