package services.Admin;

import java.util.ArrayList;
import java.util.List;

import data.OrderData;
import models.Order;
import structures.orderqueuepending.OrderQueuePending;
import structures.orderqueueprocessing.OrderQueueProcessing;
import structures.orderqueuedone.OrderQueueDone;

public class OrderManagementService {
    private final OrderQueuePending orderQueuePending;
    private final OrderQueueProcessing orderQueueProcessing;
    private final OrderQueueDone orderQueueDone;

    public OrderManagementService(OrderQueuePending orderQueuePending,
                                  OrderQueueProcessing orderQueueProcessing,
                                  OrderQueueDone orderQueueDone) {
        this.orderQueuePending = orderQueuePending;
        this.orderQueueProcessing = orderQueueProcessing;
        this.orderQueueDone = orderQueueDone;
    }

    // Lấy tất cả đơn hàng Pending
    public List<Order> getPendingOrders() {
        return new ArrayList<>(orderQueuePending.getOrdersPending());
    }

    // Lấy tất cả đơn hàng Processing
    public List<Order> getProcessingOrders() {
        return new ArrayList<>(orderQueueProcessing.getOrdersProcessing());
    }

    // Lấy tất cả đơn hàng Done
    public List<Order> getDoneOrders() {
        return new ArrayList<>(orderQueueDone.getOrdersDone());
    }

    // Lấy tất cả order trong hệ thống (Pending -> Processing -> Done)
    public List<Order> getAllOrders() {
        List<Order> all = new ArrayList<>();
        all.addAll(getPendingOrders());
        all.addAll(getProcessingOrders());
        all.addAll(getDoneOrders());
        return all;
    }

    // Cap nhat sang trang thai tiep theo
    public void ProceedStatus(int orderId, String newStatus) {
        Order targetOrder = null;

        // 1️⃣ Tìm order trong Pending
        for (Order o : orderQueuePending.getOrdersPending()) {
            if (o.getOrderId() == orderId) {
                targetOrder = o;
                orderQueuePending.getOrdersPending().remove(o); // lấy ra khỏi pending
                break;
            }
        }

        // 2️⃣ Nếu không tìm thấy trong Pending, tìm trong Processing
        if (targetOrder == null) {
            for (Order o : orderQueueProcessing.getOrdersProcessing()) {
                if (o.getOrderId() == orderId) {
                    targetOrder = o;
                    orderQueueProcessing.getOrdersProcessing().remove(o);
                    break;
                }
            }
        }

        // 3️⃣ Nếu không tìm thấy trong Pending + Processing, check Done
        if (targetOrder == null) {
            for (Order o : orderQueueDone.getOrdersDone()) {
                if (o.getOrderId() == orderId) {
                    System.out.println("❌ Đơn #" + orderId + " đã hoàn tất, không thể cập nhật trạng thái.");
                    return;
                }
            }
        }

        // 4️⃣ Cập nhật trạng thái và chuyển queue tương ứng
        if (targetOrder != null) {
            targetOrder.updateStatus(newStatus); // push vào stack

            switch (newStatus.toLowerCase()) {
                case "pending":
                    orderQueuePending.enqueue(targetOrder);
                    System.out.println("⏳ Đơn #" + orderId + " hiện ở trạng thái PENDING");
                    break;

                case "processing":
                    orderQueueProcessing.enqueue(targetOrder);
                    System.out.println("🔄 Đơn #" + orderId + " chuyển sang PROCESSING");
                    break;

                case "done":
                    orderQueueDone.enqueue(targetOrder);
                    System.out.println("✅ Đơn #" + orderId + " chuyển sang DONE");
                    break;

                default:
                    System.out.println("⚠️ Trạng thái không hợp lệ: " + newStatus);
                    // Nếu không hợp lệ, có thể trả lại order vào queue cũ nếu muốn
            }
        }
    }

    // Cập nhật trạng thái bất kỳ cho đơn hàng
    public void updateStatus(int orderId, String newStatus) {
        Order targetOrder = null;

        // Tìm order trong Pending
        for (Order o : orderQueuePending.getOrdersPending()) {
            if (o.getOrderId() == orderId) {
                targetOrder = o;
                orderQueuePending.getOrdersPending().remove(o);
                break;
            }
        }

        // Nếu không tìm thấy trong Pending, tìm trong Processing
        if (targetOrder == null) {
            for (Order o : orderQueueProcessing.getOrdersProcessing()) {
                if (o.getOrderId() == orderId) {
                    targetOrder = o;
                    orderQueueProcessing.getOrdersProcessing().remove(o);
                    break;
                }
            }
        }

        // Nếu không tìm thấy trong Pending + Processing, check Done
        if (targetOrder == null) {
            for (Order o : orderQueueDone.getOrdersDone()) {
                if (o.getOrderId() == orderId) {
                    targetOrder = o;
                    orderQueueDone.getOrdersDone().remove(o);
                    break;
                }
            }
        }

        // Nếu tìm thấy order, cập nhật trạng thái và chuyển queue
        if (targetOrder != null) {
            targetOrder.updateStatus(newStatus);

            switch (newStatus.toLowerCase()) {
                case "pending":
                    orderQueuePending.enqueue(targetOrder);
                    System.out.println("⏳ Đơn #" + orderId + " cập nhật sang PENDING");
                    break;
                case "processing":
                    orderQueueProcessing.enqueue(targetOrder);
                    System.out.println("🔄 Đơn #" + orderId + " cập nhật sang PROCESSING");
                    break;
                case "done":
                    orderQueueDone.enqueue(targetOrder);
                    System.out.println("✅ Đơn #" + orderId + " cập nhật sang DONE");
                    break;
                default:
                    System.out.println("⚠️ Trạng thái không hợp lệ: " + newStatus);
            }
        } else {
            System.out.println("❌ Không tìm thấy đơn hàng #" + orderId);
        }
    }

    // Quay lại trạng thái trước đó của đơn hàng
    public void UndoStatus(int orderId) {
        Order targetOrder = null;

        // Tìm order trong Pending
        for (Order o : orderQueuePending.getOrdersPending()) {
            if (o.getOrderId() == orderId) {
                if (o.getStatusHistory().size() < 2) {
                    System.out.println("❌ Không thể hoàn tác trạng thái cho đơn #" + orderId + " vì không có trạng thái trước đó.");
                    return;
                } else {
                    targetOrder = o;
                    orderQueuePending.getOrdersPending().remove(o);
                    break;
                }
            }
        }

        // Nếu không tìm thấy trong Pending, tìm trong Processing
        if (targetOrder == null) {
            for (Order o : orderQueueProcessing.getOrdersProcessing()) {
                if (o.getOrderId() == orderId) {
                    targetOrder = o;
                    orderQueueProcessing.getOrdersProcessing().remove(o);
                    break;
                }
            }
        }

        // Nếu không tìm thấy trong Pending + Processing, check Done
        if (targetOrder == null) {
            for (Order o : orderQueueDone.getOrdersDone()) {
                if (o.getOrderId() == orderId) {
                    targetOrder = o;
                    orderQueueDone.getOrdersDone().remove(o);
                    break;
                }
            }
        }

        // Nếu tìm thấy order và có trạng thái trước đó
        if (targetOrder != null) {
            String removedStatus = targetOrder.undoStatus();
            String prevStatus = targetOrder.getCurrentStatus();

            switch (prevStatus.toLowerCase()) {
                case "pending":
                    orderQueuePending.enqueue(targetOrder);
                    System.out.println("⏪ Đơn #" + orderId + " quay lại trạng thái PENDING");
                    break;
                case "processing":
                    orderQueueProcessing.enqueue(targetOrder);
                    System.out.println("⏪ Đơn #" + orderId + " quay lại trạng thái PROCESSING");
                    break;
                case "done":
                    orderQueueDone.enqueue(targetOrder);
                    System.out.println("⏪ Đơn #" + orderId + " quay lại trạng thái DONE");
                    break;
                default:
                    System.out.println("⚠️ Trạng thái trước đó không hợp lệ: " + prevStatus);
            }
        } else {
            System.out.println("❌ Không thể hoàn tác trạng thái cho đơn #" + orderId);
        }
    }

}
