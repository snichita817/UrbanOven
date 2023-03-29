package service;

import model.Person;
import model.Pizzeria;
import model.Customer;

import java.util.List;
import java.util.Scanner;

public class PizzeriaService {
    private static Pizzeria pizzeria = new Pizzeria();
    public static Scanner scanner = new Scanner(System.in);
    public static void greetScreen() {
        System.out.println("\t\t\t=================  WELCOME TO URBANOVEN  =================");
        System.out.println("\t\t\t1. Log in");
        System.out.println("\t\t\t2. Register an account");
        System.out.println("\t\t\t0. Exit");
        System.out.println("\t\t\t==========================================================");
        System.out.print("Select an option: ");
    }

    public static Person login() {
        System.out.println("Enter your login details");
        System.out.print("Username: ");
        String username = scanner.next();
        scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        List<Person> listOfPeople = pizzeria.getPeople();

        for(Person person : listOfPeople) {
            if( person.getUserName().compareTo(new StringBuilder(username)) == 0 && person.getPassword().compareTo(new StringBuilder(password)) == 0)
            {
                return person;
            }

        }
        return null;
    }
    public static void openShop() {
        while(true)
        {
            greetScreen();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    Person currentLogin = login();
                    if(currentLogin == null)
                    {
                        System.out.println("Username or password is incorrect!");
                        break;
                        // PART where USER IS LOGGED IN
                    } else if (currentLogin instanceof Customer) {
                            CustomerService.serviceScreen((Customer)currentLogin);
                    }
                    break;
                case 2:
                    pizzeria.addPeople(PersonService.newPerson(pizzeria.getPeople()));
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


    }

}
