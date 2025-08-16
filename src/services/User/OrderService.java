package services.User;

import models.Order;
import structures.orderqueuepending.OrderQueuePending;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    public OrderQueuePending orderqueuepending = new OrderQueuePending(); // ✅ thay vì List<Order>

    // Thêm đơn hàng mới
    public void addOrder(Order order) {
        order.updateStatus("Pending");
        orderqueuepending.enqueue(order);
        System.out.println("✅ Đã thêm đơn hàng #" + order.getOrderId() + " cho khách " + order.getCustomerName());
    }

    // Show full don hang
    public void showAllOrders(){
        orderqueuepending.displayQueue();
    }

    // Lay full don hang
    public List<Order> getAllOrders() {
        return new ArrayList<>(orderqueuepending.getOrdersPending());
    }
    
    // Lay toan bo don hang cua mot khach hang
    public List<Order> getOrdersByCustomerId(int customerId){
        List<Order> allOrders = getAllOrders();
        return allOrders.stream()
                        .filter(o -> o.getCustomerId() == customerId)
                        .collect(Collectors.toList());
    }
}