package services.Admin;

import data.UserData;
import models.Admin;
import models.Customer;
import models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerManagementService {
    private List<User> customers; // Lưu cả Customer và Admin

    public CustomerManagementService() {
        customers = new ArrayList<>();

        if (UserData.CUSTOMERS != null) {
            customers.addAll(UserData.CUSTOMERS);
        }
        if (UserData.ADMINS != null) {
            customers.addAll(UserData.ADMINS);
        }

        if (customers.isEmpty()) {
            System.out.println("⚠️ Khong co du lieu nguoi dung trong UserData.");
        } else {
            System.out.println("✅ Da tai du lieu (Customer + Admin) tu UserData.");
        }
    }

    // Thêm người dùng mới (Customer hoặc Admin)
    public void addUser(User user) {
        if (user == null) {
            System.out.println("❌ Khong the them nguoi dung null.");
            return;
        }
        boolean exists = customers.stream()
                .anyMatch(c -> c.getUserId() == user.getUserId());
        if (exists) {
            System.out.println("❌ Nguoi dung voi ID " + user.getUserId() + " da ton tai.");
            return;
        }
        customers.add(user);
        System.out.println("✅ Da them nguoi dung: " + user.getName());
    }

    // Lấy toàn bộ danh sách (Customer + Admin)
    public List<User> getAllUsers() {
        return customers;
    }

    // Tìm theo ID
    public User getUserById(int id) {
        return customers.stream()
                .filter(c -> c.getUserId() == id)
                .findFirst()
                .orElse(null);
    }

    // Xóa người dùng
    public boolean deleteUser(int id) {
        boolean removed = customers.removeIf(c -> c.getUserId() == id);
        if (removed) {
            System.out.println("🗑️ Da xoa nguoi dung ID: " + id);
        } else {
            System.out.println("⚠️ Khong tim thay nguoi dung ID: " + id);
        }
        return removed;
    }

    // Tìm kiếm theo tên
    public List<User> searchByName(String keyword) {
        return customers.stream()
                .filter(c -> c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Sắp xếp theo tên tăng dần
    public void sortByNameAsc() {
        customers.sort(Comparator.comparing(User::getName));
    }

    // Sắp xếp theo tên giảm dần
    public void sortByNameDesc() {
        customers.sort(Comparator.comparing(User::getName).reversed());
    }
}
