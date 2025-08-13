package algorithms;

import java.util.Collections;
import java.util.List;

import models.Book;

class sortAlgorithms {
    public static void insertionSort(List<Book> books, String sortType) {
        for (int i = 1; i < books.size(); i++) {
            Book key = books.get(i); // key: bookPointer
            int j = i - 1;
            while (j >= 0) {
                if (compareBooks(books.get(j), key, sortType) > 0) {
                    books.set(j + 1, books.get(j));
                }
                j--;
            }
            books.set(j + 1, key);
        }
    }

    public static void selectionSort(List<Book> books, String sortType) {
        for (int i = 0; i < books.size() - 1; i++){
            int minIndex = i;
            for (int j = i + 1; j < books.size(); j++){
                if (compareBooks(books.get(j), books.get(minIndex), sortType) > 0){
                    minIndex = j;
                }
            }
            Collections.swap(books, i, minIndex);
        }
    }

    private static int compareBooks(Book b1, Book b2, String sortType) {
        switch (sortType.toLowerCase()) {
            case "bookid":
                return Integer.compare(b1.getBookId(), b2.getBookId());
            case "title":
                return b1.getTitle().compareToIgnoreCase(b2.getTitle());
            case "author":
                return b1.getAuthor().compareToIgnoreCase(b2.getAuthor());
            case "quantity":
                return Integer.compare(b1.getQuantity(), b2.getQuantity());
            case "publishdate":
                return b1.getPublishDate().compareTo(b2.getPublishDate());
            default:
                throw new IllegalArgumentException("Invalid sort type: " + sortType);
        }
    }

    public static void insertionSortById(List<Book> books) {
        insertionSort(books, "bookId");
    }

    // Sort theo Title
    public static void insertionSortByTitle(List<Book> books) {
        insertionSort(books, "title");
    }

    // Sort theo Author
    public static void insertionSortByAuthor(List<Book> books) {
        insertionSort(books, "author");
    }

    // Sort theo Quantity
    public static void insertionSortByQuantity(List<Book> books) {
        insertionSort(books, "quantity");
    }

    // Sort theo Publish Date
    public static void insertionSortByPublishDate(List<Book> books) {
        insertionSort(books, "publishDate");
    }
}
