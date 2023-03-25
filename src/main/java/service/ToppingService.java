package service;

import model.Topping;
import model.Topping.Measure;
import java.util.ArrayList;
import java.util.List;

public class ToppingService {
    public static Topping getSauce(double quantity) {
        Topping tomatoSauce = new Topping.Builder()
                .buildName("Tomato sauce")
                .buildPrice(0.05)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
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
    public static Topping getMushroom(double quantity) {
        Topping mushroom = new Topping.Builder()
                .buildName("Mushroom")
                .buildPrice(0.090)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
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
    public static Topping getOnion(double quantity) {
        Topping onion = new Topping.Builder()
                .buildName("Onion")
                .buildPrice(0.06)
                .buildMeasure(Measure.gr)
                .buildDoubleQuantity(quantity)
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
    public static Topping getOlives(double quantity) {
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
    public static Topping getPineapple(int quantity) {
        Topping pineapple = new Topping.Builder()
                .buildName("Pineapple")
                .buildPrice(0.45)
                .buildMeasure(Measure.pc)
                .buildIntQuantity(quantity)
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

    public static void listToppings() {
        for(Topping topping : getToppings()) {
            System.out.println(topping.getName());
        }
    }

}
