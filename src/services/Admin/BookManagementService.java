package services.Admin;

import models.Book;

import java.util.*;

import algorithms.GenericSearch;
import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort;
import algorithms.GenericSort.SortType;
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

    public List<Book> searchByTitle(String keyword, SearchType searchType, SortType sortType) {
        if (searchType == SearchType.LINEAR) {
            return GenericSearch.linearSearch(books, keyword, Book::getTitle);
        } else if (searchType == SearchType.BINARY_TREE) {
            List<Book> sortedBooks = sortBooksByTitle(books, true, sortType);
            return GenericSearch.binarySearchTree(
                    sortedBooks,
                    keyword,
                    Book::getTitle,
                    String::compareToIgnoreCase);
        }
        return new ArrayList<>();
    }

    public List<Book> searchByAuthor(String keyword, SearchType searchType, SortType sortType) {
        if (searchType == SearchType.LINEAR) {
            return GenericSearch.linearSearch(books, keyword, Book::getAuthor);
        } else if (searchType == SearchType.BINARY_TREE) {
            List<Book> sortedBooks = sortBooksByAuthor(books, true, sortType);
            return GenericSearch.binarySearchTree(
                    sortedBooks,
                    keyword,
                    Book::getAuthor,
                    String::compareToIgnoreCase);
        }
        return new ArrayList<>();
    }

    // ===== 3. Sắp xếp sách =====

    public List<Book> sortBooksByTitle(List<Book> bookList, boolean ascending, SortType sortType) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER);
        if (!ascending)
            comp = comp.reversed();
        if (sortType == SortType.SELECTION) {
            GenericSort.selectionSort(sortedList, comp);
        } else if (sortType == SortType.INSERTION) {
            GenericSort.insertionSort(sortedList, comp);
        }
        return sortedList;
    }

    public List<Book> sortBooksByAuthor(List<Book> bookList, boolean ascending, SortType sortType) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER);
        if (!ascending)
            comp = comp.reversed();
        if (sortType == SortType.SELECTION) {
            GenericSort.selectionSort(sortedList, comp);
        } else if (sortType == SortType.INSERTION) {
            GenericSort.insertionSort(sortedList, comp);
        }
        return sortedList;
    }

    public List<Book> sortBooksByPrice(List<Book> bookList, boolean ascending, SortType sortType) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparingDouble(Book::getPrice);
        if (!ascending)
            comp = comp.reversed();
        if (sortType == SortType.SELECTION) {
            GenericSort.selectionSort(sortedList, comp);
        } else if (sortType == SortType.INSERTION) {
            GenericSort.insertionSort(sortedList, comp);
        }
        return sortedList;
    }

    public List<Book> sortBooksByPublishedDate(List<Book> bookList, boolean ascending, SortType sortType) {
        List<Book> sortedList = new ArrayList<>(bookList);
        Comparator<Book> comp = Comparator.comparing(Book::getPublishedDate);
        if (!ascending)
            comp = comp.reversed();
        if (sortType == SortType.SELECTION) {
            GenericSort.selectionSort(sortedList, comp);
        } else if (sortType == SortType.INSERTION) {
            GenericSort.insertionSort(sortedList, comp);
        }
        return sortedList;
    }
}
