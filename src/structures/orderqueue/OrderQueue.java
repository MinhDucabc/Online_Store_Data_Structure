package structures.orderqueue;

import java.util.Queue;
import java.util.LinkedList;
import models.Order;

public class OrderQueue {
    private Queue<Order> orderQueue;

    public OrderQueue() {
        this.orderQueue = new LinkedList<>();
    }

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

    // getter
    public Queue<Order> getOrders(){
        return new LinkedList<>(orderQueue);
    }
}
