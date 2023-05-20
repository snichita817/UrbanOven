package repository;

import main.Main;
import model.database.DatabaseConnection;
import model.person.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerRepository {
    public static void addCustomer(Customer customer) {

        String sql = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, customer.getUserName().toString());
            statement.setString(2, customer.getPassword().toString());
            statement.setString(3, customer.getFirstName().toString());
            statement.setString(4, customer.getLastName().toString());
            statement.setString(5, customer.getPhoneNumber().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customers c where c.customer_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // We have at least drinksone record in the row
                int customerId = result.getInt("customer_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String phoneNumber = result.getString("phoneNumber");
                return Optional.of(new Customer.Builder()
                        .buildId(id)
                        .buildUserName(username)
                        .buildPassword(password)
                        .buildFirstName(firstName)
                        .buildLastName(lastName)
                        .buildPhoneNumber(phoneNumber)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
