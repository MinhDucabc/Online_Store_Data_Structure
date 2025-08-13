package test;
import models.Book;

public class BookTest {
    public static void main(String[] args) {
        // Tạo đối tượng Book để test
        Book b1 = new Book("Java Programming", "James Gosling", 5, "1995-05-23");
        Book b2 = new Book("Clean Code", "Robert C. Martin", 3, "2008-08-11");

        // In ra console để xem toString()
        // System.out.println(b1); // Tự động gọi b1.toString()
        // System.out.println(b2);

        // Test trực tiếp phương thức toString()
        String output = b1.toString();
        System.out.println("Kết quả từ toString(): " + output);
    }
}
