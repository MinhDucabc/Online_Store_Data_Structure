package test.Admin;

import java.time.LocalDate;

import models.Book;
import services.Admin.BookManagementService;

public class BookManagementServiceTest {
    public static void main(String[] args) {
        BookManagementService service = new BookManagementService();

        // 1. Them sach
        service.addBook(new Book("Java Programming", "John Smith", 150000.0, 5, LocalDate.of(2022, 1, 1), "Programming", null));
        service.addBook(new Book("Advanced Algorithms", "Michael Brown", 200000.0, 3, LocalDate.of(2021, 6, 15), "Algorithms", null));
        service.addBook(new Book("Database Fundamentals", "David Johnson", 180000.0, 4, LocalDate.of(2020, 3, 10), "Database", null));

        // 2. Lay toan bo sach
        System.out.println("\n--- Danh sach sach ---");
        service.getAllBooks().forEach(System.out::println);

        // 3. Tim sach theo ID
        System.out.println("\n--- Tim sach ID = 2 ---");
        System.out.println(service.getBookById(2));

        // 4. Cap nhat sach
        System.out.println("\n--- Cap nhat sach ID = 3 ---");
        Book bookToUpdate = service.getBookById(3);
        if (bookToUpdate != null) {
            bookToUpdate.setTitle("Database Fundamentals (Updated Edition)");
            bookToUpdate.setAuthor("David Johnson");
            bookToUpdate.setPrice(190000.0);
            bookToUpdate.setQuantity(6);
            bookToUpdate.setPublishedDate(LocalDate.of(2023, 5, 1));
            bookToUpdate.setCategory("Database");
            service.updateBook(bookToUpdate);
        }
        service.getAllBooks().forEach(System.out::println);

        // 5. Xoa sach
        System.out.println("\n--- Xoa sach ID = 1 ---");
        service.deleteBook(1);
        service.getAllBooks().forEach(System.out::println);

        // 6. Tim kiem theo tieu de
        System.out.println("\n--- Tim kiem tu khoa 'Database' ---");
        service.searchByTitle("Database").forEach(System.out::println);

        // 7. Sap xep theo gia tang dan
        System.out.println("\n--- Sap xep theo gia tang dan ---");
        service.sortByPriceAsc();
        service.getAllBooks().forEach(System.out::println);
    }
}
