package model;
import java.util.ArrayList;
import java.util.List;

public class Pizza extends Product {

    private Pizza(Builder builder) {
        this.name = builder.name;
        this.toppings = builder.toppings;
        this.price = builder.price;
        this.size = builder.size;
    }

    @Override
    public String toString() {
        String toReturn = "Pizza ";
        toReturn = toReturn + name + "\n";
        toReturn = toReturn + "Toppings:\n";
        toReturn = toReturn + listToppings();
        toReturn = toReturn + "Pizza size: " + size + " cm.\n";
        toReturn = toReturn + "Pizza price: " + String.format("%.2f", price) + " lei.\n";
        return toReturn;
    }

    public static class Builder {
        private StringBuilder name;
        private List<Topping> toppings;
        private double price;
        private int size;
        public Builder buildName(String name) {
            this.name = new StringBuilder(name);
            return this;
        }
        public Builder buildTopping(ArrayList<Topping> toppings) {
            this.toppings = toppings;
            return this;
        }
        public Builder buildSize(int size) {
            this.size = size;
            return this;
        }

        private Builder buildPrice() {
            this.price = 5;
            for(Topping topping : toppings) {
                this.price = this.price + topping.getPrice();
            }
            return this;
        }

        public Pizza build() {
            buildPrice();
            return new Pizza(this);
        }
    }

}
