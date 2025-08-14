package services.User;

import models.*;
import java.util.ArrayList;
import java.util.List;

public class CartService {

    private final List<CartItem> cartItems = new ArrayList<>();

    // ===== 1. Thêm sách vào giỏ =====
    public void addToCart(Book book, int quantity) {
        // Kiểm tra sách đã có trong giỏ
        for (CartItem item : cartItems) {
            if (item.getBook().getBookId() == book.getBookId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Nếu chưa có, tạo mới
        cartItems.add(new CartItem(book, quantity));
    }

    // ===== 2. Xóa sách khỏi giỏ =====
    public boolean removeFromCart(int bookId) {
        return cartItems.removeIf(item -> item.getBook().getBookId() == bookId);
    }

    // ===== 3. Sửa số lượng sách trong giỏ =====
    public boolean updateQuantity(int bookId, int newQuantity) {
        for (CartItem item : cartItems) {
            if (item.getBook().getBookId() == bookId) {
                item.setQuantity(newQuantity);
                return true;
            }
        }
        return false;
    }

    // ===== 4. Lấy danh sách sách trong giỏ =====
    public List<CartItem> getCartItems() {
        return new ArrayList<>(cartItems); // trả về copy tránh sửa ngoài ý muốn
    }

    // ===== 5. Tính tổng tiền =====
    public double getTotalAmount() {
        return cartItems.stream()
                .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
                .sum();
    }

    // ===== 6. Xóa toàn bộ giỏ =====
    public void clearCart() {
        cartItems.clear();
    }
}
