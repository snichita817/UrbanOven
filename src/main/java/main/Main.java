package main;
import model.*;
import service.DrinkService;
import service.PersonService;
import service.PizzaService;
import service.ToppingService;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Customer customer = PersonService.newPerson();
        System.out.println(customer);
    }
}
