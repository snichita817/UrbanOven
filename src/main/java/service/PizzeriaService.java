package service;

import model.Pizzeria;

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



    public static void main(String[] args) {
        while(true)
        {
            greetScreen();
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    break;
                case 2:
                    pizzeria.addPeople(PersonService.newPerson());
                    System.out.println("User added successfully!");
                    break;
                case 0:
                    System.out.println("Thank you for visiting our shop!");
                    return;
            }
        }


    }

}
