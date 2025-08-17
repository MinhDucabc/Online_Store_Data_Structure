package test;

import services.AuthService;
import models.Customer;
import models.Admin;

public class AuthServiceTest {
    public static void main(String[] args) {
        AuthService auth = new AuthService();

        System.out.println("===== TEST CUSTOMER MODE =====");
        // Register new customer
        auth.register("John Doe", "john@example.com", "johnpass");

        // Login đúng
        boolean loginCustomerSuccess = auth.login("john@example.com", "johnpass");
        System.out.println("Login Customer Success? " + loginCustomerSuccess);

        // Login sai
        boolean loginCustomerFail = auth.login("john@example.com", "wrongpass");
        System.out.println("Login Customer Fail? " + loginCustomerFail);

        // Lấy thông tin customer đã login
        Customer loggedInCustomer = auth.getLoggedInCustomer();
        if (loggedInCustomer != null) {
            System.out.println("Logged in Customer: " + loggedInCustomer.getName() + " | Email: " + loggedInCustomer.getEmail());
        }

        // Logout customer
        System.out.println("Logging out customer...");
        auth.logout(); // logout user


        System.out.println("\n===== SWITCH TO ADMIN MODE =====");
        // Toggle sang Admin
        auth.toggleRole();

        // Register new admin
        auth.register("Super Admin", "superadmin@example.com", "superpass");

        // Login đúng admin
        boolean loginAdminSuccess = auth.login("superadmin@example.com", "superpass");
        System.out.println("Login Admin Success? " + loginAdminSuccess);

        // Login sai admin
        boolean loginAdminFail = auth.login("superadmin@example.com", "wrongpass");
        System.out.println("Login Admin Fail? " + loginAdminFail);

        // Lấy thông tin admin đã login
        Admin loggedInAdmin = auth.getLoggedInAdmin();
        if (loggedInAdmin != null) {
            System.out.println("Logged in Admin: " + loggedInAdmin.getName() + " | Email: " + loggedInAdmin.getEmail());
        }
        // Logout admin
        System.out.println("Logging out admin...");
        auth.logout(); // logout admin

        System.out.println("\n===== SWITCH BACK TO CUSTOMER MODE =====");
        auth.toggleRole(); // quay lại Customer
        boolean loginBack = auth.login("john@example.com", "johnpass");
        System.out.println("Login back as Customer? " + loginBack);
    }
}
