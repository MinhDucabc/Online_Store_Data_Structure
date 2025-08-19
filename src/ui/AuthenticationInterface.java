package ui;

import services.Auth.AuthService;
import services.User.CartService;
import models.Customer;

import javax.swing.*;
import java.awt.*;

public class AuthenticationInterface extends JFrame {
    private AuthService authService;
    private CartService cartService;
    private AuthListener authListener;

    private JTextField emailField, nameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private JCheckBox adminToggle;

    public interface AuthListener {
        void onAuthChanged(Customer customer);
    }

    public AuthenticationInterface(AuthService authService, CartService cartService, AuthListener listener) {
        this.authService = authService;
        this.cartService = cartService;
        this.authListener = listener;
        initUI();
    }

    private void initUI() {
        setTitle("🔑 Authentication");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ---- Tabbed Pane ----
        JTabbedPane tabbedPane = new JTabbedPane();

        // ===== LOGIN TAB =====
        JPanel loginPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        loginPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        loginPanel.add(emailField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> handleLogin());
        loginPanel.add(new JLabel()); // filler
        loginPanel.add(loginBtn);

        tabbedPane.addTab("Login", loginPanel);

        // ===== REGISTER TAB =====
        JPanel registerPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        registerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        registerPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        registerPanel.add(nameField);

        registerPanel.add(new JLabel("Email:"));
        JTextField registerEmailField = new JTextField();
        registerPanel.add(registerEmailField);

        registerPanel.add(new JLabel("Password:"));
        JPasswordField registerPasswordField = new JPasswordField();
        registerPanel.add(registerPasswordField);

        JButton registerBtn = new JButton("Register");
        registerBtn.addActionListener(e -> handleRegister(registerEmailField, registerPasswordField));
        registerPanel.add(new JLabel()); // filler
        registerPanel.add(registerBtn);

        // Admin toggle
        adminToggle = new JCheckBox("Register as Admin");
        adminToggle.setToolTipText("Check to register as an Admin");
        adminToggle.addActionListener(e -> {
            authService.toggleRole();
            boolean isAdmin = authService.isAdminMode();
            registerBtn.setText(isAdmin ? "Register Admin" : "Register User");
        });
        registerPanel.add(new JLabel()); // filler
        registerPanel.add(adminToggle);

        tabbedPane.addTab("Register", registerPanel);

        // ===== STATUS LABEL =====
        statusLabel = new JLabel("Status: Not logged in", SwingConstants.CENTER);

        add(tabbedPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    // ✅ Handle Login
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Email và Password không được để trống!");
            return;
        }

        if (authService.login(email, password)) {
            statusLabel.setText("✅ Login successful!");
            Customer loggedIn;
            if (authService.isAdminMode()) {
                loggedIn = authService.getLoggedInAdmin();
            } else {
                loggedIn = authService.getLoggedInCustomer();
            }
            if (authListener != null) authListener.onAuthChanged(loggedIn);
            dispose();
        } else {
            statusLabel.setText("❌ Login failed!");
        }
    }

    // ✅ Handle Register
    private void handleRegister(JTextField registerEmailField, JPasswordField registerPasswordField) {
        String name = nameField.getText().trim();
        String email = registerEmailField.getText().trim();
        String password = new String(registerPasswordField.getPassword()).trim();
        boolean isAdmin = authService.isAdminMode();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ All fields are required!");
            return;
        }
        if (!isValidEmail(email)) {
            statusLabel.setText("⚠️ Invalid email format!");
            return;
        }
        if (password.length() < 6) {
            statusLabel.setText("⚠️ Password must be at least 6 characters!");
            return;
        }


        if (authService.register(name, email, password, isAdmin)) {
            statusLabel.setText("✅ Register successful as " + (isAdmin ? "Admin" : "User") + "!");
        } else {
            statusLabel.setText("❌ Register failed!");
        }
    }

    // ✅ Handle Logout
    private void handleLogout() {
        if (authService.isLoggedIn()) {
            authService.logout();
            statusLabel.setText("✅ Logged out successfully!");
            if (authListener != null) authListener.onAuthChanged(null);
            dispose();
        } else {
            statusLabel.setText("⚠️ You are not logged in!");
        }
    }

    // ✅ Helper: Email validation
    private boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }
}
