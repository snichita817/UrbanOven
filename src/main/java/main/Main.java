package main;
import model.*;
import service.ToppingService;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Topping t = new Topping.Builder().buildName("Pepperoni").buildMeasure(Topping.Measure.pc).buildPrice(1.25).buildIntQuantity(5).build();
        Topping a = new Topping.Builder().buildName("Mushrooms").buildMeasure(Topping.Measure.gr).buildPrice(0.35).buildDoubleQuantity(9.25).build();
        Topping b = ToppingService.getOnion();
        List<Topping> toppings = new ArrayList<>();
        toppings.add(t);
        toppings.add(a);
        toppings.add(b);

        Pizza pizza = new Pizza.Builder().buildName("Nichita").buildTopping(toppings).buildSize(25).build();
        Drink drink = new Drink.Builder().buildName("Sex on the beach").buildPrice(20).buildSize(500).buildWithAlcohol(true).build();

        List<Product> products = new ArrayList<>();
        products.add(pizza);
        products.add(drink);

        Order order = new Order.Builder().buildProducts(products).build();

        System.out.println(toppings);

        ToppingService.removeTopping(toppings);

        System.out.println(toppings);
        //System.out.println(order);
    }
}
