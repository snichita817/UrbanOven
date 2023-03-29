package service;

import model.Drink;
import model.Product;
import model.ProductComparator;
import model.Topping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DrinkService {
    private final static Scanner scanner = new Scanner(System.in);

    public static void modifyDrinkSize(Drink drink) {
        while(true)
        {
            int sizeOption;
            System.out.println("Options:");
            System.out.println("\t1 -> 250 mL");
            System.out.println("\t2 -> 350 mL");
            System.out.println("\t3 -> 500 mL");
            System.out.print("Select drink size: ");
            sizeOption = scanner.nextInt();

            if (sizeOption != 1 && sizeOption != 2 && sizeOption != 3 ) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + sizeOption + ". Please choose a valid number (1-3).");
                System.out.print("\u001B[0m");
            } else {
                drink.setSize(sizeOption);
                System.out.println("Size selected successfully!");
                break;
            }
        }

    }

    public static Drink getDrink() {
        List<Drink> drinks = getDrinks(false);
        System.out.println("\t\t\t==========   DRINK CHOOSING MENU   ==========");
        int option;
        Product drink = null;
        while(true) {
            listDrinks(drinks);
            System.out.println("Choose a drink from the list below:");
            System.out.print( String.format("Option (1-%d): ", drinks.size()));
            option = scanner.nextInt();
            if (option < 1 || option > drinks.size()) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + drinks.size() + ".");
                System.out.print("\u001B[0m");
            } else {
                drink = drinks.get(option-1);
                modifyDrinkSize((Drink)drink);
                break;
            }
        }
        System.out.println(drink);
        return (Drink)drink;
    }

    public static void printModifyMenu() {
        System.out.println("1. Add topping;");
        System.out.println("2. Remove topping;");
        System.out.println("3. Modify topping quantity;");
        System.out.println("4. Modify drink size;");
        System.out.println("0. Back");
    }

    public static void modifyDrink(Drink drink, List<Product> products) {
        System.out.println("==========   DRINK PERSONALIZER   ==========");
        int option;

        while (true)
        {
            System.out.println("Your drink: ");
            System.out.println(drink);
            System.out.println("Select an option (1-3): ");
            printModifyMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1: {
                    drink.addTopping(ToppingService.getTopping(ToppingService.getDrinkToppings()));
                    break;
                }
                case 2: {
                    drink.removeTopping(ToppingService.removeTopping(drink.getToppings()));
                    break;
                }
                case 3: {
                    ToppingService.modifyToppingQuantity(drink.getToppings());
                    drink.recalculatePrice();
                    break;
                }
                case 4: {
                    modifyDrinkSize(drink);
                    break;
                }
                case 5: {
                    ProductService.deleteProduct(drink, products);
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.print("\u001B[31m");
                    System.out.println("Invalid option: " + option + ".");
                    System.out.print("\u001B[0m");
                }
            }
        }

    }

    public static Drink getLemonade() {
        Product lemonade = new Drink.Builder()
                .buildName("Lemonade with mint")
                .buildWithAlcohol(false)
                .buildTopping(
                   new ArrayList<>(
                       List.of(ToppingService.getIce(),
                               ToppingService.getLemon(),
                               ToppingService.getSugar(),
                               ToppingService.getMint(),
                               ToppingService.getGinger())
                   )
                )
                .build();
        return (Drink) lemonade;
    }

    public static Drink getFanta() {
        Product fanta = new Drink.Builder()
                .buildName("Fanta with lemon")
                .buildWithAlcohol(false)
                .buildTopping(
                        new ArrayList<>(
                                List.of(ToppingService.getIce(),
                                        ToppingService.getLemon())
                        )
                )
                .build();
        return (Drink) fanta;
    }

    public static Drink getGinAndTonic() {
        Product ginAndTonic = new Drink.Builder()
                .buildName("Gin and Tonic")
                .buildWithAlcohol(false)
                .buildTopping(
                        new ArrayList<>(
                                List.of(ToppingService.getIce(),
                                        ToppingService.getLime())
                        )
                )
                .build();
        return (Drink) ginAndTonic;
    }

    public static Drink getMojito() {
        Product mojito = new Drink.Builder()
                .buildName("Mojito")
                .buildWithAlcohol(true)
                .buildTopping(
                        new ArrayList<>(
                                List.of(
                                        ToppingService.getIce(),
                                        ToppingService.getLemon(),
                                        ToppingService.getMint(),
                                        ToppingService.getSugar()
                                )
                        )
                )
                .build();
        return (Drink) mojito;
    }

    public static List<Drink> getDrinks(boolean isStart) {
        List<Drink> drinks = new ArrayList<>();
        drinks.add(getLemonade());
        drinks.add(getFanta());
        drinks.add(getGinAndTonic());
        drinks.add(getMojito());

        // will only print out this if it's not instantiating the pizzeria class
        if (!isStart)
        {
            System.out.println("How would you like your drinks to be printed?");
            System.out.println("1 -> Default");
            System.out.println("2 -> Price (low - high)");
            System.out.println("3 -> Price (high - low)");
            System.out.print("Option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    break;
                case 2:
                    Collections.sort(drinks, new ProductComparator(true));
                    break;
                case 3:
                    Collections.sort(drinks, new ProductComparator(false));
                    break;

            }
        }


        return drinks;
    }

    public static void listDrinks(List<Drink> drinks) {
        for(int i = 0; i< drinks.size(); i++) {
            System.out.println(String.format( "\t\t\t%d. %s    ->    PRICE: " + String.format("%.2f", drinks.get(i).getPrice()), (i+1), drinks.get(i).getName()));
        }
    }
}
