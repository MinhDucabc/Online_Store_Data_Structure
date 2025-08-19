package test;

import services.BookService;
import services.User.CartService;
import services.User.OrderService;
import services.checkout.CheckoutService;
import services.Auth.AuthService;

import models.*;

import java.util.*; // import Random, Set, HashSet, List

import data.BookData;

public class UserMainFlowMultipleOrder {
    public static int nums_of_orders = 3;

    public static void main(String[] args) {
        // 1️⃣ Khởi tạo các service cần thiết
        BookService bookService = new BookService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();
        AuthService authService = new AuthService();

        // 2️⃣ Đăng nhập với tư cách customer
        System.out.println("\n=== ĐĂNG NHẬP CUSTOMER ===");
        boolean loginSuccess = authService.login("alice@example.com", "alicepass");
        if (!loginSuccess) {
            System.out.println("❌ Không thể đăng nhập. Dừng chương trình.");
            return;
        }
        Customer customer = authService.getLoggedInCustomer();

        Random rand = new Random(); // chỉ cần 1 random chung

        // 3️⃣ Tạo nhiều đơn hàng ngẫu nhiên
        for (int i = 0; i < nums_of_orders; i++) {
            Set<Book> picked = new HashSet<>();
            boolean invalidOrder = false;

            // số lượng sách được chọn ngẫu nhiên trong [1, 3]
            int nums_picked = rand.nextInt(3) + 1;

            while (picked.size() < nums_picked) {
                Book randomBook = BookData.BOOKS.get(rand.nextInt(BookData.BOOKS.size()));
                picked.add(randomBook);
            }

            System.out.println("\n=== Nhung quyen sach da them (Order " + (i+1) + ") ===");
            picked.forEach(b -> System.out.println(b.getTitle()));

            System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG (Order " + (i+1) + ") ===");
            for (Book b : picked) {
                int qty = rand.nextInt(2) + 1; // chọn 1-2 quyển
                if (qty > b.getQuantity() || qty <= 0) {
                    System.out.println("❌ Lỗi: " + b.getTitle() + " chỉ còn " + b.getQuantity() + " nhưng yêu cầu " + qty);
                    invalidOrder = true;
                    break;
                }
                cartService.addToCart(b, qty);
                System.out.println(b.getTitle() + " x " + qty);
            }

            if (invalidOrder) {
                System.out.println("⚠️ Order " + (i+1) + " bị hủy do vượt quá số lượng tồn kho!");
                cartService.clearCart(); // xoá giỏ tránh sót hàng
                continue;
            }

            // Tồn kho trước checkout
            System.out.println("\n=== TỒN KHO TRƯỚC CHECKOUT ===");
            picked.forEach(b -> System.out.println(b.getTitle() + " còn: " + b.getQuantity()));

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
            picked.forEach(b -> System.out.println(b.getTitle() + " còn: " + b.getQuantity()));
        }

        // 7️⃣ Kiểm tra toàn bộ đơn hàng của user trong các queue
        System.out.println("\n=== KIỂM TRA ORDER THEO TRẠNG THÁI ===");
        System.out.println("📌 Pending Orders:");
        orderService.getAllPendingOrdersByCustomerId(customer.getUserId())
                    .forEach(Order::printOrderDetails);

        System.out.println("📌 Processing Orders:");
        orderService.getAllProcessingOrdersByCustomerId(customer.getUserId())
                    .forEach(Order::printOrderDetails);

        System.out.println("📌 Done Orders:");
        orderService.getAllDoneOrdersByCustomerId(customer.getUserId())
                    .forEach(Order::printOrderDetails);

        // 8️⃣ Logout
        System.out.println("\n=== LOGOUT ===");
        authService.logout(cartService);
    }
}
