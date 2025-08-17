package services;

import data.UserData;
import models.Admin;
import models.Customer;

import java.util.List;

public class AuthService {
    private boolean adminMode = false; // ✅ mặc định User
    private Customer loggedInCustomer;
    private Admin loggedInAdmin;

    // Toggle role: khi bật thì dùng Admin, tắt thì dùng Customer
    public void toggleRole() {
        adminMode = !adminMode;
        System.out.println("🔀 Chuyển sang chế độ: " + (adminMode ? "Admin" : "User"));
    }

    // ===== Register
    public boolean register(String name, String email, String password) {
        if (adminMode) {
            // Admin register
            for (Admin a : UserData.ADMINS) {
                if (a.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("❌ Email đã tồn tại trong Admins.");
                    return false;
                }
            }
            Admin newAdmin = new Admin(name, email, "N/A", password, "Admin");
            UserData.ADMINS.add(newAdmin);
            System.out.println("✅ Đăng ký Admin thành công: " + name);
            return true;
        } else {
            // Customer register
            for (Customer c : UserData.CUSTOMERS) {
                if (c.getEmail().equalsIgnoreCase(email)) {
                    System.out.println("❌ Email đã tồn tại trong Customers.");
                    return false;
                }
            }
            Customer newCustomer = new Customer(name, email, "N/A", password, "Unknown");
            UserData.CUSTOMERS.add(newCustomer);
            System.out.println("✅ Đăng ký Customer thành công: " + name);
            return true;
        }
    }

    // ===== Login
    public boolean login(String email, String password) {
        if (adminMode) {
            for (Admin a : UserData.ADMINS) {
                if (a.getEmail().equalsIgnoreCase(email) && a.getPassword().equals(password)) {
                    loggedInAdmin = a;
                    System.out.println("🔑 Admin đăng nhập thành công: " + a.getName());
                    return true;
                }
            }
            System.out.println("❌ Sai email hoặc mật khẩu (Admin).");
            return false;
        } else {
            for (Customer c : UserData.CUSTOMERS) {
                if (c.getEmail().equalsIgnoreCase(email) && c.getPassword().equals(password)) {
                    loggedInCustomer = c;
                    System.out.println("🔑 Customer đăng nhập thành công: " + c.getName());
                    return true;
                }
            }
            System.out.println("❌ Sai email hoặc mật khẩu (Customer).");
            return false;
        }
    }

    // ===== Logout =====
    public void logout() {
        if (loggedInCustomer != null) {
            System.out.println("👋 User logged out: " + loggedInCustomer.getName());
            loggedInCustomer = null;
        } else if (loggedInAdmin != null) {
            System.out.println("👋 Admin logged out: " + loggedInAdmin.getName());
            loggedInAdmin = null;
        } else {
            System.out.println("⚠️ Không có ai đang đăng nhập.");
        }
    }

    // ===== Check Login =====
    public boolean isLoggedIn() {
        return loggedInCustomer != null || loggedInAdmin != null;
    }

    // Getter
    public boolean isAdminMode() {
        return adminMode;
    }

    public Customer getLoggedInCustomer() {
        return loggedInCustomer;
    }

    public Admin getLoggedInAdmin() {
        return loggedInAdmin;
    }
}
