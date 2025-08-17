package services.User;

import models.Order;
import structures.orderqueuepending.OrderQueuePending;
import structures.orderqueueprocessing.OrderQueueProcessing;
import structures.orderqueuedone.OrderQueueDone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    public OrderQueuePending orderqueuepending = new OrderQueuePending();
    public OrderQueueProcessing orderqueueprocessing = new OrderQueueProcessing();
    public OrderQueueDone orderqueuedone = new OrderQueueDone();

    // Thêm đơn hàng mới
    public void addOrder(Order order) {
        order.updateStatus("Pending");
        orderqueuepending.enqueue(order);
        System.out.println("✅ Đã thêm đơn hàng #" + order.getOrderId() + " cho khách " + order.getCustomerName());
    }

    // ===== Lấy tất cả đơn hàng theo trạng thái =====
    public List<Order> getAllPendingOrders() {
        return new ArrayList<>(orderqueuepending.getOrdersPending());
    }

    public List<Order> getAllProcessingOrders() {
        return new ArrayList<>(orderqueueprocessing.getOrdersProcessing());
    }

    public List<Order> getAllDoneOrders() {
        return new ArrayList<>(orderqueuedone.getOrdersDone());
    }

    public List<Order> getAllOrders() {
        List<Order> allOrders = new ArrayList<>();
        allOrders.addAll(orderqueuepending.getOrdersPending());
        allOrders.addAll(orderqueueprocessing.getOrdersProcessing());
        allOrders.addAll(orderqueuedone.getOrdersDone());
        return allOrders;
    }

    // ===== Lấy đơn hàng theo customerId =====
    public List<Order> getAllPendingOrdersByCustomerId(int customerId) {
        return orderqueuepending.getOrdersPending()
                .stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public List<Order> getAllProcessingOrdersByCustomerId(int customerId) {
        return orderqueueprocessing.getOrdersProcessing()
                .stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public List<Order> getAllDoneOrdersByCustomerId(int customerId) {
        return orderqueuedone.getOrdersDone()
                .stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    public List<Order> getAllOrdersByCustomerId(int customerId) {
        return getAllOrders().stream()
                .filter(o -> o.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

}