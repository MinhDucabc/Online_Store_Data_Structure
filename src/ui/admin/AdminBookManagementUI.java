package ui.admin;

import models.Book;
import services.Admin.BookManagementService;
import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort.SortType;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AdminBookManagementUI extends JPanel {
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private final BookManagementService bookService;


    private JTextField txtSearch;

    // Add these new fields
    private SearchType currentSearchType = SearchType.LINEAR;
    private SortType currentSortType = SortType.INSERTION;
    private JComboBox<String> searchTypeCombo;
    private JComboBox<String> sortTypeCombo;

    public AdminBookManagementUI(BookManagementService bookService) {
        this.bookService = bookService;
        setLayout(new BorderLayout());

        // === TOP: Search + Sort ===
        JPanel topPanel = new JPanel(new GridLayout(2, 1));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtSearch = new JTextField(15);
        JButton btnSearchTitle = new JButton("Search Title");
        JButton btnSearchAuthor = new JButton("Search Author");

        // Add search type combo
        searchTypeCombo = new JComboBox<>(new String[]{
                SearchType.LINEAR.name(),
                SearchType.BINARY_TREE.name()
        });
        searchTypeCombo.addActionListener(e -> {
            String selectedSearchType = (String) searchTypeCombo.getSelectedItem();
            currentSearchType = SearchType.valueOf(selectedSearchType);
        });

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearchTitle);
        searchPanel.add(btnSearchAuthor);
        searchPanel.add(new JLabel("Search Type:"));
        searchPanel.add(searchTypeCombo);

        // Sort panel
        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] sortOptions = {"Title", "Author", "Price",  "PublishedDate"};
        JComboBox<String> sortCombo = new JComboBox<>(sortOptions);
        JButton btnSortAsc = new JButton("Sort ↑");
        JButton btnSortDesc = new JButton("Sort ↓");

        // Add sort type combo
        sortTypeCombo = new JComboBox<>(new String[]{
                SortType.INSERTION.name(),
                SortType.SELECTION.name()
        });
        sortTypeCombo.addActionListener(e -> {
            String selectedSortType = (String) sortTypeCombo.getSelectedItem();
            currentSortType = SortType.valueOf(selectedSortType);
        });

        sortPanel.add(new JLabel("Sort by:"));
        sortPanel.add(sortCombo);
        sortPanel.add(btnSortAsc);
        sortPanel.add(btnSortDesc);
        sortPanel.add(new JLabel("Sort Type:"));
        sortPanel.add(sortTypeCombo);

        topPanel.add(searchPanel);
        topPanel.add(sortPanel);

        add(topPanel, BorderLayout.NORTH);

        // === CENTER: Table ===
        String[] columns = {"ID", "Title", "Author", "Price", "Quantity", "Published Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

    // === BOTTOM: CRUD BUTTONS ONLY ===
    JPanel buttonPanel = new JPanel(new FlowLayout());
    JButton btnAdd = new JButton("Add Book");
    JButton btnUpdate = new JButton("Update Book");
    JButton btnDelete = new JButton("Delete");
    JButton btnRefresh = new JButton("Refresh");

    buttonPanel.add(btnAdd);
    buttonPanel.add(btnUpdate);
    buttonPanel.add(btnDelete);
    buttonPanel.add(btnRefresh);

    add(buttonPanel, BorderLayout.SOUTH);

        // === Load data initially ===
        loadBooks(this.bookService.getAllBooks());

        // === Event handlers ===
        btnRefresh.addActionListener(e -> loadBooks(this.bookService.getAllBooks()));

        btnSearchTitle.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (!keyword.isEmpty()) {
                List<Book> result = this.bookService.searchByTitle(keyword, currentSearchType, currentSortType);
                loadBooks(result);
            }
        });

        btnSearchAuthor.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (!keyword.isEmpty()) {
                List<Book> result = this.bookService.searchByAuthor(keyword, currentSearchType, currentSortType);
                loadBooks(result);
            }
        });

        btnSortAsc.addActionListener((ActionEvent e) -> {
            String selected = (String) sortCombo.getSelectedItem();
            switch (selected) {
                case "Title":
                    loadBooks(this.bookService.sortBooksByTitle(this.bookService.getAllBooks(), true, currentSortType));
                    break;
                case "Author":
                    loadBooks(this.bookService.sortBooksByAuthor(this.bookService.getAllBooks(), true, currentSortType));
                    break;
                case "Price":
                    loadBooks(this.bookService.sortBooksByPrice(this.bookService.getAllBooks(), true, currentSortType));
                    break;
                case "PublishedDate":
                    loadBooks(this.bookService.sortBooksByPublishedDate(this.bookService.getAllBooks(), true, currentSortType));
                    break;
                default:
                    loadBooks(this.bookService.getAllBooks());
            }
        });

        btnSortDesc.addActionListener((ActionEvent e) -> {
            String selected = (String) sortCombo.getSelectedItem();
            switch (selected) {
                case "Title":
                    loadBooks(this.bookService.sortBooksByTitle(this.bookService.getAllBooks(), false, currentSortType));
                    break;
                case "Author":
                    loadBooks(this.bookService.sortBooksByAuthor(this.bookService.getAllBooks(), false, currentSortType));
                    break;
                case "Price":
                    loadBooks(this.bookService.sortBooksByPrice(this.bookService.getAllBooks(), false, currentSortType));
                    break;
                case "PublishedDate":
                    loadBooks(this.bookService.sortBooksByPublishedDate(this.bookService.getAllBooks(), false, currentSortType));
                    break;
                default:
                    loadBooks(this.bookService.getAllBooks());
            }
        });

        btnAdd.addActionListener(e -> {
            JTextField titleField = new JTextField();
            JTextField authorField = new JTextField();
            JTextField priceField = new JTextField();
            JTextField dateField = new JTextField();
            Object[] message = {
                "Title:", titleField,
                "Author:", authorField,
                "Price:", priceField,
                "Published Date (YYYY-MM-DD):", dateField
            };
            int option = JOptionPane.showConfirmDialog(this, message, "Add Book", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    Book book = new Book(
                        titleField.getText().trim(),
                        authorField.getText().trim(),
                        Double.parseDouble(priceField.getText().trim()),
                        1,
                        LocalDate.parse(dateField.getText().trim(), DateTimeFormatter.ISO_DATE),
                        "General",
                        null
                    );
                    this.bookService.addBook(book);
                    loadBooks(this.bookService.getAllBooks());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Invalid input: Please ensure date is in YYYY-MM-DD format\n" + ex.getMessage());
                }
            }
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                String currentTitle = tableModel.getValueAt(selectedRow, 1).toString();
                String currentAuthor = tableModel.getValueAt(selectedRow, 2).toString();
                String currentPrice = tableModel.getValueAt(selectedRow, 3).toString();
                String currentQuantity = tableModel.getValueAt(selectedRow, 4).toString();
                String currentDate = tableModel.getValueAt(selectedRow, 5).toString();
                JTextField titleField = new JTextField(currentTitle);
                JTextField authorField = new JTextField(currentAuthor);
                JTextField priceField = new JTextField(currentPrice);
                JTextField quantityField = new JTextField(currentQuantity);
                JTextField dateField = new JTextField(currentDate);
                Object[] message = {
                    "Title:", titleField,
                    "Author:", authorField,
                    "Price:", priceField,
                    "Quantity:", quantityField,
                    "Published Date (YYYY-MM-DD):", dateField
                };
                int option = JOptionPane.showConfirmDialog(this, message, "Update Book", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        Object idObj = tableModel.getValueAt(selectedRow, 0);
                        int bookId;
                        if (idObj instanceof Integer) {
                            bookId = (Integer) idObj;
                        } else {
                            bookId = Integer.parseInt(idObj.toString());
                        }
                        Book book = new Book(
                            titleField.getText().trim(),
                            authorField.getText().trim(),
                            Double.parseDouble(priceField.getText().trim()),
                            Integer.parseInt(quantityField.getText().trim()),
                            LocalDate.parse(dateField.getText().trim(), DateTimeFormatter.ISO_DATE),
                            "General",
                            null
                            
                        );
                        book.setBookId(bookId);
                        // book.setBookId(bookId); // Set the correct book ID
                        this.bookService.updateBook(book);
                        loadBooks(this.bookService.getAllBooks());
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                            "Invalid input: Please ensure all fields are correct and date is in YYYY-MM-DD format\n" + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Select a row to update");
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Object idObj = tableModel.getValueAt(selectedRow, 0);
                int bookId;
                if (idObj instanceof Integer) {
                    bookId = (Integer) idObj;
                } else {
                    bookId = Integer.parseInt(idObj.toString());
                }
                this.bookService.deleteBook(bookId);
                loadBooks(this.bookService.getAllBooks());
            } else {
                JOptionPane.showMessageDialog(this, "Select a row to delete");
            }
        });

    // Removed selection listener for input fields (no longer needed)
    }

    private void loadBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getBookId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getPrice(),
                    b.getQuantity(),
                    b.getPublishedDate()
            });
        }
    }

    // Add helper method to clear fields
    // Removed clearFields method (no longer needed)

}
