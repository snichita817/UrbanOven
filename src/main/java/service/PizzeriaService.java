package service;

import model.*;
import model.person.Customer;
import model.person.Employee;
import model.person.Person;
import model.product.Product;
import repository.CustomerRepository;

import java.util.*;

public class PizzeriaService {
    public static Pizzeria pizzeria = new Pizzeria();
    public static Scanner scanner = new Scanner(System.in);
    public static void greetScreen() {
        System.out.println("\t\t\t=================  WELCOME TO URBANOVEN  =================");
        System.out.println("\t\t\t1. Log in");
        System.out.println("\t\t\t2. Register an account");
        System.out.println("\t\t\t0. Exit");
        System.out.println("\t\t\t==========================================================");
        System.out.print("Select an option: ");
    }

    public static void listMenu() {
        System.out.println("\t\t==============  URBANOVEN MENU  ==============");
        List<Product> products = pizzeria.getProducts();
        ProductService.listProducts(products);
    }

    public static Person login() {
        System.out.println("\n\nEnter your login details");
        System.out.print("Username: ");
        String username = scanner.next();
        scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Optional<Customer> loggedIn =
                CustomerRepository.getCustomerByNameAndPassword(username, password);
//        if(loggedIn.isEmpty()) {
//            // try to retrieve the value from Employees table
//            loggedIn = EmployeeRepository.getCustomerByNameAndPassword(username, password);
//        }
        // If present returns the value, if not null
        return loggedIn.orElse(null);

    }
    public static void openShop() {
        ToppingService.addToppingsToDB();
        while(true)
        {
            greetScreen();
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        // The person who is logged in right now
                        Person currentLogin = login();
                        if(currentLogin == null)
                        {
                            System.out.println("Username or password is incorrect!");
                            break;
                          // PART where USER IS LOGGED IN
                        } else if (currentLogin instanceof Customer) {
                            CustomerService.serviceScreen((Customer)currentLogin);
                        }
                        else if (currentLogin instanceof Employee) {
                            EmployeeService.employeeScreen();
                        }
                        break;
                    case 2:
                        // Adding new user to the database
                        PersonService.newPerson();
                        System.out.println("User added successfully!");
                        break;
                    case 0:
                        System.out.println("Thank you for visiting our shop!");
                        return;
                    default:
                        System.out.print("\u001B[31m");
                        System.out.println("Invalid value! Please try again!");
                        System.out.print("\u001B[0m");
                }
            }
            catch (InputMismatchException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("\u001B[0m");
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }
    }

}
