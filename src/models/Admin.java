// Lớp con: Admin
package models;

public class Admin extends User {
    private String role; // Vai trò quản trị, ví dụ: "SuperAdmin", "Moderator"

    public Admin(int userId, String name, String email, String phoneNumber, String role) {
        super(userId, name, email, phoneNumber);
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
