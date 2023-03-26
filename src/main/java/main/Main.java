package main;
import model.*;
import service.PizzaService;
import service.ToppingService;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {


        Product pizza1 = PizzaService.getPizza();
        PizzaService.modifyPizza((Pizza)pizza1);
        //System.out.println(order);
    }
}
