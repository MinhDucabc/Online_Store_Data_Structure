// Một Stack cho mỗi đơn hàng
package structures.orderstack;

import java.util.Stack;

public class OrderStack {
    private Stack<String> statusHistory;

    public OrderStack() {
        this.statusHistory = new Stack<>();
    }

    // Thêm trạng thái mới
    public void pushStatus(String status) {
        statusHistory.push(status);
    }

    // Lấy lịch sử trạng thái
    public Stack<String> getStatusHistory() {
        return statusHistory;
    }

    // Lấy trạng thái hiện tại
    public String currentStatus() {
        return statusHistory.isEmpty() ? "Chưa có trạng thái" : statusHistory.peek();
    }

    // Quay lại trạng thái trước đó (undo)
    public String popStatus() {
        if (!statusHistory.isEmpty()) {
            return statusHistory.pop();
        }
        return "Không còn trạng thái trước đó";
    }

    public boolean isEmpty() {
        return statusHistory.isEmpty();
    }
}
