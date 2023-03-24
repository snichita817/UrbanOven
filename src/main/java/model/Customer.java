package model;

import java.util.List;

public class Customer extends Person{
    private List<Order> orderHistory;

    public void addOrder(Order order) {
        this.orderHistory.add(order);
        System.out.println("Order saved to the order history");
    }

    private Customer (Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.orderHistory = builder.orderHistory;
    }

    public static class Builder {
        private StringBuilder firstName;
        private StringBuilder lastName;
        private StringBuilder address;
        private StringBuilder phoneNumber;
        private List<Order> orderHistory;

        public Builder buildFirstName(String firstName) {
            this.firstName = new StringBuilder(firstName);
            return this;
        }

        public Builder buildLastName(String lastName) {
            this.lastName = new StringBuilder(lastName);
            return this;
        }
        public Builder buildAddress(String address) {
            this.address = new StringBuilder(address);
            return this;
        }
        public Builder buildPhoneNumber (String phoneNumber) {
            this.phoneNumber = new StringBuilder(phoneNumber);
            return this;
        }
        public Builder buildOrderHistory(List<Order> orderHistory) {
            this.orderHistory = orderHistory;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }

    }


}
