package structures.orderqueuedone;

import models.Order;
import data.OrderData;
import java.util.Queue;

public class OrderQueueDone {
    private final Queue<Order> orderQueueDone;

    public OrderQueueDone() {
        // Trỏ trực tiếp tới dữ liệu trong OrderData
        this.orderQueueDone = OrderData.getOrdersDone();
    }

    public void enqueue(Order order) {
        orderQueueDone.add(order);
    }

    public Order dequeue() {
        return orderQueueDone.poll();
    }

    public Order peek() {
        return orderQueueDone.peek();
    }

    public boolean isEmpty() {
        return orderQueueDone.isEmpty();
    }

    public void displayQueue() {
        for (Order order : orderQueueDone) {
            System.out.println(order);
        }
    }

    public Queue<Order> getOrdersDone(){
        return orderQueueDone;
    }
}
