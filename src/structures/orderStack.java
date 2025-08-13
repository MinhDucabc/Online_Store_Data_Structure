package structures;

import java.util.Stack;

import models.Order;

public class orderStack {
    private Stack<Order> orderStack = new Stack<>();

    public void push(Order order) {
        orderStack.push(order);
    }

    public Order pop(){
        return orderStack.isEmpty() ? null : stack.pop();
    }

    public void displayStack() {
        for (Order order : orderStack) {
            System.out.println(order);
        }
    }
}
