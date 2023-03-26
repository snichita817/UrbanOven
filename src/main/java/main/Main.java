package main;
import model.*;
import service.DrinkService;
import service.PizzaService;
import service.ToppingService;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        /*Product pizza1 = PizzaService.getPizza();
        PizzaService.modifyPizza((Pizza)pizza1);*/
        //System.out.println(order);

        Product drink = DrinkService.getDrink();
        DrinkService.modifyDrink((Drink) drink);

        System.out.println(drink);
    }
}
