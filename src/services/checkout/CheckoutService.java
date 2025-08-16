package services.checkout;

import data.OrderData;
import models.*;
import services.BookService;
import services.User.CartService;
import services.User.OrderService;

import java.util.ArrayList;
import java.util.List;

public class CheckoutService {
    private final BookService bookService;
    private final CartService cartService;
    private final OrderService orderService;

    public CheckoutService(BookService bookService, CartService cartService, OrderService orderService) {
        this.bookService = bookService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    public Order checkout(Customer customer) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            System.out.println("⚠️ Giỏ hàng đang trống!");
            return null;
        }

        // Kiểm tra tồn kho
        for (CartItem item : cartItems) {
            Book book = bookService.getBookById(item.getBook().getBookId());
            if (book == null || book.getQuantity() < item.getQuantity()) {
                throw new RuntimeException("Sách '" + item.getBook().getTitle() + "' không đủ tồn kho!");
            }
        }

        // Giảm tồn kho
        for (CartItem item : cartItems) {
            Book book = bookService.getBookById(item.getBook().getBookId());
            book.setQuantity(book.getQuantity() - item.getQuantity());
        }

        // Tạo Order
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            orderItems.add(new OrderItem(item.getBook(), item.getQuantity()));
        }
        Order order = new Order(customer, orderItems);

        // Lưu vào OrderData
        orderService.addOrder(order);

        // Xóa giỏ hàng
        cartService.clearCart();
        
        System.out.println("Thanh toan thanh cong");
        return order;
    }
}
