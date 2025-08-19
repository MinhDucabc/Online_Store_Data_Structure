package test.Admin;

import java.util.List;

import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort.SortType;
import models.Admin;
import models.Customer;
import models.User;
import services.Admin.CustomerManagementService;

public class CustomerManagementServiceTest {
    public static void main(String[] args) {
        CustomerManagementService service = new CustomerManagementService();

        // 1. Them moi
        service.addUser(new Customer("Chris Evans", "chris@example.com", "999-888-7777", "chrisPassword", "Boston, USA"));
        service.addUser(new Admin("Tony Stark", "tony@avengers.com", "111-222-3333", "tonyPassword", "SuperAdmin"));

        // 2. Lay toan bo Customer

        System.out.println("\n--- Danh sach (Customer + Admin) ban dau ---");
        service.getAllUsers().forEach(u -> 
            System.out.println(u.getUserId() + " - " + u.getName() + " (" + u.getClass().getSimpleName() + ")")
        );

        // 3. Tim theo ID
        System.out.println("\n--- Tim nguoi dung ID = 2 ---");
        User found = service.getUserById(2);
        System.out.println(found != null ? found.getName() : "Khong tim thay.");

        // 4. Tìm kiếm theo tên
        System.out.println("\n--- Tìm kiếm từ khóa 'John' ---");
        service.searchByName("John", SearchType.LINEAR, SortType.INSERTION)
            .forEach(u -> 
                System.out.println(u.getUserId() + " - " + u.getName())
            );

        // 5. Sắp xếp theo tên tăng dần
        System.out.println("\n--- Sắp xếp theo tên tăng dần ---");
        List<User> sortedUsersAsc = service.sortUsersByName(service.getAllUsers(), true, SortType.INSERTION);
        sortedUsersAsc.forEach(u -> 
            System.out.println(u.getUserId() + " - " + u.getName())
        );


        // 6. Xoa nguoi dung
        System.out.println("\n--- Xoa nguoi dung ID = 1 ---");
        service.deleteUser(1);
        service.getAllUsers().forEach(u -> 
            System.out.println(u.getUserId() + " - " + u.getName())
        );
    }
}
