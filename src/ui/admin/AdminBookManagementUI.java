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


    private JTextField txtId, txtTitle, txtAuthor, txtPrice, txtDate, txtSearch;

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
        String[] sortOptions = {"Title", "Author", "Price", "PublishedDate"};
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
        String[] columns = {"ID", "Title", "Author", "Price", "Published Date"};
        tableModel = new DefaultTableModel(columns, 0);
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        // === BOTTOM: CRUD ===
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 5));
        txtId = new JTextField();
        txtTitle = new JTextField();
        txtAuthor = new JTextField();
        txtPrice = new JTextField();
        txtDate = new JTextField();

        inputPanel.add(new JLabel("ID"));
        inputPanel.add(new JLabel("Title"));
        inputPanel.add(new JLabel("Author"));
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(new JLabel("Published Date"));

        inputPanel.add(txtId);
        inputPanel.add(txtTitle);
        inputPanel.add(txtAuthor);
        inputPanel.add(txtPrice);
        inputPanel.add(txtDate);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnRefresh = new JButton("Refresh");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

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
            try {
                Book book = new Book(
                        txtTitle.getText().trim(),
                        txtAuthor.getText().trim(),
                        Double.parseDouble(txtPrice.getText().trim()),
                        1, // Default quantity
                        LocalDate.parse(txtDate.getText().trim(), DateTimeFormatter.ISO_DATE),
                        "General", // Default category
                        null // Default image
                );
                this.bookService.addBook(book);
                loadBooks(this.bookService.getAllBooks());
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input: Please ensure date is in YYYY-MM-DD format\n" + ex.getMessage());
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Book book = new Book(
                            txtTitle.getText().trim(),
                            txtAuthor.getText().trim(),
                            Double.parseDouble(txtPrice.getText().trim()),
                            1, // Default quantity
                            LocalDate.parse(txtDate.getText().trim(), DateTimeFormatter.ISO_DATE),
                            "General", // Default category
                            null // Default image
                    );
                    this.bookService.updateBook(book);
                    loadBooks(this.bookService.getAllBooks());
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(this, "Select a row to update");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid input: Please ensure date is in YYYY-MM-DD format\n" + ex.getMessage());
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                String bookId = (String) tableModel.getValueAt(selectedRow, 0);
                this.bookService.deleteBook(Integer.parseInt(bookId));
                loadBooks(this.bookService.getAllBooks());
            } else {
                JOptionPane.showMessageDialog(this, "Select a row to delete");
            }
        });

        bookTable.getSelectionModel().addListSelectionListener(e -> {
            int row = bookTable.getSelectedRow();
            if (row >= 0) {
                txtId.setText((String) tableModel.getValueAt(row, 0));
                txtTitle.setText((String) tableModel.getValueAt(row, 1));
                txtAuthor.setText((String) tableModel.getValueAt(row, 2));
                txtPrice.setText(String.valueOf(tableModel.getValueAt(row, 3)));
                txtDate.setText((String) tableModel.getValueAt(row, 4));
            }
        });
    }

    private void loadBooks(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                    b.getBookId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getPrice(),
                    b.getPublishedDate()
            });
        }
    }

    // Add helper method to clear fields
    private void clearFields() {
        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPrice.setText("");
        txtDate.setText("");
    }

}
