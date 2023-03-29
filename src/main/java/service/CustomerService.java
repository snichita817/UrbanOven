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
        System.out.println("\t\t\t==========================================================");
        System.out.println("\t\t\t1. Create new order");
        System.out.println("\t\t\t2. View order history");
        System.out.println("\t\t\t3. View menu");
        System.out.println("\t\t\t0. Exit");
        System.out.println("\t\t\t==========================================================");
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
        if(newOrder.getProducts().size() != 0)
        {
            customer.addOrder(newOrder);
            System.out.println("Your order was added successfully!");
        }
        else {
            System.out.println("Can't add an order with zero products!");
        }
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
                case 3: {

                }
                case 0: {
                    return;
                }
            }
        }
    }

}
