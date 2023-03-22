package model;

public class Topping {
    public enum Measure {
        gr,                     // per gram
        pc
        // per piece
    }
    private final StringBuilder name;
    private final double pricePerUnit;
    private final Measure unitOfMeasure;

    private Topping(Builder builder) {
        this.name = builder.name;
        this.pricePerUnit = builder.pricePerUnit;
        this.unitOfMeasure = builder.unitOfMeasure;
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

    @Override
    public String toString(){
        return name + " (" + pricePerUnit + " per " + (unitOfMeasure == Measure.gr ? "gram)" : "piece)");
    }

    public static class Builder {
        private StringBuilder name;
        private double pricePerUnit;
        private Measure unitOfMeasure;

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

        public Topping build() {
            return new Topping(this);
        }
    }
}

