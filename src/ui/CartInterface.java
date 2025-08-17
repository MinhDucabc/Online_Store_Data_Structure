package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import models.Book;
import models.CartItem;
import models.Customer;
import models.Order;
import services.User.CartService;
import services.User.OrderService;
import services.BookService;
import services.checkout.CheckoutService;

public class CartInterface extends JFrame {
    private final CartService cartService;
    private final BookService bookService;
    private final OrderService orderService;
    private final Customer currentCustomer; 
    private final CheckoutService checkoutService;
    
    private final BookReloadCallback reloadCallback;

    private JPanel cartPanel;
    private JLabel totalLabel;

    @FunctionalInterface
    public interface BookReloadCallback {
        void onBooksNeedReload();
    }

    public CartInterface(CartService cartService,
                         BookService bookService,
                         OrderService orderService,
                         Customer currentCustomer,
                         BookReloadCallback reloadCallback) {
        this.cartService = cartService;
        this.bookService = bookService;
        this.orderService = orderService;
        this.currentCustomer = currentCustomer;
        this.checkoutService = new CheckoutService(bookService, cartService, orderService);
        this.reloadCallback = reloadCallback;

        initUI();
        loadCartItems();
    }

    private void initUI() {
        setTitle("🛒 Shopping Cart");
        setSize(900, 600);
        setLocationRelativeTo(null);

        // ========== LEFT: CART ITEMS (cards) ==========
        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        JScrollPane cartScroll = new JScrollPane(cartPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel("Total: $0.0");
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JButton clearBtn = new JButton("Clear Cart");
        clearBtn.addActionListener(e -> {
            cartService.clearCart();
            loadCartItems();
        });
        bottomPanel.add(clearBtn, BorderLayout.EAST);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(cartScroll, BorderLayout.CENTER);
        leftPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ========== RIGHT: CUSTOMER INFO + CHECKOUT ==========
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createTitledBorder("👤 Customer Info"));

        rightPanel.add(new JLabel("Name: " + currentCustomer.getName()));
        rightPanel.add(new JLabel("Email: " + currentCustomer.getEmail()));
        rightPanel.add(new JLabel("Phone: " + currentCustomer.getPhoneNumber()));
        rightPanel.add(new JLabel("Address: " + currentCustomer.getAddress()));

        rightPanel.add(Box.createVerticalStrut(20));

        JButton checkoutBtn = new JButton("✅ Checkout");
        checkoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutBtn.addActionListener(e -> {
            try {
                Order order = checkoutService.checkout(currentCustomer);
                JOptionPane.showMessageDialog(this,
                        "✅ Đặt hàng thành công! Mã đơn hàng: " + order.getOrderId());
                cartService.clearCart();
                loadCartItems();
                reloadCallback.onBooksNeedReload(); // Notify to reload books

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Lỗi: " + ex.getMessage());
            }
        });
        rightPanel.add(checkoutBtn);

        // ========== SPLIT ==========
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(650);
        add(splitPane);
    }

    private void loadCartItems() {
        cartPanel.removeAll();

        for (CartItem item : cartService.getCartItems()) {
            Book book = item.getBook();

            JPanel card = new JPanel(new BorderLayout());
            card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));

            // IMAGE
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

            // INFO
            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.add(new JLabel("Title: " + book.getTitle()));
            infoPanel.add(new JLabel("Price: $" + String.format("%.2f", book.getPrice())));
            infoPanel.add(new JLabel("Quantity: " + item.getQuantity()));
            infoPanel.add(new JLabel("Subtotal: $" + String.format("%.2f", book.getPrice() * item.getQuantity())));
            card.add(infoPanel, BorderLayout.CENTER);

            // ACTIONS
            JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton removeBtn = new JButton("❌ Remove");
            removeBtn.addActionListener(e -> {
                cartService.removeFromCart(book.getBookId());
                loadCartItems();
            });
            actionPanel.add(removeBtn);
            card.add(actionPanel, BorderLayout.NORTH);

            cartPanel.add(card);
        }

        totalLabel.setText("Total: $" + cartService.getTotalAmount());
        cartPanel.revalidate();
        cartPanel.repaint();
    }
}
