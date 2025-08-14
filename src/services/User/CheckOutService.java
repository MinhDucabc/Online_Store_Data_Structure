package services.User;

import models.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CheckoutService {

    private final BookService bookService;
    private final CartService cartService;
    private final List<Order> orderHistory; // Lưu tạm orders

    public CheckoutService(BookService bookService, CartService cartService, List<Order> orderHistory) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.orderHistory = orderHistory;
    }

    /**
     * Thực hiện thanh toán và tạo đơn hàng Pending
     */
    public Order checkout(String userId) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) return null;

        // Kiểm tra tồn kho
        for (CartItem item : cartItems) {
            Book book = bookService.getBookById(item.getBook().getBookId());
            if (book == null || book.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sách '" + item.getBook().getTitle() + "' không đủ tồn kho!");
            }
        }

        // Giảm số lượng tồn kho tạm thời (nếu muốn)
        for (CartItem item : cartItems) {
            Book book = bookService.getBookById(item.getBook().getBookId());
            book.setQuantity(book.getQuantity() - item.getQuantity());
        }

        // Tạo Order
        Order order = new Order(userId, LocalDateTime.now(), "Pending");
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            orderItems.add(new OrderItem(item.getBook(), item.getQuantity()));
        }
        order.setItems(orderItems);

        // Lưu Order
        orderHistory.add(order);

        // Xóa giỏ hàng
        cartService.clearCart();

        return order;
    }
}
