package service;

import model.*;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class PersonService {
    public static Scanner scanner = new Scanner(System.in);

    public static Customer newPerson() {
        System.out.println("Enter customer details:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print("Username: ");
        String userName = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phoneNumber = scanner.nextLine();

        Customer customer = new Customer.Builder()
                .buildFirstName(firstName)
                .buildLastName(lastName)
                .buildUserName(userName)
                .buildPassword(password)
                .buildAddress(address)
                .buildPhoneNumber(phoneNumber)
                .build();

        return customer;
    }

    public static void printOrderHistory(Customer customer) {
        List<Order> orderList = customer.getOrderHistory();
        System.out.println("Your current orders:");
        for(int i = 0; i<orderList.size(); i++) {
            System.out.println(orderList.get(i));
        }
    }

}
