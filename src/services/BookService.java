package services;

import algorithms.GenericSearch;
import algorithms.GenericSort;
import data.BookData;
import models.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class BookService {

    private final List<Book> books; // Danh sách sách hiện có

    // Constructor mặc định: lấy dữ liệu từ BookData
    public BookService() {
        if (BookData.BOOKS != null && !BookData.BOOKS.isEmpty()) {
            this.books = BookData.BOOKS;
            System.out.println("Đa tai du lieu sach tu BookData.");
        } else {
            this.books = new ArrayList<>();
            System.out.println("Chua co sach trong BookData.");
        }
    }
    // ===== 1. Lấy tất cả sách (có thể phân trang nếu muốn) =====
    public List<Book> getAllBooks() {
        return new ArrayList<>(books); // trả về copy tránh sửa ngoài ý muốn
    }

    // ===== 2. Tìm kiếm sách =====
    /**
     * @param keyword từ khóa tìm kiếm
     * @param fields  danh sách field extractors: ví dụ Book::getTitle,
     *                Book::getAuthor
     * @return danh sách sách match
     */
    
    public List<Book> searchByTitle(String keyword) {
        return GenericSearch.linearSearch(books, keyword, Book::getTitle);
    }

    public List<Book> searchByAuthor(String keyword) {
        return GenericSearch.linearSearch(books, keyword, Book::getAuthor);
    }

    // ===== 3. Sắp xếp sách =====
    public List<Book> sortBooksByTitle(List<Book> bookList, boolean ascending) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(sortedList, comp);
        return sortedList;
    }

    public List<Book> sortBooksByAuthor(List<Book> bookList, boolean ascending) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(sortedList, comp);
        return sortedList;
    }

    public List<Book> sortBooksByPrice(List<Book> bookList, boolean ascending) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparingDouble(Book::getPrice);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(sortedList, comp);
        return sortedList;
    }

    public List<Book> sortBooksByPublishedDate(List<Book> bookList, boolean ascending) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getPublishedDate);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(sortedList, comp);
        return sortedList;
    }

    // ===== 4. Lấy sách theo ID =====
    public Book getBookById(int bookId) {
        return books.stream()
                .filter(b -> b.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }

    public List<Book> getBooksByIds(List<Integer> bookIds) {
        List<Book> result = new ArrayList<>();
        for (int id : bookIds) {
            Book book = getBookById(id);
            if (book != null) {
                result.add(book);
            }
        }
        return result;
    }

    // Get book by category
    public List<Book> getBooksByCategory(String category) {
        if (category == null || category.isEmpty()) {
            return new ArrayList<>(books); // Trả về tất cả sách nếu không có category
        }
        return books.stream()
                .filter(b -> b.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    // Get All Categories
    public List<String> getAllCategories() {
        return books.stream()
                .map(Book::getCategory)
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }
}
