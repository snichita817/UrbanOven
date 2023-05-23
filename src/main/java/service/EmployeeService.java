package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import model.Order;
import model.Pizzeria;
import model.person.Customer;
import model.person.Employee;
import repository.CustomerRepository;
import repository.EmployeeRepository;
import repository.OrderRepository;

public class EmployeeService {
    private static Scanner scanner = new Scanner(System.in);

    private static void invalidInputText() {
        System.out.print("\u001B[31m");
        System.out.println("Invalid input! Please enter a valid integer.");
        System.out.print("\u001B[0m");
    }

    private static void listMenu(boolean isKing) {
        System.out.println("\n\n\t\t\t==========================================================");
        System.out.println("\t\t\t1. List all orders");
        System.out.println("\t\t\t2. Find order");              // Find order by order ID, Find order by customer username
        System.out.println("\t\t\t3. Filter orders");           // Filter order by date
        System.out.println("\t\t\t4. Update order status");     // Update order status

        // List the special menu items only if the employee has the ranking of "king"
        if(isKing) {
            System.out.println("\t\t\t5. New employee");
            System.out.println("\t\t\t6. Update employee salary");
            System.out.println("\t\t\t7. Update employee ranking");
            System.out.println("\t\t\t8. Delete employee");

        }
        System.out.println("\t\t\t0. Log out");
        System.out.println("\t\t\t==========================================================");
        System.out.print("Option: ");
    }

    private static void listOrders(List<Order> allOrders, boolean forStatus) {
        System.out.println("\n\n\t\t\t==============  ORDERS  ==============");
        if(allOrders.size() == 0) {
            System.out.println("No orders found!");
        }
        else{
            for(Order order: allOrders) {
                System.out.printf("Order no. %d   ->   Client: %s %s\n",
                        order.getOrderId(),
                        order.getCustomer().getFirstName(),
                        order.getCustomer().getLastName());
            }

            // if its just to list orders and get information for them (case 1)
            if(!forStatus) {
                System.out.println("\nType order ID for more information.");
                System.out.println("Type 0 to go back.");
                System.out.print("> ");
                try {
                    int orderId = scanner.nextInt();

                    if (orderId == 0) {
                        return;
                    } else {
                        try {
                            System.out.println(returnOrder(orderId, allOrders));
                        } catch (IndexOutOfBoundsException e) {
                            System.out.println("Invalid order ID. Please try again.");
                        }
                    }

                }
                catch (InputMismatchException e) {
                    invalidInputText();
                    scanner.next();
                }
            }
            else {
                System.out.println("\nType order ID to modify its status.");
                System.out.println("Type 0 to go back.");
                System.out.print("> ");
                try {
                    int orderId = scanner.nextInt();

                    if (orderId == 0) {
                        return;
                    } else if (orderId > 0 && orderId <= allOrders.size()) {
                        updateOrderStatus(allOrders.get(orderId - 1));
                    } else {
                        System.out.println("Invalid order ID. Please try again.");
                    }

                } catch (InputMismatchException e) {
                    invalidInputText();
                    scanner.next();
                }
            }
        }
    }

    private static void updateOrderStatus(Order order) {
        System.out.println("Selected order: " + order);

        System.out.print("Enter the new order status: ");
        String newStatus = scanner.next();

        boolean success = OrderRepository.updateOrderStatus(order.getOrderId(), newStatus);
        if(success) {
            System.out.println("Order status updated successfully.");
        } else {
            System.out.println("Error updating order status");
        }
    }

    public static void findOrder() {
        int option;
        while(true) {
            System.out.println("1. Find order by ID;");
            System.out.println("2. Find order by username;");
            System.out.println("0. Back");
            System.out.print("Option: ");
            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        System.out.println(getOrderId());
                        break;
                    case 2:
                        listOrders(getOrderUser(), false);
                        break;
                    case 0:
                        return;
                    default:
                        invalidInputText();
                        break;
                }

            }
            catch (InputMismatchException e ){
                invalidInputText();
                scanner.next();
            }
        }
    }

    private static Order returnOrder(int id, List<Order> orderList) {
        for(Order order : orderList) {
            if(order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    private static Order getOrderId() {
        int orderId;
        try {
            System.out.print("Please type order ID: ");
            orderId = scanner.nextInt();
            List<Order> orderList = OrderRepository.getOrdersById(orderId).orElse(null);

            if(orderList != null) {
                for(Order order:orderList) {
                    if(order.getOrderId() == orderId)
                        return order;
                }
            }

        }
        catch (InputMismatchException e ){
            invalidInputText();
            scanner.next();
        }
        return null;
    }

    private static List<Order> getOrderUser() {

        // Getting user username
        String findUser;
        System.out.print("Type username: ");
        findUser = scanner.next();
        scanner.nextLine();

        int customerId = CustomerRepository.getCustomerIdByUsername(findUser).orElse(0);
        System.out.println(customerId);
        List<Order> orderToReturn = new ArrayList<>();
        if(customerId != 0) {
            orderToReturn = OrderRepository.getOrdersByUserId(customerId).orElse(null);
        }


        return orderToReturn;
    }

    private static List<Order> filterOrders() {
        scanner.nextLine();

        Calendar startCal = Calendar.getInstance();
        while (true) {
            try {
                System.out.print("Enter start date (dd/MM/yyyy) or press Enter for today's date: ");
                String startDateStr = scanner.nextLine();
                if (startDateStr.equals("")) {
                    startCal.setTime(new Date());
                    break;
                } else {
                    startCal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(startDateStr));
                    break;
                }
            } catch (ParseException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid date format! Please enter the date in dd/MM/yyyy format.");
                System.out.print("\u001B[0m");
            }
        }

        Calendar endCal = Calendar.getInstance();
        while (true) {
            try {
                System.out.print("Enter end date (dd/MM/yyyy) or press Enter for today's date: ");
                String endDateStr = scanner.nextLine();
                if (endDateStr.equals("")) {
                    // Set end date to the next day to include today's orders
                    endCal.setTime(new Date());
                    endCal.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                } else {
                    endCal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse(endDateStr));
                    break;
                }
            } catch (ParseException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid date format! Please enter the date in dd/MM/yyyy format.");
                System.out.print("\u001B[0m");
            }
        }

        if (startCal.after(endCal)) {
            System.out.println("Start date cannot be after end date!");
            return new ArrayList<>();
        }

        return OrderRepository.getOrdersBetweenDates(startCal, endCal).orElse(null);
    }

    private static void errorMessage(String msg) {
        System.out.print("\u001B[31m");
        System.out.println(msg);
        System.out.print("\u001B[0m");
    }

    private static void addNewEmployee() {
        System.out.print("Enter the username of the customer: ");
        String username = scanner.next();

        int customerId = CustomerRepository.getCustomerIdByUsername(username).orElse(0);

        if(customerId == 0) {
            errorMessage("User not found!");
        }
        else {
            Customer customer = CustomerRepository.getCustomerById(customerId).orElse(null);
            System.out.print("Enter the salary for the employee: ");
            double salary = scanner.nextDouble();

            while(salary <= 0) {
                errorMessage("Wrong value for salary");
                System.out.print("Enter a valid value for salary: ");
                salary = scanner.nextDouble();
            }

            System.out.print("Enter the ranking for the employee: ");
            String ranking = scanner.next();

            // Add the new employee
            EmployeeRepository.addEmployee(customer, salary, ranking);

            System.out.println("New employee added successfully.");
        }

    }

    private static Employee getEmployee() {
        System.out.print("Enter username of the employee: ");
        String username = scanner.next();

        int employeeId = EmployeeRepository.getEmployeeIdByUsername(username).orElse(0);

        if(employeeId == 0) {
            errorMessage("Employee not found");
            return null;
        }
        else {
            return EmployeeRepository.getEmployeeById(employeeId).orElse(null);
        }

    }

    private static void modifySalary(Employee employee) {
        double salary = scanner.nextDouble();

        while(salary <= 0) {
            errorMessage("Wrong value for salary");
            System.out.print("Enter a valid value for salary: ");
            salary = scanner.nextDouble();
        }
        employee.setSalary(salary);

        EmployeeRepository.editEmployee(employee);
    }

    private static void modifyRanking(Employee employee) {
        System.out.print("Enter the ranking for the employee: ");
        String ranking = scanner.next();

        employee.setRanking(new StringBuilder(ranking));

        EmployeeRepository.editEmployee(employee);
    }

    private static void deleteEmployee(int employeeId) {
        System.out.println("Are you sure you want to delete this employee forever?");
        System.out.print("Type YES or NO: ");
        String choice = scanner.next();

        if(choice.equals("YES")) {
            EmployeeRepository.deleteEmployee(employeeId);
            System.out.println("Selected employee deleted successfully!");
        }
    }

    public static void employeeScreen(Employee employee) {
        int option;
        while(true) {
            listMenu(employee.getRanking().toString().equalsIgnoreCase("king"));
            try {
                option = scanner.nextInt();
                switch (option) {
                    // List all orders made by customers
                    case 1: {
                        listOrders(Objects.requireNonNull(OrderRepository.getAllOrders().orElse(null)), false);
                        scanner.nextLine();
                        System.out.println("Press enter to dismiss...");
                        scanner.nextLine();
                        break;
                    }

                    // Find an order by ID or customer username
                    case 2: {
                        findOrder();
                        break;
                    }

                    // Lists all orders between selected dates
                    case 3: {
                        listOrders(filterOrders(), false);
                        break;
                    }

                    // Update order status
                    case 4: {
                        listOrders(Objects.requireNonNull(OrderRepository.getAllOrders().orElse(null)), true);
                        break;
                    }

                    // Adds new employee from customer database
                    case 5: {
                        if(employee.getRanking().toString().equalsIgnoreCase("king")) {
                            addNewEmployee();
                        }
                        else {
                            invalidInputText();
                        }
                        break;
                    }

                    // Modifies employees salary
                    case 6: {
                        if(employee.getRanking().toString().equalsIgnoreCase("king")) {
                            Employee selectedEmployee = getEmployee();
                            if(selectedEmployee != null && selectedEmployee.getEmployee_id() != employee.getEmployee_id()) {
                                modifySalary(selectedEmployee);
                            } else {
                                errorMessage("Employee does not exists or you chose yourself");
                            }
                        } else {
                            invalidInputText();
                        }
                        break;
                    }

                    // Modifies employees ranking
                    case 7: {
                        if(employee.getRanking().toString().equalsIgnoreCase("king")) {
                            Employee selectedEmployee = getEmployee();
                            if(selectedEmployee != null && selectedEmployee.getEmployee_id() != employee.getEmployee_id()) {
                                modifyRanking(selectedEmployee);
                            } else {
                                errorMessage("Employee does not exists or you chose yourself");
                            }
                        } else {
                            invalidInputText();
                        }

                        break;
                    }

                    // Deletes an employee
                    case 8: {
                        if(employee.getRanking().toString().equalsIgnoreCase("king")) {
                            Employee selectedEmployee = getEmployee();
                            if(selectedEmployee != null && selectedEmployee.getEmployee_id() != employee.getEmployee_id() ) {
                                deleteEmployee(selectedEmployee.getEmployee_id());
                            } else {
                                errorMessage("Employee does not exists or you chose yourself");
                            }
                        } else {
                            invalidInputText();
                        }

                        break;
                    }

                    case 0:
                        return;
                    default:
                        invalidInputText();
                        break;
                }
            }
            catch (InputMismatchException e) {
                invalidInputText();
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }
    }

}
