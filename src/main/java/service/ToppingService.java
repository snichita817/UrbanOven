package service;

import model.product.Topping;
import model.product.Topping.Measure;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ToppingService {
    private final static Scanner scanner = new Scanner(System.in);
    public static Topping getTopping(List<Topping> toppings) {
        System.out.println("==========   TOPPING CHOOSING MENU   ==========");
        int option;
        while(true) {
            listToppings(toppings);
            System.out.println("Choose a topping from list below:");
            System.out.print(String.format("Option (1-%d): ", toppings.size()));
            option = scanner.nextInt();
            if (option < 1 || option > toppings.size()) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + toppings.size() + ".");
                System.out.print("\u001B[0m");
            } else {
                break;
            }
        }
        return toppings.get(option-1);
    }

    public static void modifyToppingQuantity(List<Topping> toppings) {
        System.out.println("Current toppings:");
        int option;
        while (true) {
            listToppings(toppings);
            System.out.println("Choose a topping to edit from the list below: ");
            System.out.print( String.format("Option (1-%d): ", toppings.size()));

            try {
                option = scanner.nextInt();

                if (option < 1 || option > toppings.size()) {
                    System.out.print("\u001B[31m");
                    System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + toppings.size() + ".");
                    System.out.print("\u001B[0m");
                } else {
                    Topping topping = toppings.get(option-1);

                    System.out.print("Select a quantity ");

                    if(Measure.pc == topping.getUnitOfMeasure()) {
                        System.out.print("(pieces): ");
                        int newQuantity = scanner.nextInt();
                        topping.setIntQuantity(newQuantity);
                    }
                    else {
                        System.out.print("(grams): ");
                        double newQuantity = scanner.nextDouble();
                        topping.setDoubleQuantity(newQuantity);
                    }
                    System.out.println("Quantity of " + topping.getName() + " modified!");
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

    public static int removeTopping(List<Topping> toppings) {
        System.out.println("Current toppings:");
        int option;
        while(true) {
            listToppings(toppings);
            System.out.println("Choose a topping to delete from list below:");
            System.out.print( String.format("Option (1-%d): ", toppings.size()));
            option = scanner.nextInt();
            if (option < 1 || option > toppings.size()) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + toppings.size() + ".");
                System.out.print("\u001B[0m");
            } else {
                break;
            }
        }
        return option-1;
    }


    // =============== TOPPINGS FOR PIZZA =============== //
    public static Topping getSauce(double quantity) {
        Topping tomatoSauce = new Topping.Builder()
                .buildId("P1")
                .buildName("Tomato sauce")
                .buildPrice(0.05)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return tomatoSauce;
    }
    public static Topping getSauce() {
        Topping tomatoSauce = new Topping.Builder()
                .buildId("P1")
                .buildName("Tomato sauce")
                .buildPrice(0.05)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(90)
                .build();
        return tomatoSauce;
    }
    public static Topping getMozzarella(double quantity) {
        Topping mozzarella = new Topping.Builder()
                .buildId("P2")
                .buildName("Mozzarella")
                .buildPrice(0.070)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return mozzarella;
    }
    public static Topping getMozzarella() {
        Topping mozzarella = new Topping.Builder()
                .buildId("P2")
                .buildName("Mozzarella")
                .buildPrice(0.070)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return mozzarella;
    }
    public static Topping getMushroom(double quantity) {
        Topping mushroom = new Topping.Builder()
                .buildId("P3")
                .buildName("Mushroom")
                .buildPrice(0.090)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return mushroom;
    }
    public static Topping getMushroom() {
        Topping mushroom = new Topping.Builder()
                .buildId("P3")
                .buildName("Mushroom")
                .buildPrice(0.090)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(70)
                .build();
        return mushroom;
    }
    public static Topping getPepperoni(int quantity) {
        Topping pepperoni = new Topping.Builder()
                .buildId("P4")
                .buildName("Pepperoni")
                .buildPrice(0.9)
                .buildMeasure(Measure.pc)
                .buildDoubleQuantity(quantity)
                .build();
        return pepperoni;
    }
    public static Topping getPepperoni() {
        Topping pepperoni = new Topping.Builder()
                .buildId("P4")
                .buildName("Pepperoni")
                .buildPrice(0.9)
                .buildMeasure(Measure.pc)
                .buildDoubleQuantity(10)
                .build();
        return pepperoni;
    }
    public static Topping getOnion(double quantity) {
        Topping onion = new Topping.Builder()
                .buildId("P5")
                .buildName("Onion")
                .buildPrice(0.06)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return onion;
    }
    public static Topping getOnion() {
        Topping onion = new Topping.Builder()
                .buildId("P5")
                .buildName("Onion")
                .buildPrice(0.06)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(100)
                .build();
        return onion;
    }
    public static Topping getGreenPepper(double quantity) {
        Topping greenPepper = new Topping.Builder()
                .buildId("P6")
                .buildName("Green pepper")
                .buildPrice(0.036)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return greenPepper;
    }
    public static Topping getGreenPepper() {
        Topping greenPepper = new Topping.Builder()
                .buildId("P6")
                .buildName("Green pepper")
                .buildPrice(0.036)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(50)
                .build();
        return greenPepper;
    }
    public static Topping getOlives(double quantity) {
        Topping blackOlives = new Topping.Builder()
                .buildId("P7")
                .buildName("Black olives")
                .buildPrice(0.045)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return blackOlives;
    }
    public static Topping getOlives() {
        Topping blackOlives = new Topping.Builder()
                .buildId("P7")
                .buildName("Black olives")
                .buildPrice(0.045)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(50)
                .build();
        return blackOlives;
    }
    public static Topping getHam(double quantity) {
        Topping ham = new Topping.Builder()
                .buildId("P8")
                .buildName("Ham")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return ham;
    }
    public static Topping getHam() {
        Topping ham = new Topping.Builder()
                .buildId("P8")
                .buildName("Ham")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return ham;
    }
    public static Topping getPineapple(int quantity) {
        Topping pineapple = new Topping.Builder()
                .buildId("P9")
                .buildName("Pineapple")
                .buildPrice(0.45)
                .buildMeasure(Measure.pc)
                .buildDoubleQuantity(quantity)
                .build();
        return pineapple;
    }
    public static Topping getPineapple() {
        Topping pineapple = new Topping.Builder()
                .buildId("P9")
                .buildName("Pineapple")
                .buildPrice(0.45)
                .buildMeasure(Measure.pc)
                .buildDoubleQuantity(10.0)
                .build();
        return pineapple;
    }
    public static Topping getBacon(double quantity) {
        Topping bacon = new Topping.Builder()
                .buildId("P10")
                .buildName("Bacon")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return bacon;
    }
    public static Topping getBacon() {
        Topping bacon = new Topping.Builder()
                .buildId("P10")
                .buildName("Bacon")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return bacon;
    }
    public static List<Topping> getPizzaToppings() {
        List<Topping> toppings = new ArrayList<>();

        toppings.add(getSauce());                       // SAUCE
        toppings.add(getMozzarella());                  // CHEESE
        toppings.add(getMushroom());                    // MUSHROOM
        toppings.add(getPepperoni());                   // PEPPERONI
        toppings.add(getOnion());                       // ONION
        toppings.add(getGreenPepper());                 // GREEN PEPPER
        toppings.add(getOlives());                      // OLIVES
        toppings.add(getHam());                         // HAM
        toppings.add(getPineapple());                   // PINEAPPLE
        toppings.add(getBacon());                       // BACON

        return toppings;
    }


    // =============== TOPPINGS FOR DRINKS =============== //
    public static Topping getIce() {
        Topping ice = new Topping.Builder()
                .buildId("D1")
                .buildName("Ice")
                .buildMeasure(Measure.pc)
                .buildPrice(0.5)
                .buildIntQuantity(5)
                .build();
        return ice;
    }

    public static Topping getLemon() {
        Topping lemon = new Topping.Builder()
                .buildId("D2")
                .buildName("Lemon")
                .buildMeasure(Measure.pc)
                .buildPrice(0.7)
                .buildIntQuantity(5)
                .build();
        return lemon;
    }

    public static Topping getMint() {
        Topping mint = new Topping.Builder()
                .buildId("D3")
                .buildName("Mint")
                .buildMeasure(Measure.pc)
                .buildPrice(0.8)
                .buildIntQuantity(5)
                .build();
        return mint;
    }

    public static Topping getGinger() {
        Topping ginger = new Topping.Builder()
                .buildId("D4")
                .buildName("Ginger")
                .buildMeasure(Measure.gr)
                .buildPrice(0.8)
                .buildIntQuantity(5)
                .build();
        return ginger;
    }

    public static Topping getSugar() {
        Topping sugar = new Topping.Builder()
                .buildId("D5")
                .buildName("Sugar")
                .buildMeasure(Measure.gr)
                .buildPrice(2)
                .buildIntQuantity(3)
                .build();
        return sugar;
    }

    public static Topping getLime() {
        Topping lime = new Topping.Builder()
                .buildId("D6")
                .buildName("Lime")
                .buildMeasure(Measure.pc)
                .buildPrice(0.8)
                .buildIntQuantity(6)
                .build();
        return lime;
    }

    public static List<Topping> getDrinkToppings() {
        List<Topping> toppings = new ArrayList<>();
        toppings.add(getIce());
        toppings.add(getLemon());
        toppings.add(getMint());

        return toppings;
    }

    public static void listToppings(List<Topping> toppings) {
        for(int i = 0; i<toppings.size(); i++) {
            System.out.println(String.format( "%d. %s", (i+1), toppings.get(i).getName()));
        }
    }

}
