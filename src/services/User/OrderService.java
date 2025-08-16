package services.User;

import models.Order;
import structures.orderqueue.OrderQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    public OrderQueue orderqueue = new OrderQueue(); // ✅ thay vì List<Order>

    // Thêm đơn hàng mới
    public void addOrder(Order order) {
        orderqueue.enqueue(order);
        System.out.println("✅ Đã thêm đơn hàng #" + order.getOrderId() + " cho khách " + order.getCustomerName());
    }

    // Show full don hang
    public void showAllOrders(){
        orderqueue.displayQueue();
    }

    // Lay full don hang
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderqueue.getOrders());
    }
    
    // Lay toan bo don hang cua mot khach hang
    public List<Order> getOrdersByCustomerId(int customerId){
        List<Order> allOrders = getAllOrders();
        return allOrders.stream()
                        .filter(o -> o.getCustomerId() == customerId)
                        .collect(Collectors.toList());
    }
}