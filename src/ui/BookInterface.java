package ui;

import models.Book;
import services.BookService;
import services.User.CartService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Set;

public class BookInterface {
    public static class BookListUI extends JFrame {
        private BookService bookService;
        private CartService cartService;

        private JList<String> categoryList;
        private JPanel bookPanel;
        private JTextField searchField;
        private JComboBox<String> sortCombo;
        private JComboBox<String> orderCombo; // Thay thế ascCheckBox bằng orderCombo

        public BookListUI() {
            bookService = new BookService();
            cartService = new CartService();
            initUI();
            loadCategories();
            loadBooks(bookService.getAllBooks());
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

            // ===== NAVBAR =====
            JPanel navbarPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            navbarPanel.setBackground(new Color(240, 240, 240));
            navbarPanel.add(new JLabel("📚 Online Book Store"));

            // Nút View Cart trên navbar
            JButton viewCartButton = new JButton("🛒 View Cart");
            viewCartButton.addActionListener(e -> new CartUI(cartService).setVisible(true));
            navbarPanel.add(viewCartButton);

            // Top filter panel
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.add(new JLabel("Search:"));
            searchField = new JTextField(15);
            topPanel.add(searchField);

            JButton searchButton = new JButton("Search");
            searchButton.addActionListener(e -> applySearchAndSort());
            topPanel.add(searchButton);

            topPanel.add(new JLabel("Sort by:"));
            sortCombo = new JComboBox<>(new String[] { "--Select--", "Title", "Author", "Price", "Date" });
            sortCombo.addActionListener(e -> applySearchAndSort());
            topPanel.add(sortCombo);

            orderCombo = new JComboBox<>(new String[] { "Ascending", "Descending" });
            orderCombo.addActionListener(e -> applySearchAndSort());
            topPanel.add(orderCombo);

            // Add navbar and topPanel to rightPanel
            JPanel topContainer = new JPanel();
            topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
            topContainer.add(navbarPanel);
            topContainer.add(topPanel);

            rightPanel.add(topContainer, BorderLayout.NORTH);

            rightPanel.add(topPanel, BorderLayout.NORTH);

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
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120)); // chiều cao cố định

                // Hiển thị ảnh thật từ Book (nếu có), nếu không thì dùng placeholder
                JLabel imageLabel;

                File file = new File(book.getImage());
                System.out.println("Loading image for book: " + book.getTitle() + " from " + file.getAbsolutePath());
                if (file.exists()) {
                    ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                    Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    imageLabel = new JLabel(new ImageIcon(img), SwingConstants.CENTER);
                } else {
                    imageLabel = new JLabel("📖", SwingConstants.CENTER);
                    System.out.println("Image not found for book: " + book.getTitle());
                }
                imageLabel.setPreferredSize(new Dimension(100, 100));
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                card.add(imageLabel, BorderLayout.WEST);

                // Thông tin sách
                JPanel infoPanel = new JPanel(new GridLayout(0, 1));
                infoPanel.add(new JLabel("Title: " + book.getTitle()));
                infoPanel.add(new JLabel("Author: " + book.getAuthor()));
                infoPanel.add(new JLabel("Category: " + book.getCategory()));
                infoPanel.add(new JLabel("Price: $" + book.getPrice()));
                infoPanel.add(new JLabel("Published: " + book.getPublishedDate()));
                card.add(infoPanel, BorderLayout.CENTER);

                bookPanel.add(card);

                // Drop down list hien thi so luong muon theo vao
                JPanel details = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                JComboBox<Integer> quantityCombo = new JComboBox<>();
                for (int i = 1; i <= 10; i++) {
                    quantityCombo.addItem(i);
                }

                JButton addToCartBtn = new JButton("Add to Cart");
                addToCartBtn.addActionListener(ev -> {
                    cartService.addToCart(book, (int) quantityCombo.getSelectedItem());
                    JOptionPane.showMessageDialog(this, book.getTitle() + " added to cart!");
                });
                details.add(addToCartBtn);

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

            // Tìm kiếm: có thể chọn search theo Title hoặc Author (ví dụ: mặc định là
            // Title)
            List<Book> result;
            if (!keyword.isEmpty()) {
                // có thể nâng cấp: cho user chọn search mode (Title/Author)
                result = bookService.searchByTitle(keyword);
                result.addAll(bookService.searchByAuthor(keyword)); // gộp kết quả
            } else {
                result = bookService.getAllBooks();
            }
            Set<Book> resultSet = new java.util.HashSet<>(result);
            List<Book> resultList = new java.util.ArrayList<>(resultSet);

            // Sort
            String sortField = (String) sortCombo.getSelectedItem();
            boolean ascending = orderCombo.getSelectedItem().equals("Ascending");

            // Apply sorting to the result list
            List<Book> sortedResult = switch (sortField) {
                case "Title" -> bookService.sortBooksByTitle(resultList, ascending);
                case "Author" -> bookService.sortBooksByAuthor(resultList, ascending);
                case "Price" -> bookService.sortBooksByPrice(resultList, ascending);
                case "Date" -> bookService.sortBooksByPublishedDate(resultList, ascending);
                default -> resultList;
            };

            loadBooks(sortedResult);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookListUI().setVisible(true));
    }
}