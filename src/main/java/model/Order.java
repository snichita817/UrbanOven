package model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {
    private static int orderNumber = 0;

    private final int orderId;
    private final Customer customer;
    private List<Product> products;
    private double totalCost;
    private StringBuilder orderStatus;
    private Date orderTime;
    private Date expectedTime;
    private Order (Builder builder) {
        this.orderId = ++orderNumber;
        this.customer = builder.customer;
        this.products = builder.products;
        this.totalCost = builder.totalCost;
        this.orderStatus = builder.orderStatus;
        this.orderTime = builder.orderTime;
        this.expectedTime = builder.expectedTime;
    }

    @Override
    public String toString() {
        String toReturn = "Order #" + this.orderId + "\n";
        toReturn = toReturn + "Order status: " + this.orderStatus + "\n\n";
        // print out pizzas first
        for(Product product : products) {
            if(product instanceof Pizza) {
                toReturn = toReturn + product + "\n";
            }
        }

        for(Product product : products) {
            if(product instanceof Drink) {
                toReturn = toReturn + product + "\n";
            }
        }

        toReturn = toReturn + "Total cost: " + this.totalCost + "\n";
        toReturn = toReturn + "Ordered at: " + this.orderTime + "\n";
        toReturn = toReturn + "Expect your delivery at: " + this.expectedTime + "\n";
        return  toReturn;
    }

    public static class Builder {
        private Customer customer;
        private List<Product> products;
        private double totalCost;
        private StringBuilder orderStatus = new StringBuilder("Pending");
        private Date orderTime = new Date();
        private Date expectedTime;



        public Builder buildCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }
        public Builder buildProducts(List<Product> products) {
            this.products = products;
            return this;
        }
        public Builder buildTotalCost() {
            this.totalCost = 0.0;
            for(Product product : products) {
                this.totalCost = this.totalCost + product.getPrice();
            }
            return this;
        }

        public Order build() {
            buildTotalCost();
            if(orderTime == null) {
                orderTime = new Date();
            }
            if(expectedTime == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(orderTime);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
                expectedTime = calendar.getTime();
            }
            return new Order(this);
        }
    }
}
