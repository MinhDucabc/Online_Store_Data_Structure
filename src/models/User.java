// Lớp cha: User
package models;

public class User {
    protected static int count = 1;
    protected int userId;         // Mã người dùng
    protected String name;        // Tên
    protected String email;       // Email
    protected String phoneNumber; // Số điện thoại
    protected String password;    // Mật khẩu

    public User(String name, String email, String phoneNumber, String password) {
        this.userId = count++;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    // Getter / Setter
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { 
        return phoneNumber != null ? phoneNumber : "N/A"; 
    }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
