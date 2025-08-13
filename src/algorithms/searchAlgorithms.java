package algorithms;
import java.util.ArrayList;
import java.util.List;

public class searchAlgorithms {
    public static List<Order> linearSearch(List<Order> orders, String keyword) {
        List<Order> result = new ArrayList<>();
        keyword = keyword.toLowerCase();

        for (Order order : orders) {
            if (String.valueOf(order.getOrderId()).equals(keyword)) {
                result.add(order);
            }
            if (order.getCustomer().getName().toLowerCase().contains(keyword)) {
                result.add(order);
            }
            if (order.getStatus().toLowerCase().contains(keyword)) {
                result.add(order);
            }
            for (Book book : order.getProducts()) {
                if (book.getTitle().toLowerCase().contains(keyword)) {
                    result.add(order);
                }
            }
        }
        return result;
    }

    // Binary search chi ap dung khi danh sach da duoc sap xep ma moi thuoc tinh lai co mot cach sap xep khac nhau
    // Nen ko the bo searchType nhu linearSearch duoc

    // Truoc khi tim kiem can sort theo tieu chi tuong ung
    // Trước khi tìm kiếm, phải sort theo tiêu chí tương ứng
        // orders.sort(Comparator.comparingInt(Order::getOrderId));
        // Order foundById = binarySearch(orders, "id", "3");

        // orders.sort(Comparator.comparing(o -> o.getCustomer().getName().toLowerCase()));
        // Order foundByCustomer = binarySearch(orders, "customer", "James Gosling");

    public static Order binarySearch(List<Order> orders, String searchType, String keyword) {
        int low = 0, high = orders.size() - 1;
        keyword = keyword.toLowerCase();

        while (low <= high) {
            int mid = (low + high) / 2;
            Order midOrder = orders.get(mid);
            int compareResult = 0;

            switch (searchType.toLowerCase()) {
                case "id":
                    compareResult = Integer.compare(midOrder.getOrderId(), Integer.parseInt(keyword));
                    break;
                case "customer":
                    compareResult = midOrder.getCustomer().getName()
                                    .compareToIgnoreCase(keyword);
                    break;
                case "status":
                    compareResult = midOrder.getStatus()
                                    .compareToIgnoreCase(keyword);
                    break;
                case "product":
                    // So sánh theo tên sản phẩm đầu tiên (nếu có)
                    if (!midOrder.getProducts().isEmpty()) {
                        compareResult = midOrder.getProducts().get(0)
                                        .getTitle()
                                        .compareToIgnoreCase(keyword);
                    } else {
                        compareResult = -1;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search type: " + searchType);
            }

            if (compareResult == 0) {
                return midOrder;
            } else if (compareResult < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }


}
