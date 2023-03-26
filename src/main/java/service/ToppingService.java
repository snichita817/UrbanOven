package service;

import model.Topping;
import model.Topping.Measure;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToppingService {
    public final static Scanner scanner = new Scanner(System.in);
    public static Topping getTopping() {
        List<Topping> toppings = getToppings();
        System.out.println("==========   TOPPING CHOOSING MENU   ==========");
        int option;
        while(true) {
            listToppings(getToppings());
            System.out.println("Choose a topping from list below:");
            System.out.print("Option (1-10): ");
            option = scanner.nextInt();
            if (option < 1 || option > toppings.size()) {
                System.out.print("\u001B[31m");
                System.out.println("Invalid option: " + option + ". Please choose a number between 1 and " + toppings.size() + ".");
                System.out.print("\u001B[0m");
            } else {
                break;
            }
        }
        return getToppings().get(option-1);
    }

    public static void removeTopping(List<Topping> toppings) {
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
        toppings.remove(option-1);
    }

    public static Topping getSauce(double quantity) {
        Topping tomatoSauce = new Topping.Builder()
                .buildName("Tomato sauce")
                .buildPrice(0.05)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return tomatoSauce;
    }
    public static Topping getSauce() {
        Topping tomatoSauce = new Topping.Builder()
                .buildName("Tomato sauce")
                .buildPrice(0.05)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(90)
                .build();
        return tomatoSauce;
    }
    public static Topping getMozzarella(double quantity) {
        Topping mozzarella = new Topping.Builder()
                .buildName("Mozzarella")
                .buildPrice(0.070)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return mozzarella;
    }
    public static Topping getMozzarella() {
        Topping mozzarella = new Topping.Builder()
                .buildName("Mozzarella")
                .buildPrice(0.070)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return mozzarella;
    }
    public static Topping getMushroom(double quantity) {
        Topping mushroom = new Topping.Builder()
                .buildName("Mushroom")
                .buildPrice(0.090)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return mushroom;
    }
    public static Topping getMushroom() {
        Topping mushroom = new Topping.Builder()
                .buildName("Mushroom")
                .buildPrice(0.090)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(70)
                .build();
        return mushroom;
    }
    public static Topping getPepperoni(int quantity) {
        Topping pepperoni = new Topping.Builder()
                .buildName("Pepperoni")
                .buildPrice(0.9)
                .buildMeasure(Measure.pc)
                .buildIntQuantity(quantity)
                .build();
        return pepperoni;
    }
    public static Topping getPepperoni() {
        Topping pepperoni = new Topping.Builder()
                .buildName("Pepperoni")
                .buildPrice(0.9)
                .buildMeasure(Measure.pc)
                .buildIntQuantity(10)
                .build();
        return pepperoni;
    }
    public static Topping getOnion(double quantity) {
        Topping onion = new Topping.Builder()
                .buildName("Onion")
                .buildPrice(0.06)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return onion;
    }
    public static Topping getOnion() {
        Topping onion = new Topping.Builder()
                .buildName("Onion")
                .buildPrice(0.06)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(100)
                .build();
        return onion;
    }
    public static Topping getGreenPepper(double quantity) {
        Topping greenPepper = new Topping.Builder()
                .buildName("Green pepper")
                .buildPrice(0.036)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return greenPepper;
    }
    public static Topping getGreenPepper() {
        Topping greenPepper = new Topping.Builder()
                .buildName("Green pepper")
                .buildPrice(0.036)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(50)
                .build();
        return greenPepper;
    }
    public static Topping getOlives(double quantity) {
        Topping blackOlives = new Topping.Builder()
                .buildName("Black olives")
                .buildPrice(0.045)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return blackOlives;
    }
    public static Topping getOlives() {
        Topping blackOlives = new Topping.Builder()
                .buildName("Black olives")
                .buildPrice(0.045)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(50)
                .build();
        return blackOlives;
    }
    public static Topping getHam(double quantity) {
        Topping ham = new Topping.Builder()
                .buildName("Ham")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return ham;
    }
    public static Topping getHam() {
        Topping ham = new Topping.Builder()
                .buildName("Ham")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return ham;
    }
    public static Topping getPineapple(int quantity) {
        Topping pineapple = new Topping.Builder()
                .buildName("Pineapple")
                .buildPrice(0.45)
                .buildMeasure(Measure.pc)
                .buildIntQuantity(quantity)
                .build();
        return pineapple;
    }
    public static Topping getPineapple() {
        Topping pineapple = new Topping.Builder()
                .buildName("Pineapple")
                .buildPrice(0.45)
                .buildMeasure(Measure.pc)
                .buildIntQuantity(10)
                .build();
        return pineapple;
    }
    public static Topping getBacon(double quantity) {
        Topping bacon = new Topping.Builder()
                .buildName("Bacon")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
                .build();
        return bacon;
    }
    public static Topping getBacon() {
        Topping bacon = new Topping.Builder()
                .buildName("Bacon")
                .buildPrice(0.095)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(80)
                .build();
        return bacon;
    }
    public static List<Topping> getToppings() {
        List<Topping> toppings = new ArrayList<>();

        toppings.add(getSauce(90));                       // SAUCE
        toppings.add(getMozzarella(80));                  // CHEESE
        toppings.add(getMushroom(70));                    // MUSHROOM
        toppings.add(getPepperoni(10));                   // PEPPERONI
        toppings.add(getOnion(100));                      // ONION
        toppings.add(getGreenPepper(50));                 // GREEN PEPPER
        toppings.add(getOlives(50));                      // OLIVES
        toppings.add(getHam(80));                         // HAM
        toppings.add(getPineapple(10));                   // PINEAPPLE
        toppings.add(getBacon(80));                       // BACON

        return toppings;
    }

    public static void listToppings(List<Topping> toppings) {
/*        List<Topping> toppings = new ArrayList<>();
        toppings = getToppings();*/
        for(int i = 0; i<toppings.size(); i++) {
            System.out.println(String.format( "%d. %s", (i+1), toppings.get(i).getName()));
        }
    }

}
