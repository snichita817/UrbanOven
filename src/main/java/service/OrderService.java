package service;

import model.product.Drink;
import model.product.Pizza;
import model.product.Product;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class OrderService {
    public static Scanner scanner = new Scanner(System.in);
    public static void chooseOrder() {
        System.out.println("What would you like to add to your order?");
        System.out.println("1. Add pizza");
        System.out.println("2. Add drinks");
        System.out.println("3. Modify current order");
        System.out.println("0. Back (THIS WILL CONFIRM YOUR ORDER)");
        System.out.print("Option: ");
    }

    public static void modifyMenu() {
        System.out.println("What do you want to modify?");
        System.out.println("1 -> Pizzas");
        System.out.println("2 -> Drinks");
        System.out.println("0 -> Back");
        System.out.print("Option: ");
    }

    public static Pizza returnPizza(List<Product> products) {
        int no_pizzas = 0;
        List<Pizza> pizzas = new ArrayList<>();
        for(int i = 0; i<products.size(); i++) {
            if(products.get(i) instanceof Pizza) {
                no_pizzas += 1;
                pizzas.add((Pizza) products.get(i));
                System.out.println( String.format( "Pizza no. %d\n", (i+1) ) + ( ((Pizza) products.get(i)).toString()));
            }
        }

        while (true)
        {
            if(no_pizzas == 0) {
                return null;
            }
            System.out.print( String.format("Option (%d - %d): ", 1, no_pizzas) );

            try {
                int option = scanner.nextInt();

                if(option < 1 || option > no_pizzas) {
                    System.out.print("\u001B[31m");
                    System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + no_pizzas + ".");
                    System.out.print("\u001B[0m");
                }
                else {
                    return pizzas.get(option-1);
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

    public static Drink returnDrink(List<Product> products) {
        int no_drinks = 0;
        List<Drink> drinks = new ArrayList<>();
        for(int i = 0; i<products.size(); i++) {
            if(products.get(i) instanceof Drink) {
                no_drinks += 1;
                drinks.add((Drink) products.get(i));
                System.out.println( String.format( "Drink no. %d\n", (no_drinks) ) + ( ((Drink) products.get(i)).toString()));
            }
        }
        while (true)
        {
            if (no_drinks == 0) {
                return null;
            }
            System.out.print( String.format("Option (%d - %d): ", 1, no_drinks) );

            try {
                int option = scanner.nextInt();

                if(option < 1 || option > no_drinks) {
                    System.out.print("\u001B[31m");
                    System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + no_drinks + ".");
                    System.out.print("\u001B[0m");
                }
                else {
                    return drinks.get(option-1);
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


    public static void modifyProduct(List<Product> products) {
        int option;
        while (true) {
            modifyMenu();

            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        // 1. print list of pizzas
                        // 2. select a pizza from a list
                        // 3. modify pizza with PizzaService
                        Pizza selectedPizza = returnPizza(products);
                        if(selectedPizza != null)
                        {
                            ProductService.modifyProduct(selectedPizza, products);
                        }
                        else {
                            System.out.print("\u001B[31m");
                            System.out.println("You have no products of this type in the basket!");
                            System.out.print("\u001B[0m");
                        }
                        break;
                    case 2:
                        // print list of drinks
                        // select a drink from a list
                        // modify drink with DrinkService
                        Drink selectedDrink = returnDrink(products);
                        if(selectedDrink!= null)
                        {
                            ProductService.modifyProduct(selectedDrink, products);
                        }
                        else {
                            System.out.print("\u001B[31m");
                            System.out.println("You have no products of this type in the basket!");
                            System.out.print("\u001B[0m");
                        }
                        break;
                    case 0:
                        return;
                    default:
                        break;
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

    public static void orderScreen(List<Product> products) {
        int option;
        while(true) {
            ProductService.listProducts(products);
            chooseOrder();

            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        products.add((Pizza) PizzaService.getPizza());
                        break;
                    case 2:
                        products.add((Drink) DrinkService.getDrink());
                        break;
                    case 3: {
                        if(products.size() != 0) {
                            modifyProduct(products);
                        }
                        else {

                            System.out.print("\u001B[31m");
                            System.out.println("You have no products in your order!");
                            System.out.print("\u001B[0m");
                        }
                        break;
                    }
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid option!");
                        break;
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
