package service;

import model.*;
import model.person.Customer;
import model.person.Person;

import java.util.List;
import java.util.Scanner;

public class PersonService {
    public static Scanner scanner = new Scanner(System.in);

    public static Customer newPerson(List<Person> people) {
        System.out.println("Enter customer details:");

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        String userName = null;
        boolean unique = true;
        while(unique)
        {
            System.out.print("Username: ");
            userName = scanner.nextLine();
            if(people.size() == 0)
                break;
            for(Person pers : people) {
                if( pers.getUserName().compareTo(new StringBuilder(userName)) == 0 )
                {
                    System.out.print("\u001B[31m");
                    System.out.println("User " + userName + " already exists!");
                    System.out.print("\u001B[0m");
                }
                else {
                    unique = false;
                }
            }
        }
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
