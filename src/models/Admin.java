// Lớp con: Admin
package models;

public class Admin extends Customer {
    private String role; // Vai trò quản trị, ví dụ: "SuperAdmin", "Moderator"

    public Admin(String name, String email, String phoneNumber, String password, String address, String role) {
        super(name, email, phoneNumber, password, address);
        this.role = role;
    }

    // Getter / Setter riêng cho Admin
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Hành vi quản trị
    public void manageSystem() {
        System.out.println(name + " đang quản trị hệ thống với quyền " + role);
    }
}
