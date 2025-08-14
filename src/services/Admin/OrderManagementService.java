package services.Admin;

import models.Order;
import java.util.*;

public class OrderManagementService {
    private List<Order> orders = new ArrayList<>();

    // Thêm đơn hàng (chỉ để test dữ liệu, không phải user tạo)
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Xem toàn bộ đơn hàng
    public void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
            return;
        }
        for (Order order : orders) {
            order.printOrderDetails();
            System.out.println();
        }
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                order.setStatus(newStatus);
                System.out.println("✅ Đã cập nhật trạng thái đơn #" + orderId + " thành: " + newStatus);
                return;
            }
        }
        System.out.println("❌ Không tìm thấy đơn hàng #" + orderId);
    }

    // Xoá đơn hàng
    public void removeOrder(int orderId) {
        Order toRemove = null;
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                toRemove = order;
                break;
            }
        }
        if (toRemove != null) {
            orders.remove(toRemove);
            System.out.println("🗑️ Đã xoá đơn hàng #" + orderId);
        } else {
            System.out.println("❌ Không tìm thấy đơn hàng #" + orderId);
        }
    }

    // Sắp xếp đơn hàng theo ngày
    public void sortOrdersByDate(boolean ascending) {
        orders.sort((o1, o2) -> ascending ?
                o1.getOrderDate().compareTo(o2.getOrderDate()) :
                o2.getOrderDate().compareTo(o1.getOrderDate()));
    }

    // Sắp xếp đơn hàng theo trạng thái
    public void sortOrdersByStatus(boolean ascending) {
        orders.sort((o1, o2) -> ascending ?
                o1.getStatus().compareToIgnoreCase(o2.getStatus()) :
                o2.getStatus().compareToIgnoreCase(o1.getStatus()));
    }

    // Sắp xếp đơn hàng theo tổng tiền
    public void sortOrdersByTotalAmount(boolean ascending) {
        orders.sort((o1, o2) -> ascending ?
                Double.compare(o1.getTotalAmount(), o2.getTotalAmount()) :
                Double.compare(o2.getTotalAmount(), o1.getTotalAmount()));
    }
}
