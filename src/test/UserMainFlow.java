package test;

import services.BookService;
import services.User.CartService;
import services.User.OrderService;
import services.checkout.CheckoutService;

import models.*;
import data.BookData;
import data.OrderData;

public class UserMainFlow{
    public static void main(String[] args) {
        // 1️⃣ Khởi tạo các service cần thiết
        BookService bookService = new BookService();
        CartService cartService = new CartService();

        // 2️⃣ Tạo customer mẫu
        Customer customer = new Customer("Nguyen Van A", "a@gmail.com", "0123456789", "Hanoi");

        // 3️⃣ Thêm sách vào giỏ hàng
        Book book1 = BookData.BOOKS.get(0); // Machine Learning Basics
        Book book2 = BookData.BOOKS.get(1); // Operating Systems

        System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG ===");
        cartService.addToCart(book1, 2); // có 7 tồn kho
        cartService.addToCart(book2, 1); // có 6 tồn kho

        // Ton kho truoc checkout
        System.out.println("\n=== TỒN KHO TRUOC CHECKOUT ===");
        System.out.println(book1.getTitle() + " còn: " + book1.getQuantity());
        System.out.println(book2.getTitle() + " còn: " + book2.getQuantity());
        
        // Hiển thị giỏ hàng
        System.out.println("\n=== GIỎ HÀNG HIỆN TẠI ===");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );
        System.out.println("Tổng tiền: " + cartService.getTotalAmount());

        OrderService orderService = new OrderService();
        CheckoutService checkoutService = new CheckoutService(bookService, cartService, orderService);

        // 4️⃣ Thực hiện checkout
        System.out.println("\n=== THỰC HIỆN CHECKOUT ===");
        Order order = checkoutService.checkout(customer);

        // Hiển thị thông tin đơn hàng
        order.printOrderDetails();

        // 5️⃣ Kiểm tra giỏ hàng sau checkout (phải trống)
        System.out.println("\n=== GIỎ HÀNG SAU CHECKOUT ===");
        System.out.println("Số lượng sản phẩm: " + cartService.getCartItems().size());

        // 6️⃣ Kiểm tra tồn kho đã giảm chưa
        System.out.println("\n=== TỒN KHO SAU CHECKOUT ===");
        System.out.println(book1.getTitle() + " còn: " + book1.getQuantity());
        System.out.println(book2.getTitle() + " còn: " + book2.getQuantity());
    }
}
