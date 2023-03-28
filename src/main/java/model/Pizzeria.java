package model;

import service.DrinkService;
import service.PizzaService;
import service.PizzeriaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Pizzeria {
    private List<Person> people = new Vector<>();
    private List<Product> products;
    private List<Order> allOrders;

    public Pizzeria() {
        this.people = new Vector<>();
        this.products = new ArrayList<>();
        products.addAll(PizzaService.getPizzas(true));
        products.addAll(DrinkService.getDrinks(true));
        this.allOrders = new ArrayList<>();
    }

    public void addPeople(Person people) {
        this.people.add(people);
    }

    public List<Person> getPeople() {
        return this.people;
    }

}
