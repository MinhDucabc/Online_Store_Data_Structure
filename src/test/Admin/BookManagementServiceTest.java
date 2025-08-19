package test.Admin;

import java.time.LocalDate;

import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort.SortType;
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

        // ====================== TEST SEARCH ======================

        // 6. Tìm kiếm theo tiêu đề (Linear Search + Selection Sort)
        System.out.println("\n--- Tìm kiếm theo tiêu đề (Database) ---");
        service.searchByTitle("Database", SearchType.LINEAR, SortType.SELECTION)
                .forEach(System.out::println);

        // 7. Tìm kiếm theo tác giả (BinaryTree + Insertion Sort)
        System.out.println("\n--- Tìm kiếm theo tác giả (John) ---");
        service.searchByAuthor("John", SearchType.BINARY_TREE, SortType.INSERTION)
                .forEach(System.out::println);

        // ====================== TEST SORT ======================

        // 8. Sắp xếp theo tiêu đề (A → Z, Selection Sort)
        System.out.println("\n--- Sắp xếp theo tiêu đề tăng dần ---");
        service.sortBooksByTitle(service.getAllBooks(), true, SortType.SELECTION)
                .forEach(System.out::println);

        // 9. Sắp xếp theo tiêu đề (Z → A, Insertion Sort)
        System.out.println("\n--- Sắp xếp theo tiêu đề giảm dần ---");
        service.sortBooksByTitle(service.getAllBooks(), false, SortType.INSERTION)
                .forEach(System.out::println);

        // 10. Sắp xếp theo tác giả (A → Z, Insertion Sort)
        System.out.println("\n--- Sắp xếp theo tác giả tăng dần ---");
        service.sortBooksByAuthor(service.getAllBooks(), true, SortType.INSERTION)
                .forEach(System.out::println);

        // 11. Sắp xếp theo tác giả (Z → A, Selection Sort)
        System.out.println("\n--- Sắp xếp theo tác giả giảm dần ---");
        service.sortBooksByAuthor(service.getAllBooks(), false, SortType.SELECTION)
                .forEach(System.out::println);

        // 12. Sắp xếp theo giá (Tăng dần, Selection Sort)
        System.out.println("\n--- Sắp xếp theo giá tăng dần ---");
        service.sortBooksByPrice(service.getAllBooks(), true, SortType.SELECTION)
                .forEach(System.out::println);

        // 13. Sắp xếp theo giá (Giảm dần, Insertion Sort)
        System.out.println("\n--- Sắp xếp theo giá giảm dần ---");
        service.sortBooksByPrice(service.getAllBooks(), false, SortType.INSERTION)
                .forEach(System.out::println);

        // 14. Sắp xếp theo ngày xuất bản (Cũ → Mới, Selection Sort)
        System.out.println("\n--- Sắp xếp theo ngày xuất bản tăng dần ---");
        service.sortBooksByPublishedDate(service.getAllBooks(), true, SortType.SELECTION)
                .forEach(System.out::println);

        // 15. Sắp xếp theo ngày xuất bản (Mới → Cũ, Insertion Sort)
        System.out.println("\n--- Sắp xếp theo ngày xuất bản giảm dần ---");
        service.sortBooksByPublishedDate(service.getAllBooks(), false, SortType.INSERTION)
                .forEach(System.out::println);

    }
}
