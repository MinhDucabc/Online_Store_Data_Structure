 // ✅ Xử lý đơn tiếp theo (FIFO)
    public void processNextOrder(String newStatus) {
        if (orderQueuePending.isEmpty()) {
            System.out.println("❌ Không còn đơn hàng trong hàng chờ!");
            return;
        }
        Order nextOrder = orderQueuePending.dequeue();
        nextOrder.updateStatus(newStatus);
        
        System.out.println("✅ Đơn hàng #" + nextOrder.getOrderId()
                + " đã được cập nhật trạng thái: " + newStatus);
    }

    // ✅ Cập nhật trạng thái bất kỳ (nếu biết id)
    public void updateOrderStatus(int orderId, String newStatus) {
        for (Order o : orderQueuePending.getOrdersPending()) {
            if (o.getOrderId() == orderId) {
                o.updateStatus(newStatus);
                System.out.println("✅ Đơn hàng #" + orderId
                        + " đã được cập nhật trạng thái: " + newStatus);
                return;
            }
        }
        System.out.println("❌ Không tìm thấy đơn hàng #" + orderId);
    }