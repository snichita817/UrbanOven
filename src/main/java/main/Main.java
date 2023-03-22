package main;
import model.Topping;
public class Main {

    public static void main(String[] args) {
        Topping t = new Topping.Builder().buildName("pepperoni").buildMeasure(Topping.Measure.gr).buildPrice(12).build();
        System.out.println(t);
    }
}
