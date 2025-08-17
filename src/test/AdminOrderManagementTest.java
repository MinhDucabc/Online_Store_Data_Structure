import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import models.Order;
import services.AuthService;
import services.BookService;
import services.Admin.OrderManagementService;
import services.User.OrderService;
import test.MultiUserMainFlowMultipleOrder;

import structures.orderqueuepending.OrderQueuePending;
import structures.orderqueueprocessing.OrderQueueProcessing;
import structures.orderqueuedone.OrderQueueDone;

public class AdminOrderManagementTest {
    public static void main(String[] args) {
        // Gọi utility để tạo sẵn Orders
        OrderService orderService = new OrderService();
        AuthService authService = new AuthService();
        MultiUserMainFlowMultipleOrder.generateMultiOrdersfromMultiUsers(orderService, authService);

        // === Admin login ===
        authService.toggleRole(); // Chuyển sang chế độ Admin
        System.out.println("🔀 Đã chuyển sang chế độ Admin");
        boolean isLoggedIn = authService.login("david@example.com", "davidpass");
        if (!isLoggedIn) {
            System.out.println("❌ Không thể đăng nhập admin. Thoát chương trình.");
            return;
        }

        List<Order> allOrders = new ArrayList<>(orderService.getAllOrders());

        System.out.println("=== TOÀN BỘ ORDER CÓ SẴN ===");
        allOrders.forEach(Order::printOrderDetails);

        System.out.println("\n=== ADMIN: QUẢN LÝ ORDER ===");
        OrderManagementService adminService = new OrderManagementService(
                new OrderQueuePending(),
                new OrderQueueProcessing(),
                new OrderQueueDone());

        System.out.println("\n📋 Toàn bộ order ban đầu (pending):");
        List<Order> pendingOrders = adminService.getPendingOrders();

        if (pendingOrders.isEmpty()) {
            System.out.println("⏳ Không có order nào trong PENDING.");
        } else {
            for (Order o : pendingOrders) {
                o.printOrderDetails();
            }
        }

        Random random = new Random();
        // 3️⃣ Cập nhật ngẫu nhiên một vài order từ Pending
        if (!pendingOrders.isEmpty()) {
            int updatesFromPending = Math.min(pendingOrders.size(), random.nextInt(4) + 2);

            // shuffle và lấy một số lượng ngẫu nhiên
            List<Order> shuffled = new ArrayList<>(pendingOrders);
            Collections.shuffle(shuffled);

            for (int i = 0; i < updatesFromPending; i++) {
                Order o = shuffled.get(i);
                adminService.ProceedStatus(o.getOrderId(), "processing");
                System.out.println("✅ Order #" + o.getOrderId() + " đã được cập nhật sang processing");
                pendingOrders.remove(o); // remove khỏi pending
            }
        }

        // 4️⃣ Cập nhật ngẫu nhiên một vài order từ Processing
        List<Order> processingOrders = adminService.getProcessingOrders();

        if (processingOrders.isEmpty()) {
            System.out.println("⏳ Không có order nào trong PROCESSING.");
        } else {
            System.out.println("\n⚙️ Cập nhật một vài order từ PROCESSING:");

            int updatesFromProcessing = Math.min(processingOrders.size(), random.nextInt(4) + 2);

            // shuffle danh sách để chọn ngẫu nhiên
            List<Order> shuffled = new ArrayList<>(processingOrders);
            Collections.shuffle(shuffled);

            for (int i = 0; i < updatesFromProcessing; i++) {
                Order o = shuffled.get(i);
                String newStatus = "done";
                adminService.ProceedStatus(o.getOrderId(), newStatus);
                System.out.println("✅ Order #" + o.getOrderId() + " đã được cập nhật sang " + newStatus);
                processingOrders.remove(o); // xóa khỏi queue Processing
            }
        }

        System.out.flush();
        // 5️⃣ In danh sách order còn lại trong từng queue
        System.out.println("\n📋 Danh sách order hiện tại trong PENDING:");
        adminService.getPendingOrders().forEach(Order::printOrderDetails);

        System.out.println("\n📋 Danh sách order hiện tại trong PROCESSING:");
        adminService.getProcessingOrders().forEach(Order::printOrderDetails);

        System.out.println("\n📋 Danh sách order hiện tại trong DONE:");
        adminService.getDoneOrders().forEach(Order::printOrderDetails);

        // === Admin logout ===
        authService.logout(null); // Không cần CartService khi logout admin
    }
}
