package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class UserInterface extends JFrame {
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JPanel bookListPanel;
    private JPanel cartPanel;
    private CardLayout cardLayout;

    public UserInterface() {
        // Set up the main frame
        setTitle("Online Bookstore - User Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen

        // Initialize card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        initializeLoginPanel();
        initializeBookListPanel();
        initializeCartPanel();

        // Add panels to main panel
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(bookListPanel, "BOOKLIST");
        mainPanel.add(cartPanel, "CART");

        // Add main panel to frame
        add(mainPanel);

        // Show login panel first
        cardLayout.show(mainPanel, "LOGIN");
    }

    private void initializeLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username field
        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        
        // Password field
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            // TODO: Add login logic here
            cardLayout.show(mainPanel, "BOOKLIST");
        });

        // Add components to panel
        gbc.gridx = 0; gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passField, gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);
    }

    private void initializeBookListPanel() {
        bookListPanel = new JPanel(new BorderLayout());
        
        // Create top menu panel
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cartButton = new JButton("View Cart");
        JButton logoutButton = new JButton("Logout");
        menuPanel.add(cartButton);
        menuPanel.add(logoutButton);

        // Create book list (using JTable as placeholder)
        String[] columnNames = {"Title", "Author", "Price", "Action"};
        Object[][] data = {}; // This will be populated with actual book data
        JTable bookTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(bookTable);

        // Add components to panel
        bookListPanel.add(menuPanel, BorderLayout.NORTH);
        bookListPanel.add(scrollPane, BorderLayout.CENTER);

        // Add action listeners
        cartButton.addActionListener(e -> cardLayout.show(mainPanel, "CART"));
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
    }

    private void initializeCartPanel() {
        cartPanel = new JPanel(new BorderLayout());
        
        // Create top menu panel
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Books");
        menuPanel.add(backButton);

        // Create cart list (using JTable as placeholder)
        String[] columnNames = {"Title", "Quantity", "Price", "Action"};
        Object[][] data = {}; // This will be populated with actual cart data
        JTable cartTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        // Create checkout panel
        JPanel checkoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total: $0.00");
        JButton checkoutButton = new JButton("Checkout");
        checkoutPanel.add(totalLabel);
        checkoutPanel.add(checkoutButton);

        // Add components to panel
        cartPanel.add(menuPanel, BorderLayout.NORTH);
        cartPanel.add(scrollPane, BorderLayout.CENTER);
        cartPanel.add(checkoutPanel, BorderLayout.SOUTH);

        // Add action listeners
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "BOOKLIST"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserInterface ui = new UserInterface();
            ui.setVisible(true);
        });
    }
}
