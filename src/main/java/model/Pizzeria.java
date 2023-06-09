package model;

import model.person.Person;
import model.product.Product;
import service.DrinkService;
import service.PizzaService;

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

    public void addOrder(Order order) {
        allOrders.add(order);
    }
    public List<Order> getAllOrders() {
        return allOrders;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public void addPeople(Person people) {
        this.people.add(people);
    }

    public List<Person> getPeople() {
        return this.people;
    }

}
