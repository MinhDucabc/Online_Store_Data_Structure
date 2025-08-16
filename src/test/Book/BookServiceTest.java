package test.Book;

import models.Book;
import services.BookService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookServiceTest {
    public static void main(String[] args) {
        // 1. Tao service
        BookService service = new BookService();
        
        // 3. Lay toan bo sach
        System.out.println("\n--- Danh sach sach ---");
        service.getAllBooks().forEach(System.out::println);

        // 4. Tim sach theo ID
        System.out.println("\n--- Tim sach ID = 2 ---");
        System.out.println(service.getBookById(2));

        // 5. Tim kiem theo tieu de
        System.out.println("\n--- Tim kiem tu khoa 'Basics' ---");
        service.searchByTitle("Basics").forEach(System.out::println);

        // 6. Tim kiem theo tac gia
        System.out.println("\n--- Tim kiem tac gia 'John' ---");
        service.searchByAuthor("John").forEach(System.out::println);

        // 7. Tim kiem theo category
        System.out.println("\n--- Tim kiem category 'Programming' ---");
        service.searchByCategory("Programming").forEach(System.out::println);

        // 8. Sap xep theo tieu de tang dan
        System.out.println("\n--- Sap xep theo tieu de tang dan ---");
        service.sortBooksByTitle(true);
        service.getAllBooks().forEach(System.out::println);

        // 9. Sap xep theo gia giam dan
        System.out.println("\n--- Sap xep theo gia giam dan ---");
        service.sortBooksByPrice(false);
        service.getAllBooks().forEach(System.out::println);

        // 10. Sap xep theo ngay xuat ban tang dan
        System.out.println("\n--- Sap xep theo ngay xuat ban tang dan ---");
        service.sortBooksByPublishedDate(true);
        service.getAllBooks().forEach(System.out::println);
    }
}
