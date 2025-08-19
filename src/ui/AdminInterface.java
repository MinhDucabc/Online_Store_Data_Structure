package ui;

import javax.swing.*;
import java.awt.*;

import services.Admin.OrderManagementService;
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

    public AdminInterface(OrderManagementService orderService,
                          CustomerManagementService customerService,
                          BookManagementService bookService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.bookService = bookService;

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
