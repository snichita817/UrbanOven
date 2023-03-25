package model;

public class Topping {
    public enum Measure {
        gr,                     // per gram
        pc
        // per piece
    }
    private StringBuilder name;
    private double pricePerUnit;
    private Measure unitOfMeasure;
    private double doubleQuantity;
    private int intQuantity;

    private Topping(Builder builder) {
        this.name = builder.name;
        this.pricePerUnit = builder.pricePerUnit;
        this.unitOfMeasure = builder.unitOfMeasure;
        this.doubleQuantity = builder.doubleQuantity;
        this.intQuantity = builder.intQuantity;
    }

    public StringBuilder getName() {
        return name;
    }

    public double getPrice(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
        return pricePerUnit * quantity;
    }

    public double getPrice(double quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be non-negative");
        }
        return pricePerUnit * quantity;
    }
    public double getPrice() {
        double quantity = doubleQuantity + intQuantity;
        return quantity*pricePerUnit;
    }
    @Override
    public String toString(){
        return name + " ("
                + (this.doubleQuantity + this.intQuantity) + (unitOfMeasure == Measure.gr ? " grams)" : " pieces)")
                + " +" + String.format("%.2f", getPrice()) + " lei.";
    }

    public static class Builder {
        private StringBuilder name;
        private double pricePerUnit;
        private Measure unitOfMeasure;
        private double doubleQuantity;
        private int intQuantity;

        public Builder buildName(String name) {
            this.name = new StringBuilder(name);
            return this;
        }
        public Builder buildPrice(double price) {
            this.pricePerUnit = price;
            return this;
        }

        public Builder buildMeasure(Measure measure) {
            this.unitOfMeasure = measure;
            return this;
        }
        public Builder buildDoubleQuantity(double doubleQuantity) {
            this.doubleQuantity = doubleQuantity;
            return this;
        }

        public Builder buildIntQuantity(int intQuantity) {
            this.intQuantity = intQuantity;
            return this;
        }

        public Topping build() {
            return new Topping(this);
        }
    }
}

