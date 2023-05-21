package model.person;

public class Person {
    protected int id;
    protected StringBuilder firstName;
    protected StringBuilder userName;
    protected StringBuilder lastName;
    protected StringBuilder address;
    protected StringBuilder phoneNumber;
    protected StringBuilder password;
    @Override
    public String toString() {
        String toReturn = "INFO:\n";
        toReturn = toReturn + "First name: " + this.firstName + ";\n";
        toReturn = toReturn + "Last name: " + this.lastName + ";\n";
        toReturn = toReturn + "Address: " + this.address + ";\n";
        toReturn = toReturn + "Telephone Number: " + this.phoneNumber + ";\n";
        return toReturn;
    }

    public void setUserName(String userName) {
        this.userName = new StringBuilder(userName);
    }

    public void setPassword(String password) {
        this.password = new StringBuilder(password);
    }

    public int getId() {
        return id;
    }

    public StringBuilder getUserName() {
        return userName;
    }

    public StringBuilder getPassword() {
        return password;
    }

    public StringBuilder getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = new StringBuilder(firstName);
    }

    public StringBuilder getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = new StringBuilder(lastName);
    }

    public StringBuilder getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = new StringBuilder(address);
    }

    public StringBuilder getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = new StringBuilder(phoneNumber);
    }
}
