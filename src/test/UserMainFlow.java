package test;

import services.BookService;
import services.User.CartService;
import services.User.OrderService;
import services.checkout.CheckoutService;
import services.AuthService;

import models.*;
import data.BookData;

public class UserMainFlow {
    public static void main(String[] args) {
        // 1️⃣ Khởi tạo các service cần thiết
        BookService bookService = new BookService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        AuthService authService = new AuthService();

        // 2️⃣ Đăng nhập với tư cách customer từ UserData
        System.out.println("\n=== ĐĂNG NHẬP CUSTOMER ===");
        boolean loginSuccess = authService.login("alice@example.com", "alicepass"); // có sẵn trong UserData
        if (!loginSuccess) {
            System.out.println("❌ Không thể đăng nhập. Dừng chương trình.");
            return;
        }
        Customer customer = authService.getLoggedInCustomer();

        // 3️⃣ Thêm sách vào giỏ hàng
        Book book1 = BookData.BOOKS.get(0); // Machine Learning Basics
        Book book2 = BookData.BOOKS.get(1); // Operating Systems

        System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG ===");
        cartService.addToCart(book1, 2); // có 7 tồn kho
        cartService.addToCart(book2, 1); // có 6 tồn kho

        // Tồn kho trước checkout
        System.out.println("\n=== TỒN KHO TRƯỚC CHECKOUT ===");
        System.out.println(book1.getTitle() + " còn: " + book1.getQuantity());
        System.out.println(book2.getTitle() + " còn: " + book2.getQuantity());

        // Hiển thị giỏ hàng
        System.out.println("\n=== GIỎ HÀNG HIỆN TẠI ===");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );
        System.out.println("Tổng tiền: " + cartService.getTotalAmount());

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

        // 7️⃣ Kiểm tra đơn hàng trong các queue
        System.out.println("\n=== KIỂM TRA ĐƠN HÀNG TRƯỚC KHI LOGOUT ===");
        System.out.println("📌 Pending Orders:");
        orderService.getAllPendingOrders().forEach(Order::printOrderDetails);

        System.out.println("📌 Processing Orders:");
        orderService.getAllProcessingOrders().forEach(Order::printOrderDetails);

        System.out.println("📌 Done Orders:");
        orderService.getAllDoneOrders().forEach(Order::printOrderDetails);

        
        // 7️⃣ Logout sau khi xong
        System.out.println("\n=== LOGOUT ===");
        authService.logout(cartService);
    }
}
