package ui;

import models.Book;
import models.Customer; // Add this import

import services.Auth.AuthService;
import services.BookService;
import services.Admin.OrderManagementService;
import services.Admin.CustomerManagementService;
import services.Admin.BookManagementService;
import services.User.CartService;
import services.User.OrderService;
import structures.orderqueuedone.OrderQueueDone;
import structures.orderqueuepending.OrderQueuePending;
import structures.orderqueueprocessing.OrderQueueProcessing;
import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort.SortType;
import services.checkout.CheckoutService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Set;

public class BookInterface {
    public static class BookListUI extends JFrame {
        private AuthService authService;
        private BookService bookService;
        private CartService cartService;
        private OrderService orderService; // Thêm OrderService

        private OrderManagementService orderManagementService; // Thêm OrderManagementService
        private CustomerManagementService customerService; // Thêm CustomerService
        private BookManagementService bookManagementService; // Thêm BookManagementService

        private Customer currentCustomer; // Biến toàn cục để lưu thông tin khách hàng
        private boolean isAdmin = false; // Biến toàn cục để lưu trạng thái Admin
        private JButton authBtn, profileBtn, logoutBtn, adminBtn; // Thêm biến toàn cục cho các nút
        private JList<String> categoryList;
        private JPanel bookPanel;
        private JTextField searchField;
        private JComboBox<String> sortCombo;
        private JComboBox<String> orderCombo; // Thay thế ascCheckBox bằng orderCombo
        private SearchType currentSearchType = SearchType.LINEAR; // Biến toàn cục để lưu search type
        private SortType currentSortType = SortType.INSERTION; // Biến toàn cục để lưu sort type
        private JLabel welcomeLabel; // Thêm biến toàn cục cho welcome label

        public BookListUI() {
            bookService = new BookService();
            cartService = new CartService();
            authService = new AuthService();
            orderService = new OrderService();

            OrderQueuePending orderQueuePending = new OrderQueuePending();
            OrderQueueProcessing orderQueueProcessing = new OrderQueueProcessing();
            OrderQueueDone orderQueueDone = new OrderQueueDone();

            orderManagementService = new OrderManagementService(
                orderQueuePending,
                orderQueueProcessing,
                orderQueueDone
            );
            customerService = new CustomerManagementService();
            bookManagementService = new BookManagementService();
            initUI();
            loadCategories();
            loadBooks(bookService.getAllBooks());
        }

        private void updateWelcomeLabel(Customer customer, JLabel welcomeLabel) {
            if (customer != null) {
                if (isAdmin) {
                    welcomeLabel.setText("Welcome, Admin " + customer.getName());
                } else {
                    welcomeLabel.setText("Welcome, " + customer.getName());
                }
            } else {
                welcomeLabel.setText("Not logged in");
            }
        }

        private void setVisiblewhenAuthChanged(Customer customer, JButton profileBtn, JButton logoutBtn,
            JButton authBtn, JButton adminBtn) {
            currentCustomer = customer;
            
            // First hide all buttons
            profileBtn.setVisible(false);
            logoutBtn.setVisible(false);
            authBtn.setVisible(false);
            adminBtn.setVisible(true);

            if (customer != null) {  // User is logged in (either admin or customer)
                profileBtn.setVisible(true);
                logoutBtn.setVisible(true);  // Show logout for both admin and customer
                authBtn.setVisible(false);
                
                if (isAdmin) {  // Additional admin button for admin users
                    adminBtn.setVisible(true);
                }
            } else {  // No user logged in
                authBtn.setVisible(true);
            }
        }

        private void initUI() {
            setTitle("📚 Book Store");
            setSize(900, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            // ===== LEFT PANEL: Category List =====
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.add(new JLabel("Categories", SwingConstants.CENTER), BorderLayout.NORTH);
            categoryList = new JList<>(new DefaultListModel<>());
            categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            categoryList.addListSelectionListener(e -> applyCategoryFilter());
            leftPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

            // ===== RIGHT PANEL: Search + Sort + Book Cards =====
            JPanel rightPanel = new JPanel(new BorderLayout());

            // Above Navbar
            JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            headerPanel.setBackground(new Color(240, 240, 240));
            headerPanel.add(new JLabel("📚 Online Book Store"));

            // ===== NAVBAR =====
            JPanel navbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            navbarPanel.setBackground(new Color(240, 240, 240));

            // Top filter panel
            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

            // Search row
            JPanel searchRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchRow.add(new JLabel("Search:"));
            searchField = new JTextField(15);
            searchRow.add(searchField);

            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(e -> applySearchAndSort());
            searchRow.add(searchButton);

            JComboBox<String> searchTypeCombo = new JComboBox<>(new String[] {
                    SearchType.LINEAR.name(),
                    SearchType.BINARY_TREE.name()
            });

            searchTypeCombo.addActionListener(e -> {
                String selectedSearchType = (String) searchTypeCombo.getSelectedItem();
                currentSearchType = SearchType.valueOf(selectedSearchType);
            });

            searchRow.add(new JLabel("Search Type:"));
            searchRow.add(searchTypeCombo);

            // Sort row
            JPanel sortRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
            sortRow.add(new JLabel("Sort by:"));
            sortCombo = new JComboBox<>(new String[] { "Title", "Author", "Price", "Date" });
            sortCombo.setSelectedIndex(0);
            sortCombo.addActionListener(e -> applySearchAndSort());
            sortRow.add(sortCombo);

            orderCombo = new JComboBox<>(new String[] { "Ascending", "Descending" });
            orderCombo.addActionListener(e -> applySearchAndSort());
            sortRow.add(orderCombo);

            JComboBox<String> sortTypeCombo = new JComboBox<>(new String[] {
                    SortType.INSERTION.name(),
                    SortType.SELECTION.name(),
            });
            sortTypeCombo.addActionListener(e -> {
                String selectedSortType = (String) sortTypeCombo.getSelectedItem();
                currentSortType = SortType.valueOf(selectedSortType);
                applySearchAndSort();
            });

            sortRow.add(new JLabel("Sort Type:"));
            sortRow.add(sortTypeCombo);

            // Add rows to topPanel
            topPanel.add(searchRow);
            topPanel.add(sortRow);

            // Add navbar and topPanel to rightPanel
            JPanel topContainer = new JPanel();
            topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
            topContainer.add(headerPanel);  
            topContainer.add(navbarPanel);
            topContainer.add(topPanel);

            rightPanel.add(topContainer, BorderLayout.NORTH);

            // Giỏ hàng icon
            JButton cartBtn = new JButton("🛒 Cart");
            cartBtn.addActionListener(e -> {
                if (!authService.isLoggedIn()) {
                    JOptionPane.showMessageDialog(this,
                            "⚠️ Please login first to access your cart!",
                            "Not Logged In", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (currentCustomer == null) {
                    JOptionPane.showMessageDialog(this,
                            "⚠️ Customer information not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                CartInterface cartInterface = new CartInterface(
                        cartService,
                        bookService,
                        orderService,
                        currentCustomer,
                        () -> loadBooks(bookService.getAllBooks()) // Add reload callback
                );
                cartInterface.setVisible(true);
            });

            navbarPanel.add(cartBtn);

            // Khoi tao cac nut
            profileBtn = new JButton("👤 Profile");
            logoutBtn = new JButton("🚪 Logout");
            authBtn = new JButton("🔑 Authentication");
            adminBtn = new JButton("⚙️ Admin Panel");

            // Nút Authentication
            authBtn.addActionListener(e -> {
                AuthenticationInterface authUI = new AuthenticationInterface(authService, cartService, customer -> {
                    currentCustomer = customer;
                    isAdmin = authService.isAdminMode();
                    updateWelcomeLabel(currentCustomer, welcomeLabel);
                    setVisiblewhenAuthChanged(currentCustomer, profileBtn, logoutBtn, authBtn, adminBtn); // Cập nhật giao diện
                                                                                                // khi đăng nhập
                });
                authUI.setVisible(true);
            });
            navbarPanel.add(authBtn);

            // Nut Profile
            profileBtn.addActionListener(e -> {
                if (!authService.isLoggedIn()) {
                    JOptionPane.showMessageDialog(this,
                            "⚠️ Please login first to view your profile!",
                            "Not Logged In", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // currentCustomer = authService.getLoggedInCustomer();
                if (currentCustomer == null) {
                    JOptionPane.showMessageDialog(this,
                            "⚠️ Customer information not found!",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                ProfileUI profileUI = new ProfileUI(currentCustomer, orderService);
                profileUI.setVisible(true);
            });
            navbarPanel.add(profileBtn);

            // Welcome label
            welcomeLabel = new JLabel();
            welcomeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            updateWelcomeLabel(currentCustomer, welcomeLabel);
            headerPanel.add(welcomeLabel);

            // Nut Admin
            adminBtn.addActionListener(e -> {
                AdminInterface adminUI = new AdminInterface(orderManagementService, customerService, bookManagementService);
                adminUI.setVisible(true);
            });
            navbarPanel.add(adminBtn);

            // ==== Nút Logout ====
            logoutBtn.addActionListener(e -> {
                if (!authService.isLoggedIn()) {
                    JOptionPane.showMessageDialog(this,
                            "⚠️ You are not logged in!",
                            "Logout", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                authService.logout(cartService);
                currentCustomer = null;
                updateWelcomeLabel(null, welcomeLabel);
                setVisiblewhenAuthChanged(null, profileBtn, logoutBtn, authBtn, adminBtn); // Cập nhật giao diện khi đăng xuất

                JOptionPane.showMessageDialog(this,
                        "✅ You have been logged out successfully!",
                        "Logout", JOptionPane.INFORMATION_MESSAGE);
            });
            navbarPanel.add(logoutBtn);

            // Cap nhat trang thai hien thi khi dang nhap/khong dang nhap
            setVisiblewhenAuthChanged(currentCustomer, profileBtn, logoutBtn, authBtn, adminBtn);

            // Book display panel (cards)
            bookPanel = new JPanel();
            bookPanel.setLayout(new BoxLayout(bookPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(bookPanel);
            rightPanel.add(scrollPane, BorderLayout.CENTER);

            // ===== SPLIT PANE =====
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
            splitPane.setDividerLocation(200); // 1/5 width for categories
            add(splitPane);
        }

        private void loadCategories() {
            DefaultListModel<String> model = (DefaultListModel<String>) categoryList.getModel();
            model.addElement("All");
            for (String c : bookService.getAllCategories()) {
                model.addElement(c);
            }
            categoryList.setSelectedIndex(0);
        }

        private void loadBooks(List<Book> books) {
            bookPanel.removeAll();

            for (Book book : books) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

                // ========== IMAGE ==========
                JLabel imageLabel;
                File file = new File(book.getImage());
                if (file.exists()) {
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                } else {
                    imageLabel = new JLabel("📖", SwingConstants.CENTER);
                }
                imageLabel.setPreferredSize(new Dimension(100, 100));
                card.add(imageLabel, BorderLayout.WEST);

                // ========== INFO ==========
                JPanel infoPanel = new JPanel(new GridLayout(0, 1));
                infoPanel.add(new JLabel("Title: " + book.getTitle()));
                infoPanel.add(new JLabel("Author: " + book.getAuthor()));
                infoPanel.add(new JLabel("Category: " + book.getCategory()));
                infoPanel.add(new JLabel("Price: $" + String.format("%.2f", book.getPrice())));
                infoPanel.add(new JLabel("Published: " + book.getPublishedDate()));

                card.add(infoPanel, BorderLayout.CENTER);

                // ========== ACTIONS (nút + combo số lượng) ==========
                JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                qtyPanel.add(new JLabel("Quantity: " + book.getQuantity()));

                JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

                String[] qtyOptions = new String[book.getQuantity()];
                for (int i = 0; i < book.getQuantity(); i++) {
                    qtyOptions[i] = String.valueOf(i + 1);
                }
                JComboBox<String> qtyCombo = new JComboBox<>(qtyOptions);

                JButton addToCartBtn = new JButton("➕ Add to Cart");
                addToCartBtn.addActionListener(e -> {
                    if (!authService.isLoggedIn()) {
                        JOptionPane.showMessageDialog(this,
                                "⚠️ Please login first before adding items to cart!",
                                "Not Logged In", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    int quantity = Integer.parseInt((String) qtyCombo.getSelectedItem());
                    cartService.addToCart(book, quantity);
                    JOptionPane.showMessageDialog(this,
                            "✅ Added " + quantity + " × " + book.getTitle() + " to cart!");
                });

                actionPanel.add(qtyPanel);
                actionPanel.add(qtyCombo);
                actionPanel.add(addToCartBtn);

                card.add(actionPanel, BorderLayout.NORTH);

                bookPanel.add(card);
            }

            bookPanel.revalidate();
            bookPanel.repaint();
        }

        private void applyCategoryFilter() {
            String selected = categoryList.getSelectedValue();
            if (selected == null || selected.equals("All")) {
                loadBooks(bookService.getAllBooks());
            } else {
                loadBooks(bookService.getBooksByCategory(selected));
            }
        }

        // 2. Search + Sort (không động chạm tới category)
        private void applySearchAndSort() {
            String keyword = searchField.getText().trim();
            String selectedCategory = categoryList.getSelectedValue();

            List<Book> result;

            long searchStart = System.nanoTime();

            if (!keyword.isEmpty()) {
                if (currentSearchType == SearchType.LINEAR) {
                    result = bookService.searchByTitle(keyword, SearchType.LINEAR, currentSortType);
                    result.addAll(bookService.searchByAuthor(keyword, SearchType.LINEAR, currentSortType));
                    if (selectedCategory != null && !selectedCategory.equals("All")) {
                        result.removeIf(book -> !book.getCategory().equals(selectedCategory));
                    }
                } else {
                    result = bookService.searchByTitle(keyword, SearchType.BINARY_TREE, currentSortType);
                    result.addAll(bookService.searchByAuthor(keyword, SearchType.BINARY_TREE, currentSortType));
                    if (selectedCategory != null && !selectedCategory.equals("All")) {
                        result.removeIf(book -> !book.getCategory().equals(selectedCategory));
                    }
                }
            } else {
                result = bookService.getAllBooks();
                if (selectedCategory != null && !selectedCategory.equals("All")) {
                    result.removeIf(book -> !book.getCategory().equals(selectedCategory));
                }
            }

            long searchEnd = System.nanoTime();
            double searchMillis = (searchEnd - searchStart) / 1_000_000.0;

            Set<Book> resultSet = new java.util.HashSet<>(result);
            List<Book> resultList = new java.util.ArrayList<>(resultSet);

            String sortField = (String) sortCombo.getSelectedItem();
            boolean ascending = orderCombo.getSelectedItem().equals("Ascending");

            long sortStart = System.nanoTime();

            List<Book> sortedResult = switch (sortField) {
                case "Title" -> bookService.sortBooksByTitle(resultList, ascending, currentSortType);
                case "Author" -> bookService.sortBooksByAuthor(resultList, ascending, currentSortType);
                case "Price" -> bookService.sortBooksByPrice(resultList, ascending, currentSortType);
                case "Date" -> bookService.sortBooksByPublishedDate(resultList, ascending, currentSortType);
                default -> resultList;
            };

            long sortEnd = System.nanoTime();
            double sortMillis = (sortEnd - sortStart) / 1_000_000.0;

            // ===== 3. Hiển thị kết quả
            JOptionPane.showMessageDialog(this,
                    "Search Type: " + currentSearchType +
                            "\nSort Type: " + currentSortType +
                            "\nSearch Time: " + searchMillis + " ms" +
                            "\nSort Time: " + sortMillis + " ms");

            loadBooks(sortedResult);
        }

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookListUI().setVisible(true));
    }
}
