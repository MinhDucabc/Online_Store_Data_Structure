package ui;

import javax.swing.*;
import java.awt.*;

import services.Admin.OrderManagementService;
import structures.orderqueuedone.OrderQueueDone;
import structures.orderqueuepending.OrderQueuePending;
import structures.orderqueueprocessing.OrderQueueProcessing;
import services.Admin.CustomerManagementService;
import services.Admin.BookManagementService;
import ui.admin.AdminOrderManagementUI;
import ui.admin.AdminCustomerManagementUI;
import ui.admin.AdminBookManagementUI;

public class AdminInterface extends JFrame {
    private final OrderManagementService orderService;
    private final CustomerManagementService customerService;
    private final BookManagementService bookService;
    private JTabbedPane tabbedPane;

    public AdminInterface() {
        OrderQueuePending orderQueuePending = new OrderQueuePending();
        OrderQueueProcessing orderQueueProcessing = new OrderQueueProcessing();
        OrderQueueDone orderQueueDone = new OrderQueueDone();
        this.orderService = new OrderManagementService(
            orderQueuePending,
            orderQueueProcessing,
            orderQueueDone
        );
        this.customerService = new CustomerManagementService();
        this.bookService = new BookManagementService();

        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        tabbedPane = new JTabbedPane();

        AdminOrderManagementUI orderManagementUI = new AdminOrderManagementUI(orderService);
        AdminCustomerManagementUI customerManagementUI = new AdminCustomerManagementUI(customerService);
        AdminBookManagementUI bookManagementUI = new AdminBookManagementUI(bookService);

        tabbedPane.addTab("📦 Orders", orderManagementUI);
        tabbedPane.addTab("👥 Customers", customerManagementUI);
        tabbedPane.addTab("📚 Books", bookManagementUI);

        add(tabbedPane);
    }
}
