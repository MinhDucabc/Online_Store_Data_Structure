package test;

import services.BookService;
import services.User.CartService;
import services.User.OrderService;
import services.checkout.CheckoutService;

import models.*;

import java.util.*; // import đầy đủ Random, Set, HashSet, List

import data.UserData;
import data.BookData;

public class UserMainFlowMultipleOrder {
    public static int nums_of_orders = 3;

    public static void main(String[] args) {
        // 1️⃣ Khởi tạo các service cần thiết
        BookService bookService = new BookService();
        CartService cartService = new CartService();
        OrderService orderService = new OrderService();

        // 2️⃣ Lay customer mẫu
        Customer customer = UserData.CUSTOMERS.get(0);

        Random rand = new Random(); // chỉ cần 1 random chung

        boolean invalidOrder = false;
        for (int i = 0; i < nums_of_orders; i++) {
            Set<Book> picked = new HashSet<>();

            // số lượng sách được chọn ngẫu nhiên trong [min, max]
            // Chon sach ngau nhien
            int nums_picked = rand.nextInt(3) + 1; // 1-3 sách

            while (picked.size() < nums_picked) {
                Book randomBook = BookData.BOOKS.get(rand.nextInt(BookData.BOOKS.size()));
                picked.add(randomBook);
            }

            System.out.println("\n=== Nhung quyen sach da them ===");
            picked.forEach(b -> System.out.println(b.getTitle()));
            
            System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG (Order " + (i+1) + ") ===");
            for (Book b : picked) {
                int qty = rand.nextInt(2) + 1;
                if (qty > b.getQuantity() || qty <= 0) {
                    System.out.println("❌ Lỗi: " + b.getTitle() + " chỉ còn " + b.getQuantity() + " nhưng yêu cầu " + qty);
                    invalidOrder = true;
                    break; // thoát vòng for (sách)
                }
                cartService.addToCart(b, qty);
                System.out.println(b.getTitle() + " x " + qty);
            }
            if (invalidOrder) {
                System.out.println("⚠️ Order " + (i+1) + " bị hủy do vượt quá số lượng tồn kho!");
                continue; // tiep tuc vòng lặp order
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

        // Kiểm tra toàn bộ đơn hàng của user
        System.out.println("\n=== DANH SÁCH TOÀN BỘ ORDER ===");
        List<Order> allOrders = orderService.getOrdersByCustomerId(customer.getUserId());
        allOrders.forEach(Order::printOrderDetails);

    }
}
