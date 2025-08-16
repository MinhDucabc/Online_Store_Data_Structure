package test.User;

import models.*;

import data.UserData;
import services.BookService;
import services.User.CartService;
import services.checkout.CheckoutService;
import data.BookData;

import data.OrderData;

import java.time.LocalDate;

public class CartServiceTest {
    public static void main(String[] args) {
        CartService cartService = new CartService();

        // 1️⃣ Thêm sách vào giỏ
        Book book1 = BookData.BOOKS.get(0); // Machine Learning Basics
        Book book2 = BookData.BOOKS.get(1); // Operating Systems

        cartService.addToCart(book1, 2);
        cartService.addToCart(book2, 1);

        System.out.println("--- Gio hang sau khi them ---");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );

        // 2️⃣ Cập nhật số lượng
        cartService.updateQuantity(book1.getBookId(), 5);
        System.out.println("\n--- Gio hang sau khi cap nhat ---");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );

        // 3️⃣ Tính tổng tiền
        double total = cartService.getTotalAmount();
        System.out.println("\nTong tien: " + total);

        // 4️⃣ Xóa sách khỏi giỏ
        cartService.removeFromCart(book2.getBookId());
        System.out.println("\n--- Gio hang sau khi xoa ---");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );

        // 5️⃣ Xóa toàn bộ giỏ
        cartService.clearCart();
        System.out.println("\n--- Gio hang sau khi clear ---");
        System.out.println(cartService.getCartItems()); // []

        BookService bookService = new BookService();
        CheckoutService checkoutService = new CheckoutService(bookService, cartService);

        Book book3 = BookData.BOOKS.get(2); // Example: Data Structures
        cartService.addToCart(book3, 3);

        System.out.println("\n--- Gio hang truoc khi thanh toan ---");
        cartService.getCartItems().forEach(item ->
            System.out.println(item.getBook().getTitle() + " x " + item.getQuantity())
        );

        Order checkoutResult = checkoutService.checkout(UserData.CUSTOMERS.get(0));
        System.out.println("\nKet qua thanh toan: ");
        // Ket qua thanh toan can in ra list order
        
        
        System.out.println("\n--- Gio hang sau khi thanh toan ---");
        System.out.println(cartService.getCartItems()); // []
    }
}
