package model.product;
import java.util.ArrayList;
import java.util.List;

public class Pizza extends Product {

    private Pizza(Builder builder) {
        this.name = builder.name;
        this.toppings = builder.toppings;
        this.price = builder.price;
        this.size = builder.size;
        this.id = builder.id;
    }
    @Override
    public String toString() {
        String toReturn = "Pizza ";
        toReturn = toReturn + name + "\n";
        toReturn = toReturn + "Toppings:\n";
        toReturn = toReturn + listToppings();

        if(this.size!=0)
        {
            toReturn = toReturn + "Pizza size: " + size + " slices.\n";
        }
        else {
            toReturn = toReturn + "Pizza size: 6/8/10 slices.\n";
        }
        toReturn = toReturn + "Pizza price: " + String.format("%.2f", price) + " lei.\n";
        return toReturn;
    }

    public static class Builder {
        private int id;
        private StringBuilder name;
        private List<Topping> toppings;
        private double price;
        private int size;

        public Builder buildId(int id) {
            this.id = id;
            return this;
        }

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

        public Builder buildPrice(Double price) {
            this.price = price;
            return this;
        }

        private Builder buildPrice() {
            this.price = 5 + (double) this.size /2;
            for(Topping topping : toppings) {
                this.price = this.price + topping.getPrice();
            }
            return this;
        }

        public Pizza build() {
            if(this.toppings != null) {
                buildPrice();
            }
            return new Pizza(this);
        }
    }

}
