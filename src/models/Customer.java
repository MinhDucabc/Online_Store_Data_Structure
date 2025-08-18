// Lớp con: Customer
package models;

public class Customer extends User {
    private String address; // Địa chỉ

    public Customer(String name, String email, String phoneNumber, String password, String address) {
        super(name, email, phoneNumber, password); // Gọi constructor lớp cha
        this.address = address;
    }

    // Getter / Setter riêng cho Customer
    public String getAddress() {
        return address != null ? address : "N/A";
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
