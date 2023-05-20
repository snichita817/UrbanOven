package service;
import model.person.Customer;
import model.Order;
import model.product.Product;
import repository.OrderRepository;
import service.PizzeriaService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomerService {
    private static Scanner scanner = new Scanner(System.in);

    public static void userScreen() {
        System.out.println("\t\t\t==========================================================");
        System.out.println("\t\t\t1. Create new order");
        System.out.println("\t\t\t2. View order history");
        System.out.println("\t\t\t3. View menu");
        System.out.println("\t\t\t0. Log out");
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
        // and to the database
        if(newOrder.getProducts().size() != 0)
        {
            OrderRepository.addOrder(newOrder);
            PizzeriaService.pizzeria.addOrder(newOrder);
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
            try {
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
                            scanner.nextLine();
                            System.out.println("Press enter to dismiss...");
                            scanner.nextLine();
                        }
                        break;
                    }
                    case 3: {
                        PizzeriaService.listMenu();
                        scanner.nextLine();
                        System.out.println("Press enter to dismiss...");
                        scanner.nextLine();
                        break;
                    }
                    default: {
                        invalidInputText();
                        break;
                    }
                    case 0: {
                        return;
                    }
                }
            }
            catch (InputMismatchException e) {
                invalidInputText();
                scanner.next(); // consume the invalid input to avoid an infinite loop

            }
        }
    }
    public static void invalidInputText() {
        System.out.print("\u001B[31m");
        System.out.println("Invalid input! Please enter a valid integer.");
        System.out.print("\u001B[0m");
    }
}
