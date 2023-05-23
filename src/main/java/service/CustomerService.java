package service;
import model.person.Customer;
import model.Order;
import model.product.Product;
import repository.CustomerRepository;
import repository.OrderRepository;
import service.PizzeriaService;

import java.util.*;

public class CustomerService {
    private static Scanner scanner = new Scanner(System.in);

    private static void userScreen() {
        System.out.println("\t\t\t==========================================================");
        System.out.println("\t\t\t1. Create new order");
        System.out.println("\t\t\t2. View order history");
        System.out.println("\t\t\t3. View menu");
        System.out.println("\t\t\t4. Edit your account");
        System.out.println("\t\t\t5. Delete your account");
        System.out.println("\t\t\t0. Log out");
        System.out.println("\t\t\t==========================================================");
        System.out.print("Select an option: ");
    }

    private static void editCustomerFields(int customerId, Map<String, String> fieldsToUpdate) {
        Optional<Customer> optionalCustomer = CustomerRepository.getCustomerById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Scanner editScanner = new Scanner(System.in);

            System.out.println("Fields available for update:");
            for (String fieldValue : fieldsToUpdate.values()) {
                System.out.println(fieldValue);
            }

            System.out.println("Enter field names to update (separated by commas):");
            String userInput = editScanner.nextLine();
            String[] selectedFields = userInput.split(",");
            Map<String, String> fieldsToUpdateInDB = new HashMap<>();
            for (String fieldName : selectedFields) {
                fieldName = fieldName.trim().toLowerCase();
                if (fieldsToUpdate.containsKey(fieldName)) {
                    System.out.print("Enter new value for " + fieldName + ": ");
                    String fieldValue = editScanner.nextLine().trim();
                    if (!fieldValue.isEmpty()) {
                        if (fieldName.equals("username") && !CustomerRepository.uniqueUsername(fieldValue)) {
                            System.out.println("Username is already taken. Please choose a different one.");
                        } else {
                            fieldsToUpdateInDB.put(fieldName, fieldValue);
                        }
                    }
                } else {
                    System.out.println("Invalid field name: " + fieldName);
                }
            }


            for (Map.Entry<String, String> entry : fieldsToUpdateInDB.entrySet()) {
                String fieldName = entry.getKey().toLowerCase();
                String fieldValue = entry.getValue();
                switch (fieldName) {
                    case "username":
                        customer.setUserName(fieldValue);
                        break;
                    case "password":
                        customer.setPassword(fieldValue);
                        break;
                    case "firstname":
                        customer.setFirstName(fieldValue);
                        break;
                    case "lastname":
                        customer.setLastName(fieldValue);
                        break;
                    case "phonenumber":
                        customer.setPhoneNumber(fieldValue);
                        break;
                    case "address":
                        customer.setAddress(fieldValue);
                        break;
                    default:
                        System.out.println("Invalid field name: " + fieldName);
                        break;
                }
            }

            CustomerRepository.editCustomer(customer);

        } else {
            System.out.println("Customer not found with ID: " + customerId);
        }
    }

    private static void newOrder(Customer customer) {
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
    private static boolean deleteCustomer(int customerId) {
        System.out.println("Are you sure you want to delete your account? (all your orders will be lost)");
        System.out.print("Type YES or NO: ");
        Scanner deleteScanner = new Scanner(System.in);

        String decision = deleteScanner.nextLine();

        if(decision.equals("YES")) {
            CustomerRepository.deleteCustomer(customerId);
            return true;
        }
        return false;
    }
    public static void serviceScreen(Customer customer) {
        while(true) {

            userScreen();
            try {
                String input = scanner.nextLine().trim();
                if(!input.isEmpty()) {
                    int option = Integer.parseInt(input);

                    switch (option) {
                        case 1: {
                            newOrder(customer);
                            break;
                        }
                        case 2: {
                            List<Order> orderList = OrderRepository.getOrdersByUserId(customer.getId()).orElse(null);

//                        List<Order> orderList = customer.getOrderHistory();
                            if(orderList.size() == 0)
                                System.out.println("You have no orders :(");
                            else {
                                for(Order order : orderList)
                                    System.out.println(order);
                                // scanner.nextLine();
                                System.out.println("Press enter to dismiss...");
                                scanner.nextLine();
                            }
                            break;
                        }
                        case 3: {
                            PizzeriaService.listMenu();
                            // scanner.nextLine();
                            System.out.println("Press enter to dismiss...");
                            scanner.nextLine();
                            break;
                        }
                        case 4: {
                            Map<String, String> fieldsToUpdate = new HashMap<>();
                            fieldsToUpdate.put("username", "Username");
                            fieldsToUpdate.put("password", "Password");
                            fieldsToUpdate.put("firstname", "FirstName");
                            fieldsToUpdate.put("lastname", "LastName");
                            fieldsToUpdate.put("address", "Address");
                            fieldsToUpdate.put("phonenumber", "PhoneNumber");

                            //     scanner.nextLine();
                            editCustomerFields(customer.getId(), fieldsToUpdate);

                            break;
                        }
                        case 5: {
                            if(deleteCustomer(customer.getId())) {
                                System.out.println("ACCOUNT DELETED SUCCESSFULLY!");
                                return;
                            }
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
            }
            catch ( NoSuchElementException e) {
                invalidInputText();
                scanner.nextLine(); // consume the invalid input to avoid an infinite loop
            }
        }
    }
    public static void invalidInputText() {
        System.out.print("\u001B[31m");
        System.out.println("Invalid input! Please enter a valid integer.");
        System.out.print("\u001B[0m");
    }
}
