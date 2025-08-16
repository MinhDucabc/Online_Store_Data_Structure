package data;

import models.Order;
import java.util.LinkedList;
import java.util.Queue;

public class OrderData {
    private static final Queue<Order> ordersPending = new LinkedList<>();
    private static final Queue<Order> ordersProcessing = new LinkedList<>();
    private static final Queue<Order> ordersDone = new LinkedList<>();

    // ✅ Getters
    public static Queue<Order> getOrdersPending() { return ordersPending; }
    public static Queue<Order> getOrdersProcessing() { return ordersProcessing; }
    public static Queue<Order> getOrdersDone() { return ordersDone; }
}
