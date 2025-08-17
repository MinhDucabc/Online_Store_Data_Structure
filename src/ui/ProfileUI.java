package ui;

import models.Customer;
import models.Order;
import services.User.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfileUI extends JFrame {
    private final Customer customer;
    private final OrderService orderService;

    public ProfileUI(Customer customer, OrderService orderService) {
        this.customer = customer;
        this.orderService = orderService;

        setTitle("Profile - " + customer.getName());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 🔹 Phần thông tin khách hàng
        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Customer Info"));
        infoPanel.add(new JLabel("ID: " + customer.getUserId()));
        infoPanel.add(new JLabel("Name: " + customer.getName()));
        infoPanel.add(new JLabel("Email: " + customer.getEmail()));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // 🔹 Tab hiển thị orders
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Pending Orders", createOrderTable(orderService.getAllPendingOrdersByCustomerId(customer.getUserId())));
        tabbedPane.add("Processing Orders", createOrderTable(orderService.getAllProcessingOrdersByCustomerId(customer.getUserId())));
        tabbedPane.add("Done Orders", createOrderTable(orderService.getAllDoneOrdersByCustomerId(customer.getUserId())));
        tabbedPane.add("All Orders", createOrderTable(orderService.getAllOrdersByCustomerId(customer.getUserId())));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JScrollPane createOrderTable(List<Order> orders) {
        String[] columnNames = {"Order ID", "Book Title", "Quantity", "Price", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Order o : orders) {
            model.addRow(new Object[]{
                    o.getOrderId(),
                    o.getItems().stream().map(item -> item.getBook().getTitle()).reduce((a, b) -> a + ", " + b).orElse(""),
                    o.getItems().stream().mapToInt(item -> item.getQuantity()).sum(),
                    o.getTotalAmount(),
                    o.getCurrentStatus(),
            });
        }

        JTable table = new JTable(model);
        return new JScrollPane(table);
    }
}
