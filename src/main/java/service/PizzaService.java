package service;

import model.Pizza;
import model.Product;
import model.Topping;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PizzaService {
    private final static Scanner scanner = new Scanner(System.in);

    public static void modifyPizzaSize(Pizza pizza) {
        while(true)
        {
            int sizeOption;
            System.out.print("Select pizza size (6, 8, 10 slices): ");
            sizeOption = scanner.nextInt();

            if (sizeOption != 6 && sizeOption != 8 && sizeOption != 10 ) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + sizeOption + ". Please choose a valid number (6 or 8 or 10).");
                System.out.print("\u001B[0m");
            } else {
                pizza.setSize(sizeOption);
                System.out.println("Size selected successfully!");
                break;
            }
        }

    }

    public static Pizza getPizza() {
        List<Pizza> pizzas = getPizzas();
        System.out.println("==========   PIZZA CHOOSING MENU   ==========");
        int option;
        Product pizza = null;
        while(true) {
            listPizzas(getPizzas());
            System.out.println("Choose a pizza from the list below:");
            System.out.print( String.format("Option (1-%d): ", pizzas.size()));
            option = scanner.nextInt();
            if (option < 1 || option > pizzas.size()) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + pizzas.size() + ".");
                System.out.print("\u001B[0m");
            } else {
                pizza = pizzas.get(option-1);
                modifyPizzaSize((Pizza)pizza);
                break;
            }
        }
        System.out.println(pizza);
        return (Pizza)pizza;
    }

    public static void printModifyMenu() {
        System.out.println("1. Add topping;");
        System.out.println("2. Remove topping;");
        System.out.println("3. Modify topping quantity;");
        System.out.println("4. Modify pizza size;");
        System.out.println("0. Back");
    }

    public static void modifyPizza(Pizza pizza) {
        System.out.println("==========   PIZZA PERSONALIZER   ==========");
        int option;

        while (true)
        {
            System.out.println("Your pizza: ");
            System.out.println(pizza);
            System.out.println("Select an option (1-3): ");
            printModifyMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1: {
                    pizza.addTopping(ToppingService.getTopping(ToppingService.getPizzaToppings()));
                    break;
                }
                case 2: {
                    pizza.removeTopping(ToppingService.removeTopping(pizza.getToppings()));
                    break;
                }
                case 3: {
                    ToppingService.modifyToppingQuantity(pizza.getToppings());
                    pizza.recalculatePrice();
                    break;
                }
                case 4: {
                    modifyPizzaSize(pizza);
                    break;
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

    public static Pizza getMargherita(){
        Product pizza = new Pizza.Builder()
                .buildName("Margherita")
                .buildTopping( new ArrayList<>(
                    List.of(ToppingService.getSauce(),
                            ToppingService.getMozzarella()))
                )
                .build();
        return (Pizza)pizza;
    }

    public static Pizza getPepperoni() {
        Product pizza = new Pizza.Builder()
                .buildName("Pepperoni")
                .buildTopping(new ArrayList<>(
                    List.of(ToppingService.getSauce(),
                            ToppingService.getMozzarella(),
                            ToppingService.getPepperoni()))
                )
                .build();
        return (Pizza)pizza;
    }

    public static Pizza getVegetarian(){
        Product pizza = new Pizza.Builder()
                .buildName("Vegetarian")
                .buildTopping(new ArrayList<>(
                    List.of(ToppingService.getSauce(),
                            ToppingService.getMozzarella(),
                            ToppingService.getOlives(),
                            ToppingService.getGreenPepper(),
                            ToppingService.getMushroom()))
                )
                .build();
        return (Pizza)pizza;
    }
    public static Pizza getHawaiian() {
        Product pizza = new Pizza.Builder()
                .buildName("Hawaiian")
                .buildTopping(new ArrayList<>(
                        List.of(ToppingService.getSauce(),
                                ToppingService.getMozzarella(),
                                ToppingService.getHam(),
                                ToppingService.getPineapple()))
                )
                .build();
        return (Pizza)pizza;
    }
    public static Pizza getMeatLovers() {
        Product pizza = new Pizza.Builder()
                .buildName("Meat Lovers")
                .buildTopping(new ArrayList<>(
                        List.of(ToppingService.getSauce(),
                                ToppingService.getMozzarella(),
                                ToppingService.getPepperoni(),
                                ToppingService.getBacon(),
                                ToppingService.getHam()))
                )
                .build();
        return (Pizza)pizza;
    }
    public static List<Pizza> getPizzas() {
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(getMargherita());
        pizzas.add(getPepperoni());
        pizzas.add(getVegetarian());
        pizzas.add(getHawaiian());
        pizzas.add(getMeatLovers());

        return pizzas;
    }

    public static void listPizzas(List<Pizza> pizzas) {
        for(int i = 0; i< pizzas.size(); i++) {
            System.out.println(String.format( "%d. %s    ->    PRICE: " + String.format("%.2f", pizzas.get(i).getPrice()), (i+1), pizzas.get(i).getName()));
        }
    }

}
