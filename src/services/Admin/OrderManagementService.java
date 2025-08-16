package services.Admin;

import data.OrderData;
import models.Order;

import java.util.List;
import java.util.Comparator;

public class OrderManagementService {
    private final List<Order> orders;

    public OrderManagementService() {
        orders = OrderData.ORDERS;
    }

    public void viewAllOrders() {
        if (orders.isEmpty()) {
            System.out.println("Không có đơn hàng nào.");
            return;
        }
        for (Order o : orders) {
            o.printOrderDetails();
            System.out.println();
        }
    }

    public void updateOrderStatus(int orderId, String newStatus) {
        orders.stream()
                .filter(o -> o.getOrderId() == orderId)
                .findFirst()
                .ifPresent(o -> o.setStatus(newStatus));
    }

    public void removeOrder(int orderId) {
        orders.removeIf(o -> o.getOrderId() == orderId);
    }

    public void sortOrdersByTotalAmount(boolean ascending) {
        orders.sort(Comparator.comparingDouble(Order::getTotalAmount));
        if (!ascending) orders.sort(Comparator.comparingDouble(Order::getTotalAmount).reversed());
    }
}
