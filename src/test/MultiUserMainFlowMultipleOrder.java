package test;

import services.BookService;
import services.User.CartService;
import services.User.OrderService;
import services.checkout.CheckoutService;

import models.*;

import java.util.*; // import Random, Set, HashSet, List, ArrayList, Arrays

import data.UserData;
import data.BookData;

public class MultiUserMainFlowMultipleOrder {
    public static int order_length = 6;

    // public static void main(String[] args) {
    public static void generateMultiOrdersfromMultiUsers(OrderService orderService) {
        BookService bookService = new BookService();
        // OrderService orderService = new OrderService();

        // 1️⃣ Lấy ngẫu nhiên 3 customer khác nhau
        Random rand = new Random();
        int size = UserData.CUSTOMERS.size();
        if (size < 3)
            throw new IllegalArgumentException("Cần ít nhất 3 khách hàng");

        int i, j, k;
        do {
            i = rand.nextInt(size);
            j = rand.nextInt(size);
            k = rand.nextInt(size);
        } while (i == j || i == k || j == k);

        Customer customer1 = UserData.CUSTOMERS.get(i);
        Customer customer2 = UserData.CUSTOMERS.get(j);
        Customer customer3 = UserData.CUSTOMERS.get(k);

        List<Customer> customerList = new ArrayList<>();
        customerList.addAll(Arrays.asList(customer1, customer2, customer3));

        boolean invalidOrder = false;
        // 2️⃣ Sinh nhiều order
        for (int u = 0; u < order_length; u++) {
            // chọn customer random
            Customer currentCustomer = customerList.get(rand.nextInt(customerList.size()));
            CartService cartService = new CartService();

            // chọn random sách
            Set<Book> picked = new HashSet<>();
            int nums_picked = rand.nextInt(3) + 1; // 1–3 sách
            while (picked.size() < nums_picked) {
                Book randomBook = BookData.BOOKS.get(rand.nextInt(BookData.BOOKS.size()));
                picked.add(randomBook);
            }

            System.out.println("\n=== CUSTOMER " + currentCustomer.getName() + " TẠO ORDER #" + (u + 1) + " ===");

            System.out.println("\n=== NHỮNG QUYỂN SÁCH ĐÃ CHỌN ===");
            picked.forEach(b -> System.out.println(b.getTitle()));

            System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG ===");
            for (Book b : picked) {
                int qty = rand.nextInt(2) + 1;
                if (qty > b.getQuantity() || qty <= 0) {
                    System.out.println(
                            "❌ Lỗi: " + b.getTitle() + " chỉ còn " + b.getQuantity() + " nhưng yêu cầu " + qty);
                    invalidOrder = true;
                    break; // thoát vòng for (sách)
                }
                cartService.addToCart(b, qty);
                System.out.println(b.getTitle() + " x " + qty);
            }
            if (invalidOrder) {
                System.out.println("⚠️ Order " + (i + 1) + " bị hủy do vượt quá số lượng tồn kho!");
                continue; // tiep tuc vong lặp order
            }

            // tồn kho trước checkout
            System.out.println("\n=== TỒN KHO TRƯỚC CHECKOUT ===");
            picked.forEach(b -> System.out.println(b.getTitle() + " còn: " + b.getQuantity()));

            // giỏ hàng hiện tại
            System.out.println("\n=== GIỎ HÀNG HIỆN TẠI ===");
            cartService.getCartItems()
                    .forEach(item -> System.out.println(item.getBook().getTitle() + " x " + item.getQuantity()));
            System.out.println("Tổng tiền: " + cartService.getTotalAmount());

            // checkout
            CheckoutService checkoutService = new CheckoutService(bookService, cartService, orderService);
            System.out.println("\n=== THỰC HIỆN CHECKOUT ===");
            Order order = checkoutService.checkout(currentCustomer);

            System.out.println("✅ Checkout thành công, OrderId: " + order.getOrderId());
            System.out.println("\n");
            order.printOrderDetails();

            // giỏ hàng sau checkout
            System.out.println("\n=== GIỎ HÀNG SAU CHECKOUT ===");
            System.out.println("Số lượng sản phẩm: " + cartService.getCartItems().size());

            // tồn kho sau checkout
            System.out.println("\n=== TỒN KHO SAU CHECKOUT ===");
            picked.forEach(b -> System.out.println(b.getTitle() + " còn: " + b.getQuantity()));
        }

        // // 3️⃣ In tất cả order theo từng user
        // System.out.println("\n=== DANH SÁCH ORDER THEO USER ===");
        // for (Customer customer : customerList) {
        // System.out.println("\n📌 Orders của " + customer.getName() + ":");
        // List<Order> orders =
        // orderService.getOrdersByCustomerId(customer.getUserId());
        // orders.forEach(Order::printOrderDetails);
        // }

        // 3️⃣ In tất cả order
        System.out.println("\n=== DANH SÁCH TOÀN BỘ ORDER ===\n");
        List<Order> allOrders = orderService.getAllOrders();
        allOrders.forEach(Order::printOrderDetails);

    }
}
