package data;

import models.Admin;
import models.Customer;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static final List<Customer> CUSTOMERS = new ArrayList<>();
    public static final List<Admin> ADMINS = new ArrayList<>();
    
    static {
        // Customers
        CUSTOMERS.add(new Customer("Alice Johnson", "alice@example.com", "123-456-7890", "alicepass", "123 Main St, New York"));
        CUSTOMERS.add(new Customer("Bob Smith", "bob@example.com", "555-234-5678", "bobpass", "456 Oak Ave, Los Angeles"));
        CUSTOMERS.add(new Customer("Charlie Brown", "charlie@example.com", "222-789-4561", "charliepass", "789 Pine Rd, Chicago"));
        CUSTOMERS.add(new Customer("Diana Prince", "diana@example.com", "333-987-6543", "dianapass", "321 Maple St, San Francisco"));
        CUSTOMERS.add(new Customer("Ethan Hunt", "ethan@example.com", "444-321-7654", "ethanpass", "654 Elm Blvd, Miami"));

        // Admins
        ADMINS.add(new Admin("David Lee", "david@example.com", "111-222-3333", "davidpass", "Admin"));
        ADMINS.add(new Admin("Emma Wilson", "emma@example.com", "444-555-6666", "emmapass", "Admin"));
    }
}
