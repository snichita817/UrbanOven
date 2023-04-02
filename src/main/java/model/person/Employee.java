package model.person;

public class Employee extends Person{
    private int salary;
    private StringBuilder ranking;

    private Employee(Builder builder) {
        this.password = builder.password;
        this.userName = builder.userName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.salary = builder.salary;
        this.ranking = builder.ranking;
    }



    public static class Builder{
        private StringBuilder password;
        private StringBuilder userName;
        private StringBuilder firstName;
        private StringBuilder lastName;
        private StringBuilder address;
        private StringBuilder phoneNumber;
        public int salary;
        public StringBuilder ranking;

        public Builder buildUserName(String userName) {
            this.userName = new StringBuilder(userName);
            return this;
        }

        public Builder buildPassword(String password) {
            this.password = new StringBuilder(password);
            return this;
        }

        public Builder buildFirstName(String firstName) {
            this.firstName = new StringBuilder(firstName);
            return this;
        }
        public Builder buildSalary(int salary) {
            this.salary = salary;
            return this;
        }
        public Builder buildRanking(String ranking) {
            this.ranking = new StringBuilder(ranking);
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
        public Employee build() {
            return new Employee(this);
        }

    }
    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public StringBuilder getRanking() {
        return ranking;
    }

    public void setRanking(StringBuilder ranking) {
        this.ranking = ranking;
    }
}
