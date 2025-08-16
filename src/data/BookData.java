package data;

import models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookData {
    public static final List<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book("Database System Concepts", "Henry F. Korth", 28.40f, 8,
                LocalDate.of(2015, 3, 10), "Database"));
        BOOKS.add(new Book("Computer Networks", "Andrew S. Tanenbaum", 32.10f, 5,
                LocalDate.of(2011, 7, 21), "Networking"));
        BOOKS.add(new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell & Peter Norvig", 45.00f, 4,
                LocalDate.of(2020, 1, 15), "Artificial Intelligence"));
        BOOKS.add(new Book("The Pragmatic Programmer", "Andrew Hunt & David Thomas", 27.80f, 10,
                LocalDate.of(2019, 10, 20), "Software Engineering"));
        BOOKS.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", 40.00f, 6,
                LocalDate.of(2009, 7, 31), "Algorithms"));
    }
}
