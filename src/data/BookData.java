package data;

import models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookData {
    public static final List<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book("Machine Learning Basics", "Andrew Ng", 25.99f, 7, LocalDate.of(2018, 9, 12), "Artificial Intelligence"));
        BOOKS.add(new Book("Operating Systems", "Abraham Silberschatz", 30.50f, 6, LocalDate.of(2016, 2, 5), "Computer Science"));
        BOOKS.add(new Book("Clean Code", "Robert C. Martin", 22.75f, 9, LocalDate.of(2008, 8, 1), "Software Engineering"));
    }
}
