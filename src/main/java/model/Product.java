package model;

import java.util.List;

public class Product {
    protected StringBuilder name;
    protected List<Topping> toppings;
    protected double price;
    protected int size;

    public void removeTopping(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= toppings.size()) {
            throw new IndexOutOfBoundsException("Index out of range: " + index);
        }
        this.price = this.price - toppings.get(index).getPrice();
        toppings.remove(index);
    }

    public String listToppings() {
        if (toppings == null)
            return "\tYou have no added toppings\n";
        else {
            String toReturn = "";
            for(var topping : toppings) {
                toReturn = toReturn + "\t" + topping + "\n";
            }
            return toReturn;
        }
    }

    // Method to add a specific topping for a pizza
    public void addTopping(Topping newTopping) throws IllegalArgumentException {
        if (newTopping == null) {
            throw new IllegalArgumentException("Cannot add null topping");
        }
        toppings.add(newTopping);
        price = price + newTopping.getPrice();
        // Debug message
        System.out.println("Topping " + newTopping + " added successfully");
    }
    public StringBuilder getName() {
        return name;
    }

    public void setName(StringBuilder name) {
        this.name = name;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.price = (this.price - this.size/10) + (this.price + size/10);
        this.size = size;
    }
}