package structures;

import java.util.Stack;
import models.Order;

public class OrderStack {
    private Stack<Order> orderStack = new Stack<>();

    public void push(Order order) {
        orderStack.push(order);
    }

    public Order peek() {
        return orderStack.isEmpty() ? null : orderStack.peek();
    }

    public Order pop() {
        return orderStack.isEmpty() ? null : orderStack.pop(); // sửa stack -> orderStack
    }

    public boolean isEmpty() {
        return orderStack.isEmpty();
    }

    public void displayStack() {
        for (Order order : orderStack) {
            System.out.println(order);
        }
    }
}
