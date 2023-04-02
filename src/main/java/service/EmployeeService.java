package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;

import model.Order;
import model.Pizzeria;

public class EmployeeService {
    private static Scanner scanner = new Scanner(System.in);
    public static void invalidInputText() {
        System.out.print("\u001B[31m");
        System.out.println("Invalid input! Please enter a valid integer.");
        System.out.print("\u001B[0m");
    }
    public static void listMenu() {
        System.out.println("\n\n\t\t\t==========================================================");
        System.out.println("\t\t\t1. List all orders");
        System.out.println("\t\t\t2. Find order");        // Find order by order ID, Find order by customer username
        System.out.println("\t\t\t3. Filter orders");     // Filter order by date
        System.out.println("\t\t\t0. Log out");
        System.out.println("\t\t\t==========================================================");
        System.out.print("Option: ");
    }

    public static void listOrders(List<Order> allOrders) {
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
                        listOrders(getOrderUser());
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

    public static Order returnOrder(int id, List<Order> orderList) {
        for(Order order : orderList) {
            if(order.getOrderId() == id) {
                return order;
            }
        }
        return null;
    }

    public static Order getOrderId() {
        int orderId;
        try {
            System.out.print("Please type order ID: ");
            orderId = scanner.nextInt();
            List<Order> orderList = PizzeriaService.pizzeria.getAllOrders();

            for(Order order:orderList) {
                if(order.getOrderId() == orderId)
                    return order;
            }
        }
        catch (InputMismatchException e ){
            invalidInputText();
            scanner.next();
        }
        return null;
    }

    public static List<Order> getOrderUser() {
        String findUser;
        System.out.print("Type username: ");
        findUser = scanner.next();
        scanner.nextLine();
        List<Order> orderList = PizzeriaService.pizzeria.getAllOrders();
        List<Order> orderToReturn = new ArrayList<>();
        for(Order order : orderList) {
            if(order.getCustomer().getUserName().compareTo(new StringBuilder(findUser)) == 0) {
                orderToReturn.add(order);
            }
        }

        return orderToReturn;
    }

    public static List<Order> filterOrders(List<Order> orders) {
        scanner.nextLine();

        Calendar startCal = Calendar.getInstance();
        while (true)
        {
            try {
                System.out.print("Enter start date (dd/MM/yyyy) or press Enter for today's date: ");
                String startDateStr = scanner.nextLine();
                if (startDateStr.equals("")) {
                    startCal.setTime(new Date());
                    break;
                }
                else {
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

        while (true)
        {
            try {
                System.out.print("Enter end date (dd/MM/yyyy) or press Enter for today's date: ");
                String endDateStr = scanner.nextLine();
                if (endDateStr.equals("")) {
                    endCal.setTime(new Date());
                    break;
                }
                else {
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
        }

        List<Order> ordersBetweenDates = new ArrayList<>();
        for (Order order : orders) {
            Calendar orderCal = Calendar.getInstance();
            orderCal.setTime(order.getOrderTime());
            if (      (orderCal.get(Calendar.YEAR) >= startCal.get(Calendar.YEAR)
                    && orderCal.get(Calendar.MONTH) >= startCal.get(Calendar.MONTH)
                    && orderCal.get(Calendar.DAY_OF_MONTH) >= startCal.get(Calendar.DAY_OF_MONTH))
                    && (orderCal.get(Calendar.MONTH) <= endCal.get(Calendar.MONTH)
                    && orderCal.get(Calendar.DAY_OF_MONTH) <= endCal.get(Calendar.DAY_OF_MONTH)
                    && orderCal.get(Calendar.YEAR) <= endCal.get(Calendar.YEAR))) {
                ordersBetweenDates.add(order);
            }
        }

        return ordersBetweenDates;
    }

    public static void getStatistics() {
        List<Order> orderList = PizzeriaService.pizzeria.getAllOrders();
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        int todayOrders = 0;

        for(Order order : orderList) {
            Calendar orderCal = Calendar.getInstance();
            orderCal.setTime(order.getOrderTime());
            if( orderCal.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                    && orderCal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH) ) {
                todayOrders += 1;
            }
        }

    }

    public static void employeeScreen() {
        int option;
        while(true) {
            listMenu();
            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1:
                        listOrders(PizzeriaService.pizzeria.getAllOrders());
                        scanner.nextLine();
                        System.out.println("Press enter to dismiss...");
                        scanner.nextLine();
                        break;
                    case 2:
                        findOrder();
                        break;
                    case 3:
                        listOrders(filterOrders(PizzeriaService.pizzeria.getAllOrders()));
                        break;
                    case 4:
                        getStatistics();
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
