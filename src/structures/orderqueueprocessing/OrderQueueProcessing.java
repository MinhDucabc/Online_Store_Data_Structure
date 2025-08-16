package structures.orderqueueprocessing;

import models.Order;
import data.OrderData;
import java.util.Queue;

public class OrderQueueProcessing {
    private final Queue<Order> orderQueueProcessing;

    public OrderQueueProcessing() {
        // Trỏ trực tiếp tới dữ liệu trong OrderData
        this.orderQueueProcessing = OrderData.getOrdersProcessing();
    }

    public void enqueue(Order order) {
        orderQueueProcessing.add(order);
    }

    public Order dequeue() {
        return orderQueueProcessing.poll();
    }

    public Order peek() {
        return orderQueueProcessing.peek();
    }

    public boolean isEmpty() {
        return orderQueueProcessing.isEmpty();
    }

    public void displayQueue() {
        for (Order order : orderQueueProcessing) {
            System.out.println(order);
        }
    }

    public Queue<Order> getOrdersProcessing(){
        return orderQueueProcessing;
    }
}
