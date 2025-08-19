package services.Admin;

import data.UserData;
import models.Admin;
import models.Customer;
import models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import algorithms.GenericSearch;
import algorithms.GenericSearch.SearchType;
import algorithms.GenericSort;
import algorithms.GenericSort.SortType;

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
    public List<User> searchByName(String keyword, SearchType searchType, SortType sortType) {
        if (searchType == SearchType.LINEAR) {
            return GenericSearch.linearSearch(customers, keyword, User::getName);
        } else if (searchType == SearchType.BINARY_TREE) {
            List<User> sortedUsers = sortUsersByName(customers, true, sortType);
            return GenericSearch.binarySearchTree(
                    sortedUsers,
                    keyword,
                    User::getName,
                    String::compareToIgnoreCase
            );
        }
        return new ArrayList<>();
    }   

    // Sắp xếp theo tên
    public List<User> sortUsersByName(List<User> userList, boolean ascending, SortType sortType) {
        List<User> sortedList = new ArrayList<>(userList);
        Comparator<User> comp = Comparator.comparing(User::getName, String.CASE_INSENSITIVE_ORDER);

        if (!ascending) {
            comp = comp.reversed();
        }

        if (sortType == SortType.SELECTION) {
            GenericSort.selectionSort(sortedList, comp);
        } else if (sortType == SortType.INSERTION) {
            GenericSort.insertionSort(sortedList, comp);
        }

        return sortedList;
    }
}
