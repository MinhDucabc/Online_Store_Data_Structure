package ui.admin;

import models.Order;
import services.Admin.OrderManagementService;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminOrderManagementUI extends JPanel {
    private final OrderManagementService orderService;
    private DefaultTableModel pendingModel, processingModel, doneModel, allModel;
    private JTable pendingTable, processingTable, doneTable, allTable;

    public AdminOrderManagementUI(OrderManagementService orderService) {
        this.orderService = orderService;
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        // Initialize models with columns
        String[] columns = {"Order ID", "Customer", "Total Amount", "Status", "Items"};
        pendingModel = new DefaultTableModel(columns, 0);
        processingModel = new DefaultTableModel(columns, 0);
        doneModel = new DefaultTableModel(columns, 0);
        allModel = new DefaultTableModel(columns, 0);

        // Initialize tables
        pendingTable = new JTable(pendingModel);
        processingTable = new JTable(processingModel);
        doneTable = new JTable(doneModel);
        allTable = new JTable(allModel);

        // Setup table properties
        setupTable(pendingTable);
        setupTable(processingTable);
        setupTable(doneTable);
        setupTable(allTable);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("⏳ Pending Orders", new JScrollPane(pendingTable));
        tabbedPane.add("🔄 Processing Orders", new JScrollPane(processingTable));
        tabbedPane.add("✅ Done Orders", new JScrollPane(doneTable));
        tabbedPane.add("📦 All Orders", new JScrollPane(allTable));

        // Add control panel
        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton btnRefresh = new JButton("Refresh");
        JButton btnProceed = new JButton("Proceed →");
        JButton btnUndo = new JButton("← Undo");

        // New update controls
        JComboBox<String> statusDropdown = new JComboBox<>();
        JButton btnUpdate = new JButton("Update");

        controlPanel.add(btnRefresh);
        controlPanel.add(btnProceed);
        controlPanel.add(btnUndo);
        controlPanel.add(new JLabel("Change status:"));
        controlPanel.add(statusDropdown);
        controlPanel.add(btnUpdate);

        btnRefresh.addActionListener(e -> loadOrders());

        btnProceed.addActionListener(e -> {
            int selectedRow = getCurrentTable(tabbedPane).getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = getCurrentModel(tabbedPane);
                int orderId = (int) model.getValueAt(selectedRow, 0);
                String currentStatus = (String) model.getValueAt(selectedRow, 3);
                orderService.ProceedStatus(orderId, nextStatus(currentStatus));
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order first");
            }
        });

        btnUndo.addActionListener(e -> {
            int selectedRow = getCurrentTable(tabbedPane).getSelectedRow();
            if (selectedRow >= 0) {
                DefaultTableModel model = getCurrentModel(tabbedPane);
                int orderId = (int) model.getValueAt(selectedRow, 0);
                orderService.UndoStatus(orderId);
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order first");
            }
        });

        // Populate dropdown when row is selected
        ListSelectionListener selectionListener = e -> {
            if (!e.getValueIsAdjusting()) {
                JTable currentTable = getCurrentTable(tabbedPane);
                int selectedRow = currentTable.getSelectedRow();
                statusDropdown.removeAllItems();
                if (selectedRow >= 0) {
                    DefaultTableModel model = getCurrentModel(tabbedPane);
                    String currentStatus = (String) model.getValueAt(selectedRow, 3);

                    // Example statuses
                    String[] allStatuses = {"Pending", "Processing", "Done"};
                    for (String status : allStatuses) {
                        if (!status.equalsIgnoreCase(currentStatus)) {
                            statusDropdown.addItem(status);
                        }
                    }
                }
            }
        };

        pendingTable.getSelectionModel().addListSelectionListener(selectionListener);
        processingTable.getSelectionModel().addListSelectionListener(selectionListener);
        doneTable.getSelectionModel().addListSelectionListener(selectionListener);
        allTable.getSelectionModel().addListSelectionListener(selectionListener);

        btnUpdate.addActionListener(e -> {
            JTable currentTable = getCurrentTable(tabbedPane);
            int selectedRow = currentTable.getSelectedRow();
            if (selectedRow >= 0 && statusDropdown.getSelectedItem() != null) {
                DefaultTableModel model = getCurrentModel(tabbedPane);
                int orderId = (int) model.getValueAt(selectedRow, 0);
                String newStatus = (String) statusDropdown.getSelectedItem();
                orderService.updateStatus(orderId, newStatus);
                loadOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Please select an order and a status");
            }
        });

        add(tabbedPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Load initial data
        loadOrders();
    }

    private void setupTable(JTable table) {
        table.setRowHeight(30);
        table.getColumnModel().getColumn(4).setPreferredWidth(300); // Items column wider
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }

    private void loadOrders() {
        // Clear all models
        pendingModel.setRowCount(0);
        processingModel.setRowCount(0);
        doneModel.setRowCount(0);
        allModel.setRowCount(0);

        // Load pending orders
        for (Order order : orderService.getPendingOrders()) {
            addOrderToModel(order, pendingModel);
            addOrderToModel(order, allModel);
        }

        // Load processing orders
        for (Order order : orderService.getProcessingOrders()) {
            addOrderToModel(order, processingModel);
            addOrderToModel(order, allModel);
        }

        // Load done orders
        for (Order order : orderService.getDoneOrders()) {
            addOrderToModel(order, doneModel);
            addOrderToModel(order, allModel);
        }
    }

    private void addOrderToModel(Order order, DefaultTableModel model) {
        StringBuilder items = new StringBuilder();
        order.getItems().forEach(item ->
            items.append(item.getBook().getTitle())
                 .append(" (x")
                 .append(item.getQuantity())
                 .append("), "));

        model.addRow(new Object[]{
            order.getOrderId(),
            order.getCustomerName(),
            String.format("$%.2f", order.getTotalAmount()),
            order.getCurrentStatus(),
            items.toString().replaceAll(", $", "")
        });
    }

    private JTable getCurrentTable(JTabbedPane tabbedPane) {
        return switch (tabbedPane.getSelectedIndex()) {
            case 0 -> pendingTable;
            case 1 -> processingTable;
            case 2 -> doneTable;
            case 3 -> allTable;
            default -> null;
        };
    }

    private DefaultTableModel getCurrentModel(JTabbedPane tabbedPane) {
        return switch (tabbedPane.getSelectedIndex()) {
            case 0 -> pendingModel;
            case 1 -> processingModel;
            case 2 -> doneModel;
            case 3 -> allModel;
            default -> null;
        };
    }

    private String nextStatus(String current) {
        return switch (current.toLowerCase()) {
            case "pending" -> "Processing";
            case "processing" -> "Done";
            case "done" -> null;
            default -> null;
        };
    }
}
