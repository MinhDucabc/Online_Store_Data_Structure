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
        infoPanel.add(new JLabel("Phone: " + customer.getPhoneNumber()));
        infoPanel.add(new JLabel("Address: " + customer.getAddress()));
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // 🔹 Tab hiển thị orders
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Pending Orders", new JScrollPane(createOrderCards(
                orderService.getAllPendingOrdersByCustomerId(customer.getUserId()))));
        tabbedPane.add("Processing Orders", new JScrollPane(createOrderCards(
                orderService.getAllProcessingOrdersByCustomerId(customer.getUserId()))));
        tabbedPane.add("Done Orders", new JScrollPane(createOrderCards(
                orderService.getAllDoneOrdersByCustomerId(customer.getUserId()))));
        tabbedPane.add("All Orders", new JScrollPane(createOrderCards(
                orderService.getAllOrdersByCustomerId(customer.getUserId()))));

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createOrderCards(List<Order> orders) {
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));

        for (Order o : orders) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(10, 10, 10, 10),
                    BorderFactory.createLineBorder(Color.GRAY, 1)));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200)); // hạn chế chiều cao card

            // 🔹 Tiêu đề order
            JLabel orderLabel = new JLabel("Order #" + o.getOrderId() + " - Status: " + o.getCurrentStatus());
            orderLabel.setFont(orderLabel.getFont().deriveFont(Font.BOLD, 14f));
            card.add(orderLabel, BorderLayout.NORTH);

            // 🔹 Panel hiển thị items (có scroll riêng cho từng order)
            JPanel itemsPanel = new JPanel();
            itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));

            for (var item : o.getItems()) {
                itemsPanel.add(new JLabel("- " + item.getBook().getTitle()
                        + " (x" + item.getQuantity() + ")"
                        + " - $" + item.getBook().getPrice()));
            }

            JScrollPane itemsScroll = new JScrollPane(itemsPanel);
            itemsScroll.setPreferredSize(new Dimension(600, 100)); // giới hạn kích thước hiển thị
            itemsScroll.getVerticalScrollBar().setUnitIncrement(12);

            card.add(itemsScroll, BorderLayout.CENTER);

            // 🔹 Tổng tiền
            JLabel totalLabel = new JLabel("Total: $" + o.getTotalAmount());
            totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            totalLabel.setFont(totalLabel.getFont().deriveFont(Font.BOLD));
            card.add(totalLabel, BorderLayout.SOUTH);

            // add card vào container
            cardContainer.add(card);
            cardContainer.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        return cardContainer;
    }

}
