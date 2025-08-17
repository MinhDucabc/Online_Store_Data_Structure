package ui;

import javac.swing.*;
import javax.swing.table.DefaultTableModel;

import models.Book;
import models.CartItem;
import services.User.CartService;

public class CartInterface {
    private CartService cartService;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;

    public CartUI(CartService cartService) {
        this.cartService = cartService;
        initUI();
        loadCartItems();
    }

    private void initUI() {
        setTitle("🛒 Shopping Cart");
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] cols = { "ID", "Title", "Quantity", "Price", "Total" };
        tableModel = new DefaultTableModel(cols, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: $0.0");
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JButton clearBtn = new JButton("Clear Cart");
        clearBtn.addActionListener(e -> {
            cartService.clearCart();
            loadCartItems();
        });
        bottomPanel.add(clearBtn, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadCartItems() {
        tableModel.setRowCount(0);
        for (CartItem item : cartService.getCartItems()) {
            Book b = item.getBook();
            tableModel.addRow(new Object[] {
                b.getBookId(),
                b.getTitle(),
                item.getQuantity(),
                b.getPrice(),
                b.getPrice() * item.getQuantity()
            });
        }
        totalLabel.setText("Total: $" + cartService.getTotalAmount());
    }

    
}
