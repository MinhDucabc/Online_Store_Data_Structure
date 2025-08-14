package services.User;

import models.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private List<Order> orders = new ArrayList<>();

    // Thêm đơn hàng mới
    public void addOrder(Order order) {
        orders.add(order);
        System.out.println("✅ Đã thêm đơn hàng #" + order.getOrderId() + " cho khách " + order.getCustomerName());
    }

    // Lấy danh sách đơn hàng của 1 khách hàng
    public List<Order> getOrdersByCustomerId(int customerId) {
        return orders.stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    // Lấy tất cả đơn hàng (Admin)
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    // Tìm đơn hàng theo ID
    public Order findOrderById(int orderId) {
        return orders.stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .orElse(null);
    }

    // In danh sách đơn hàng (debug/test)
    public void printOrders(List<Order> ordersList) {
        if (ordersList.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
            return;
        }
        for (Order o : ordersList) {
            o.printOrderDetails();
            System.out.println("----------------------------");
        }
    }
}
