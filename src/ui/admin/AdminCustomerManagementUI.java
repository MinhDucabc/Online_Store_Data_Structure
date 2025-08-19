package ui.admin;

import models.Admin;
import models.Customer;
import models.User;
import services.Admin.CustomerManagementService;
import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort.SortType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminCustomerManagementUI extends JPanel {
    private final CustomerManagementService customerService;

    private JTable allTable, customerTable, adminTable;
    private DefaultTableModel allModel, customerModel, adminModel;

    private JComboBox<SearchType> searchTypeBox;
    private JComboBox<SortType> sortTypeBox;
    private JTextField searchField;

    public AdminCustomerManagementUI(CustomerManagementService customerService) {
        this.customerService = customerService;

        setLayout(new BorderLayout());

        // Tabs
        JTabbedPane tabPane = new JTabbedPane();

        allModel = new DefaultTableModel(new String[]{"ID", "Name", "Role"}, 0);
        customerModel = new DefaultTableModel(new String[]{"ID", "Name"}, 0);
        adminModel = new DefaultTableModel(new String[]{"ID", "Name"}, 0);

        allTable = new JTable(allModel);
        customerTable = new JTable(customerModel);
        adminTable = new JTable(adminModel);

        tabPane.add("👥 All Users", new JScrollPane(allTable));
        tabPane.add("🛒 Customers", new JScrollPane(customerTable));
        tabPane.add("🛡 Admins", new JScrollPane(adminTable));

        // Controls
        JPanel controlPanel = new JPanel(new FlowLayout());

        searchField = new JTextField(15);
        searchTypeBox = new JComboBox<>(SearchType.values());
        sortTypeBox = new JComboBox<>(SortType.values());

        JButton btnSearch = new JButton("Search");
        JButton btnSortAsc = new JButton("Sort Asc");
        JButton btnSortDesc = new JButton("Sort Desc");
        JButton btnAdd = new JButton("Add User");
        JButton btnDelete = new JButton("Delete User");
        JButton btnRefresh = new JButton("Refresh");

        controlPanel.add(new JLabel("Keyword:"));
        controlPanel.add(searchField);
        controlPanel.add(new JLabel("SearchType:"));
        controlPanel.add(searchTypeBox);
        controlPanel.add(new JLabel("SortType:"));
        controlPanel.add(sortTypeBox);
        controlPanel.add(btnSearch);
        controlPanel.add(btnSortAsc);
        controlPanel.add(btnSortDesc);
        controlPanel.add(btnAdd);
        controlPanel.add(btnDelete);
        controlPanel.add(btnRefresh);

        add(tabPane, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Load initial data
        loadUsers();

        // Actions
        btnSearch.addActionListener(e -> searchUsers());
        btnSortAsc.addActionListener(e -> sortUsers(true));
        btnSortDesc.addActionListener(e -> sortUsers(false));
        btnAdd.addActionListener(e -> addUserDialog());
        btnDelete.addActionListener(e -> deleteSelectedUser(tabPane));
        btnRefresh.addActionListener(e -> loadUsers());
    }

    private void loadUsers() {
        allModel.setRowCount(0);
        customerModel.setRowCount(0);
        adminModel.setRowCount(0);

        for (User u : customerService.getAllUsers()) {
            String role = (u instanceof Admin) ? "Admin" : "Customer";
            allModel.addRow(new Object[]{u.getUserId(), u.getName(), role});

            if (u instanceof Customer) {
                customerModel.addRow(new Object[]{u.getUserId(), u.getName()});
            } else if (u instanceof Admin) {
                adminModel.addRow(new Object[]{u.getUserId(), u.getName()});
            }
        }
    }

    private void searchUsers() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Vui lòng nhập từ khóa.");
            return;
        }

        SearchType searchType = (SearchType) searchTypeBox.getSelectedItem();
        SortType sortType = (SortType) sortTypeBox.getSelectedItem();

        List<User> results = customerService.searchByName(keyword, searchType, sortType);

        allModel.setRowCount(0);
        for (User u : results) {
            String role = (u instanceof Admin) ? "Admin" : "Customer";
            allModel.addRow(new Object[]{u.getUserId(), u.getName(), role});
        }
    }

    private void sortUsers(boolean ascending) {
        SortType sortType = (SortType) sortTypeBox.getSelectedItem();
        List<User> sorted = customerService.sortUsersByName(customerService.getAllUsers(), ascending, sortType);

        allModel.setRowCount(0);
        for (User u : sorted) {
            String role = (u instanceof Admin) ? "Admin" : "Customer";
            allModel.addRow(new Object[]{u.getUserId(), u.getName(), role});
        }
    }

    private void addUserDialog() {
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField phoneField = new JTextField();
        String[] roles = {"Customer", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);

        Object[] message = {
                "Name:", nameField,
                "Email:", emailField,
                "Password:", passwordField,
                "Address (optional):", addressField,
                "Phone Number (optional):", phoneField,
                "Role:", roleBox
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add User", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText().trim();
                String email = emailField.getText().trim();
                String password = passwordField.getText().trim();
                String address = addressField.getText().trim();
                String phone = phoneField.getText().trim();
                String role = (String) roleBox.getSelectedItem();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "⚠ Name, Email, and Password are required for Customer.");
                    return;
                }

                User newUser;
                if (role.equals("Admin")) {
                    newUser = new Admin(name, email, password,
                            address.isEmpty() ? null : address,
                            phone.isEmpty() ? null : phone, "Admin");
                } else {
                    newUser = new Customer(name, email, password,
                            address.isEmpty() ? null : address,
                            phone.isEmpty() ? null : phone);
                }
                customerService.addUser(newUser);
                loadUsers();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ ID must be a number.");
            }
        }
    }

    private void deleteSelectedUser(JTabbedPane tabPane) {
        JTable table = switch (tabPane.getSelectedIndex()) {
            case 0 -> allTable;
            case 1 -> customerTable;
            case 2 -> adminTable;
            default -> null;
        };

        if (table == null) return;

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠ Hãy chọn user cần xóa.");
            return;
        }

        int userId = (int) table.getValueAt(row, 0);
        customerService.deleteUser(userId);
        loadUsers();
    }

    public static void main(String[] args) {
        CustomerManagementService service = new CustomerManagementService();
        SwingUtilities.invokeLater(() -> new AdminCustomerManagementUI(service).setVisible(true));
    }
}
