package service;

import model.product.Drink;
import model.product.Pizza;
import model.product.Product;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductService {
    private final static Scanner scanner = new Scanner(System.in);
    public static void deleteProduct(Product product, List<Product> products){

        while (true)
        {
            System.out.print("Are you sure you want to delete " + product.getName() + "? [Y(es) | n]: ");
            scanner.nextLine();
            String opt = scanner.nextLine();

            if(opt.equalsIgnoreCase("y")) {
                products.remove(product);
                System.out.println("Product removed successfully!");
                break;
            }
            else if (opt.equalsIgnoreCase("n")){
                break;
            }
            else {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option!");
                System.out.print("\u001B[0m");
            }
        }

    }
    public static void printModifyMenu(StringBuilder productName) {
        System.out.println("1. Add topping;");
        System.out.println("2. Remove topping;");
        System.out.println("3. Modify topping quantity;");
        System.out.println("4. Modify " + productName + " size;");
        System.out.println("5. Delete " + productName + ";");
        System.out.println("0. Back");
    }
    public static void modifyProduct(Product product, List<Product> products) {
        System.out.println("==========   PRODUCT PERSONALIZER   ==========");
        int option;
        while (true) {
            System.out.println(product);
            printModifyMenu(product.getName());
            System.out.print("Select an option (1-5): ");

            try {
                option = scanner.nextInt();
                switch (option) {
                    case 1: {
                        if(product instanceof Drink)
                            ((Drink)product).addTopping(ToppingService.getTopping(ToppingService.getDrinkToppings()));
                        else ((Pizza)product).addTopping(ToppingService.getTopping(ToppingService.getPizzaToppings()));
                        break;
                    }
                    case 2: {
                        product.removeTopping(ToppingService.removeTopping(product.getToppings()));
                        break;
                    }
                    case 3: {
                        ToppingService.modifyToppingQuantity(product.getToppings());
                        product.recalculatePrice();
                        break;
                    }
                    case 4: {
                        if(product instanceof Drink)
                        {
                            DrinkService.modifyDrinkSize((Drink)product);
                        }
                        else {
                            PizzaService.modifyPizzaSize((Pizza)product);
                        }
                        break;
                    }
                    case 5: {
                        ProductService.deleteProduct(product, products);
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
            catch (InputMismatchException e) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid input! Please enter a valid integer.");
                System.out.print("\u001B[0m");
                scanner.next(); // consume the invalid input to avoid an infinite loop
            }
        }
    }

    public static void listPizzas(List<Product> products)
    {
        System.out.println("\t\t\t===========  OUR PIZZAS  ===========");
        for(Product product : products) {
            if(product instanceof Pizza) {
                System.out.println(((Pizza) product).toString());
            }
        }
    }

    public static void listDrinks(List<Product> products)
    {
        System.out.println("\t\t\t===========  OUR DRINKS  ===========");
        for(Product product : products) {
            if(product instanceof Drink) {
                System.out.println(((Drink) product ).toString());
            }
        }
    }

    public static void listProducts(List<Product> products) {
        listPizzas(products);
        listDrinks(products);
    }
}
