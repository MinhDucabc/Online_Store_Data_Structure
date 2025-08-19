package test;

import services.Auth.AuthService;
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
    public static int nums_of_random_customers = 3; // số lượng khách hàng
    public static int[] Books_per_order_range = {1, 5};
    public static int[] book_quantity_range = {1, 3}; // số lượng sách trong mỗi đơn hàng

    // public static void main(String[] args) {
    public static void generateMultiOrdersfromMultiUsers(OrderService orderService, AuthService authService) {
        BookService bookService = new BookService();
        // OrderService orderService = new OrderService();

        // 1️⃣ Lấy ngẫu nhiên nums_of_customers khác nhau
        Random rand = new Random();

        List<Customer> customerList = UserData.CUSTOMERS;
        if (customerList.isEmpty()) {
            System.out.println("❌ Không có khách hàng nào để thực hiện đơn hàng. Dừng chương trình.");
            return;
        }
        if (nums_of_random_customers > customerList.size()) {
            System.out.println("❌ Không đủ khách hàng để chọn " + nums_of_random_customers + " khách hàng. Dừng chương trình.");
            return;
        }
        Set<Customer> customersSet = new HashSet<>();
        while (customersSet.size() < nums_of_random_customers) {
            int randomIndex = rand.nextInt(customerList.size());
            customersSet.add(customerList.get(randomIndex));
        }
        List<Customer> randomCustomerList = new ArrayList<>(customersSet);

        // 2️⃣ Sinh mot luong order nhat dinh giua nhieu random user
        boolean invalidOrder = false;
        for (int u = 0; u < order_length; u++) {
            // 1. Dang nhap customer ngẫu nhiên su dung AuthService
            Customer randomCustomer = randomCustomerList.get(rand.nextInt(randomCustomerList.size()));
            
            boolean loginSuccess = authService.login(randomCustomer.getEmail(), randomCustomer.getPassword());
            if (!loginSuccess) {
                System.out.println("❌ Không thể đăng nhập cho " + randomCustomer.getName() + ". Dừng chương trình.");
                return;
            }
            Customer currentCustomer = authService.getLoggedInCustomer();


            // 2. chọn random sách voi so luong ngẫu nhiên
            List<Book> bookList = new ArrayList<>(BookData.BOOKS);
            if (bookList.isEmpty()) {
                System.out.println("❌ Không có sách nào để chọn. Dừng chương trình.");
                return;
            }
            if (bookList.size() < Books_per_order_range[1]) {
                System.out.println("❌ Không đủ sách để tạo đơn hàng. Dừng chương trình.");
                return;
            }
            Set<Book> picked = new HashSet<>();
            int nums_picked = rand.nextInt(Books_per_order_range[1] - Books_per_order_range[0] + 1) + Books_per_order_range[0]; // 1–3 sách
            while (picked.size() < nums_picked) {
                Book randomBook = bookList.get(rand.nextInt(bookList.size()));
                picked.add(randomBook);
            }

            System.out.println("\n=== CUSTOMER " + currentCustomer.getName() + " TẠO ORDER #" + (u + 1) + " ===");

            // Kiểm tra số lượng sách đã chọn
            System.out.println("\n=== NHỮNG QUYỂN SÁCH ĐÃ CHỌN ===");
            picked.forEach(b -> System.out.println(b.getTitle()));

            // 3. Thêm sách vào giỏ hàng
            CartService cartService = new CartService();
            System.out.println("\n=== THÊM SÁCH VÀO GIỎ HÀNG ===");
            for (Book b : picked) {
                // So luong sach ngẫu nhiên từ 1 den 3
                int qty = rand.nextInt(book_quantity_range[1] - book_quantity_range[0] + 1) + 1;
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
                System.out.println("❌ Không thể tạo order do lỗi số lượng sách. Bỏ qua order này.");
                continue; // tiep tuc vong lặp order
            }

            // 4. In tồn kho trước khi checkout
            // giỏ hàng hiện tại
            System.out.println("\n=== GIỎ HÀNG HIỆN TẠI ===");
            cartService.getCartItems()
                    .forEach(item -> System.out.println(item.getBook().getTitle() + " x " + item.getQuantity()));
            System.out.println("Tổng tiền: " + cartService.getTotalAmount());

            System.out.println("\n=== TỒN KHO TRƯỚC CHECKOUT ===");
            picked.forEach(b -> System.out.println(b.getTitle() + " còn: " + b.getQuantity()));

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

            // // 7️⃣ Kiểm tra toàn bộ đơn hàng của user trong các queue
            // System.out.println("\n=== KIỂM TRA ORDER THEO TRẠNG THÁI ===");
            // System.out.println("📌 Pending Orders:");
            // orderService.getAllPendingOrdersByCustomerId(customer.getUserId())
            //         .forEach(Order::printOrderDetails);

            // System.out.println("📌 Processing Orders:");
            // orderService.getAllProcessingOrdersByCustomerId(customer.getUserId())
            //         .forEach(Order::printOrderDetails);

            // System.out.println("📌 Done Orders:");
            // orderService.getAllDoneOrdersByCustomerId(customer.getUserId())
            //         .forEach(Order::printOrderDetails);

            // 8️⃣ Logout
            System.out.println("\n=== LOGOUT ===");
            authService.logout(cartService);
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
