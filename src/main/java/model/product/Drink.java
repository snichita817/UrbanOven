package model.product;

import java.util.List;

public class Drink extends Product{
    private boolean withAlcohol;

    @Override
    public String toString()
    {
        String toReturn = "" + this.name;
        if(withAlcohol == true) {
            toReturn = toReturn + " (ALCOHOLIC BEVERAGE)";
        }
        toReturn = toReturn + "\nAdded toppings:\n";
        toReturn = toReturn + listToppings();
        if(this.size != 0)
        {
            toReturn = toReturn + "Drink size: " + this.size + "mL.\n";
        }
        else {
            toReturn = toReturn + "Drink size: 250/350/500 mL.\n";
        }
        toReturn = toReturn + "Drink price: " + this.price + " lei.\n";
        return toReturn;
    }

    public boolean isWithAlcohol() {
        return withAlcohol;
    }

    private Drink(Builder builder) {
        this.id= builder.id;
        this.name = builder.name;
        this.toppings = builder.toppings;
        this.price = builder.price;
        this.size = builder.size;
        this.withAlcohol = builder.withAlcohol;
    }
    public static class Builder {
        private int id;
        private StringBuilder name;
        private List<Topping> toppings;
        private double price;
        private int size;
        private boolean withAlcohol;

        public Builder buildWithAlcohol(boolean withAlcohol) {
            this.withAlcohol = withAlcohol;
            return this;
        }
        public Builder buildId(int id) {
            this.id = id;
            return this;
        }
        public Builder buildName(String name) {
            this.name = new StringBuilder(name);
            return this;
        }
        public Builder buildTopping(List<Topping> toppings) {
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

        public Builder buildPrice() {
            int priceForAlcohol = this.withAlcohol ? 5 : 0;
            this.price = 5 + (double) this.size /10 + priceForAlcohol;
            if(toppings!= null) {
                for(Topping topping : toppings) {
                    this.price = this.price + topping.getPrice();
                }
            }
            return this;
        }

        public Drink build() {
            buildPrice();
            return new Drink(this);
        }
    }
}
