package structures;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private Queue<Order> orderQueue = new LinkedList<>();

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