package structures;

import java.util.Queue;
import models.Order;

public class OrderQueue {
    private Queue<Order> orderQueue = new Queue<>();

    public void enqueue(Order order) {
        orderQueue.add(order);
    }

    public Order dequeue() {
        return orderQueue.poll();
    }

    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }

    public void displayQueue() {
        for (Order order : orderQueue) {
            System.out.println(order);
        }
    }
}
