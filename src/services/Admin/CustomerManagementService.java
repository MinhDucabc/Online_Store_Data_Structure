package services.Admin;

import models.Customer;
import java.util.*;

public class CustomerManagementService {
    private List<Customer> customers = new ArrayList<>();

    // Thêm khách hàng mới (thường không dùng nhiều ở Admin, 
    // nhưng vẫn để nếu cần tạo nhanh cho mục đích quản trị)
    public void addCustomer(Customer customer) {
        customers.add(customer);
        System.out.println("✅ Đã thêm khách hàng: " + customer.getName());
    }

    // Lấy toàn bộ danh sách khách hàng
    public List<Customer> getAllCustomers() {
        return customers;
    }

    // Tìm khách hàng theo ID
    public Customer getCustomerById(int id) {
        return customers.stream()
                .filter(c -> c.getCustomerId() == id)
                .findFirst()
                .orElse(null);
    }

    // Xóa khách hàng theo ID
    public boolean deleteCustomer(int id) {
        boolean removed = customers.removeIf(c -> c.getCustomerId() == id);
        if (removed) {
            System.out.println("🗑️ Đã xóa khách hàng ID: " + id);
        } else {
            System.out.println("⚠️ Không tìm thấy khách hàng ID: " + id);
        }
        return removed;
    }

    // Tìm kiếm khách hàng theo tên
    public List<Customer> searchByName(String keyword) {
        return customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Sắp xếp khách hàng theo tên (tăng dần)
    public void sortByNameAsc() {
        customers.sort(Comparator.comparing(Customer::getName));
    }

    // Sắp xếp khách hàng theo tên (giảm dần)
    public void sortByNameDesc() {
        customers.sort(Comparator.comparing(Customer::getName).reversed());
    }
}
