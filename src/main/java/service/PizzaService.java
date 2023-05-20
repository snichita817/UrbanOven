package service;

import model.product.Pizza;
import model.product.Product;
import model.product.ProductComparator;
import repository.PizzaRepository;

import java.util.*;

public class PizzaService {
    private final static Scanner scanner = new Scanner(System.in);

    public static void modifyPizzaSize(Pizza pizza) {
        while(true)
        {
            int sizeOption;
            System.out.print("Select pizza size (6, 8, 10 slices): ");

            try {
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
            catch (InputMismatchException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("\u001B[0m");
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }

    }

    public static Pizza getPizza() {
        List<Pizza> pizzas = getPizzas(false);
        System.out.println("\t\t\t==========   PIZZA CHOOSING MENU   ==========");
        int option;
        Product pizza = null;
        while(true) {
            listPizzas(pizzas);
            System.out.println("Choose a pizza from the list below:");
            System.out.print( String.format("Option (1-%d): ", pizzas.size()));

            try {
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
            catch (InputMismatchException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("\u001B[0m");
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }
        System.out.println(pizza);
        return (Pizza)pizza;
    }

    public static Pizza getMargherita(){
        Product pizza = new Pizza.Builder()
                .buildId(1)
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
                .buildId(2)
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
                .buildId(3)
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
                .buildId(4)
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
                .buildId(5)
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

    public static List<Pizza> getPizzas(boolean isStart) {
        if(isStart) {
            PizzaRepository.addPizza(getMargherita());
            PizzaRepository.addPizza(getPepperoni());
            PizzaRepository.addPizza(getVegetarian());
            PizzaRepository.addPizza(getHawaiian());
            PizzaRepository.addPizza(getMeatLovers());
        }
//        List<Pizza> pizzas = PizzaRepository.getAllPizzas();
        List<Pizza> pizzas = new ArrayList<>();
        pizzas.add(getMargherita());
        pizzas.add(getPepperoni());
        pizzas.add(getVegetarian());
        pizzas.add(getHawaiian());
        pizzas.add(getMeatLovers());
        // will only print out this if it's not instantiating the pizzeria class
        if(!isStart)
        {
            System.out.println("How would you like your pizzas to be printed?");
            System.out.println("1 -> Default");
            System.out.println("2 -> Price (low - high)");
            System.out.println("3 -> Price (high - low)");
            System.out.print("Option: ");


            try {
                int option = scanner.nextInt();

                switch (option) {
                    case 1:
                        break;
                    case 2:
                        Collections.sort(pizzas, new ProductComparator(true));
                        break;
                    case 3:
                        Collections.sort(pizzas, new ProductComparator(false));
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

        return pizzas;
    }

    public static void listPizzas(List<Pizza> pizzas) {

        for(int i = 0; i< pizzas.size(); i++) {
            System.out.println(String.format( "%d. %s    ->    PRICE: " + String.format("%.2f", pizzas.get(i).getPrice()), (i+1), pizzas.get(i).getName()));
        }
    }

    public static void listPizzas(List<Pizza> pizzas, boolean inAscending) {
        Collections.sort(pizzas, new ProductComparator(inAscending));
        for(int i = 0; i< pizzas.size(); i++) {
            System.out.println(String.format( "\t\t\t%d. %s    ->    PRICE: " + String.format("%.2f", pizzas.get(i).getPrice()), (i+1), pizzas.get(i).getName()));
        }
    }

}
