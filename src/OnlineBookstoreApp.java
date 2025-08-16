
// import models.*;
// import structures.*;
// import algorithms.*;
// import java.util.*;

// public class OnlineBookstoreApp {
//     public static void main(String[] args) {
//         OrderQueuePending orderQueuePending = new OrderQueuePending();
//         OrderStack orderHistory = new OrderStack();
//         List<Order> allOrders = new ArrayList<>();

//         // Create Orders
//         Order o1 = new Order("Alice", "123 Street", Arrays.asList(
//                 new Book("Java Basics", "John Doe", 1),
//                 new Book("Algorithms", "Jane Smith", 2)
//         ));

//         Order o2 = new Order("Bob", "456 Avenue", Arrays.asList(
//                 new Book("Data Structures", "Mary Lee", 1),
//                 new Book("Design Patterns", "Alan Turing", 3)
//         ));

//         // Enqueue Orders
//         orderQueuePending.enqueue(o1);
//         orderQueuePending.enqueue(o2);

//         allOrders.add(o1);
//         allOrders.add(o2);

//         // Process Orders
//         while (!orderQueuePending.isEmpty()) {
//             Order current = orderQueuePending.dequeue();
//             System.out.println("\nProcessing: " + current);

//             // Sort books by title
//             SortingAlgorithms.insertionSortByTitle(current.books);

//             // Mark as Shipped
//             current.status = "Shipped";
//             orderHistory.push(current);
//         }

//         // Search for an Order
//         int searchId = 1;
//         Order found = SearchAlgorithms.linearSearch(allOrders, searchId);
//         System.out.println("\nSearch Result: " + (found != null ? found : "Order Not Found"));

//         // Display Order History
//         System.out.println("\nOrder History (Stack):");
//         orderHistory.displayStack();
//     }
// }
