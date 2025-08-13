package test;
import java.util.ArrayList;
import models.*;

public class OrderTest {
    public static void main(String[] args) {
        Customer c1 = new Customer(1, "Nguyen Van A", "a@example.com", "0123456789", "Hà Nội");

        ArrayList<Book> books = new ArrayList<>();
        // books.add(new Book(...));

        ArrayList<Integer> quantities = new ArrayList<>();
        // quantities.add(...);

        Order o1 = new Order(c1, books, quantities);

        System.out.println(o1.getCustomerName()); // Lấy tên nhanh
        System.out.println(o1.getCustomerId()); // Lấy ID để tra cứu Customer đầy đủ
    }
}
