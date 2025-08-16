package structures.orderqueuepending;

import models.Order;
import data.OrderData;
import java.util.Queue;

public class OrderQueuePending {
    private final Queue<Order> orderQueuePending;

    public OrderQueuePending() {
        // Trỏ trực tiếp tới dữ liệu trong OrderData
        this.orderQueuePending = OrderData.getOrdersPending();
    }

    public void enqueue(Order order) {
        orderQueuePending.add(order);
    }

    public Order dequeue() {
        return orderQueuePending.poll();
    }

    public Order peek() {
        return orderQueuePending.peek();
    }

    public boolean isEmpty() {
        return orderQueuePending.isEmpty();
    }

    public void displayQueue() {
        for (Order order : orderQueuePending) {
            System.out.println(order);
        }
    }

    public Queue<Order> getOrdersPending(){
        return orderQueuePending;
    }
}
