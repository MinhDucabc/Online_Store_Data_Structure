package data;

import models.Book;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookData {
    public static final List<Book> BOOKS = new ArrayList<>();

    static {
        BOOKS.add(new Book("Database System Concepts", "Henry F. Korth", 28.40f, 8,
            LocalDate.of(2015, 3, 10), "Database",
            "images/Database_System_Concepts.jpg"));

        BOOKS.add(new Book("Computer Networks", "Andrew S. Tanenbaum", 32.10f, 5,
            LocalDate.of(2011, 7, 21), "Networking",
            "images/Computer_Networks.jpg"));

        BOOKS.add(new Book("Artificial Intelligence: A Modern Approach", "Stuart Russell & Peter Norvig", 45.00f, 4,
            LocalDate.of(2020, 1, 15), "Artificial Intelligence",
            "images/Artificial_Intelligence_A_Modern_Approach.jpg"));

        BOOKS.add(new Book("The Pragmatic Programmer", "Andrew Hunt & David Thomas", 27.80f, 10,
            LocalDate.of(2019, 10, 20), "Software Engineering",
            "images/The_Pragmatic_Programmer.jpg"));

        BOOKS.add(new Book("Introduction to Algorithms", "Thomas H. Cormen", 40.00f, 6,
            LocalDate.of(2009, 7, 31), "Algorithms",
            "images/Introduction_to_Algorithms.jpg"));

        BOOKS.add(new Book("Clean Code", "Robert C. Martin", 35.50f, 7,
            LocalDate.of(2008, 8, 1), "Software Engineering",
            "images/Clean_Code.jpg"));

        BOOKS.add(new Book("Design Patterns", "Erich Gamma et al.", 42.00f, 5,
            LocalDate.of(1994, 10, 31), "Software Engineering",
            "images/Design_Pattern.jpg"));

        BOOKS.add(new Book("Deep Learning", "Ian Goodfellow", 50.00f, 3,
            LocalDate.of(2016, 11, 18), "Artificial Intelligence",
            "images/Deep_Learning.jpg"));

        BOOKS.add(new Book("Effective Java", "Joshua Bloch", 38.00f, 6,
            LocalDate.of(2018, 1, 6), "Java",
            "images/Effective_Java.jpg"));

        BOOKS.add(new Book("Java: The Complete Reference", "Herbert Schildt", 45.00f, 8,
            LocalDate.of(2018, 4, 11), "Java",
            "images/Java_The_Complete_Reference_Thirteenth_Edition.jpg"));

        BOOKS.add(new Book("Python Crash Course", "Eric Matthes", 30.00f, 9,
            LocalDate.of(2019, 5, 3), "Python",
            "images/Python_Crash_Course.jpg"));

        BOOKS.add(new Book("Fluent Python", "Luciano Ramalho", 48.00f, 4,
            LocalDate.of(2015, 7, 30), "Python",
            "images/Fluent_Python.jpg"));

        BOOKS.add(new Book("Head First Design Patterns", "Eric Freeman", 37.00f, 7,
            LocalDate.of(2004, 10, 25), "Software Engineering",
            "images/Head_First_Design_Patterns.jpg"));

        BOOKS.add(new Book("Introduction to Machine Learning", "Ethem Alpaydin", 42.50f, 5,
            LocalDate.of(2020, 2, 14), "Artificial Intelligence",
            "images/Introduction_to_Machine_Learning.jpg"));

        BOOKS.add(new Book("Cracking the Coding Interview", "Gayle Laakmann McDowell", 35.00f, 8,
            LocalDate.of(2015, 7, 1), "Algorithms",
            "images/Cracking_the_Coding_Interview.jpg"));

        BOOKS.add(new Book("Learning JavaScript Design Patterns", "Addy Osmani", 28.00f, 6,
            LocalDate.of(2012, 8, 30), "Web Development",
            "images/Learning_JavaScript_Design_Patterns.jpg"));

        BOOKS.add(new Book("You Don't Know JS Yet", "Kyle Simpson", 25.00f, 10,
            LocalDate.of(2020, 1, 28), "Web Development",
            "images/You_Dont_Know_JS_Yet.jpg"));

    }
}
