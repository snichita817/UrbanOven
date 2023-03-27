package service;
import model.Customer;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    public static Scanner scanner = new Scanner(System.in);

    public static void userScreen() {
        System.out.println("\t\t\t1. Create new order");
        System.out.println("\t\t\t2. View order history");
        System.out.println("\t\t\t0. Exit");
        System.out.print("Select an option: ");
    }

    public static void newOrder(Customer customer) {
        List<Product> productList = new ArrayList<>();
        OrderService.orderScreen(productList);
        System.out.println(productList);

        // creating new order to be added to the customer
        Order newOrder = new Order.Builder()
                .buildCustomer(customer)
                .buildProducts(productList)
                .build();

        // adding the order to the customer orders list
        customer.addOrder(newOrder);
        System.out.println("Your order was added successfully!");
    }
    public static void serviceScreen(Customer customer) {
        while(true) {
            userScreen();
            int option = scanner.nextInt();
            switch (option) {
                case 1: {
                    newOrder(customer);
                    break;
                }
                case 2: {
                    List<Order> orderList = customer.getOrderHistory();
                    if(orderList.size() == 0)
                        System.out.println("You have no orders :(");
                    else {
                        for(Order order : orderList)
                            System.out.println(order);
                    }
                    break;
                }
                case 0: {
                    return;
                }
            }
        }
    }

}
