package services;

import algorithms.GenericSearch;
import algorithms.GenericSort;
import models.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class BookService {

    private final List<Book> books; // Danh sách sách hiện có

    public BookService(List<Book> books) {
        this.books = books;
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
        return GenericSearch.linearSearch(books, keyword,
                (book, k) -> book.getTitle().toLowerCase().contains((String) k) ? 0 : 1);
    }

    public List<Book> searchByAuthor(String keyword) {
        return GenericSearch.linearSearch(books, keyword,
                (book, k) -> book.getAuthor().toLowerCase().contains((String) k) ? 0 : 1);
    }

    public List<Book> searchByCategory(String keyword) {
        return GenericSearch.linearSearch(books, keyword,
                (book, k) -> book.getCategory().toLowerCase().contains((String) k) ? 0 : 1);
    }

    // ===== 3. Sắp xếp sách =====
    public void sortBooksByTitle(boolean ascending) {
        Comparator<Book> comp = Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(books, comp);
    }

    public void sortBooksByPrice(boolean ascending) {
        Comparator<Book> comp = Comparator.comparingDouble(Book::getPrice);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(books, comp);
    }

    public void sortBooksByPublishedDate(boolean ascending) {
        Comparator<Book> comp = Comparator.comparing(Book::getPublishedDate);
        if (!ascending)
            comp = comp.reversed();
        GenericSort.insertionSort(books, comp);
    }

    // ===== 4. Lấy sách theo ID =====
    public Book getBookById(int bookId) {
        return books.stream()
                .filter(b -> b.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }


}
