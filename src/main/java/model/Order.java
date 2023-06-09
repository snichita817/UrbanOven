package model;

import model.person.Customer;
import model.product.Drink;
import model.product.Pizza;
import model.product.Product;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {
//    private static int orderNumber = 0;

    private final int orderId;
    private final Customer customer;
    private List<Product> products;
    private double totalCost;
    private StringBuilder orderStatus;
    private Date orderTime;
    private Date expectedTime;
    public double getTotalCost() {
        return totalCost;
    }

    public StringBuilder getOrderStatus() {
        return orderStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public Date getExpectedTime() {
        return expectedTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    private Order (Builder builder) {
        this.orderId = builder.orderId;
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
                toReturn = toReturn + ((Pizza) product).toString() + "\n";
            }
        }

        for(Product product : products) {
            if(product instanceof Drink) {
                toReturn = toReturn + ((Drink) product ).toString()+ "\n";
            }
        }

        toReturn = toReturn + "Total cost: " + String.format("%.2f", this.totalCost) + "\n";
        toReturn = toReturn + "Ordered at: " + this.orderTime + "\n";
        toReturn = toReturn + "Expect your delivery at: " + this.expectedTime + "\n";
        return  toReturn;
    }

    public static class Builder {
        private int orderId;
        private Customer customer;
        private List<Product> products;
        private double totalCost = 0.0;
        private StringBuilder orderStatus = new StringBuilder("Pending");
        private Date orderTime = new Date();
        private Date expectedTime;
        public Builder buildOrderId(int orderId) {
            this.orderId = orderId;
            return this;
        }
        public Builder buildOrderStatus(String orderStatus)
        {
            this.orderStatus = new StringBuilder(orderStatus);
            return this;
        }
        public Builder buildOrderTime(Date orderTime) {
            this.orderTime = orderTime;
            return this;
        }

        public Builder buildExpectedTime(Date expectedTime) {
            this.expectedTime = expectedTime;
            return this;
        }

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
        public Builder buildTotalCost(double totalCost) {
            this.totalCost = totalCost;
            return this;
        }
        public Order build() {
            if(this.totalCost == 0.0) {
                buildTotalCost();
            }

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
