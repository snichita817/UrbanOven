package model;

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
        this.allOrders = new ArrayList<>();
    }

    public void addPeople(Person people) {
        this.people.add(people);
    }

}
