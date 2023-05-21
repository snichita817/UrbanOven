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
        String sql = "INSERT INTO customers VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        executeCustomerUpdate(sql, customer);
    }

    public static void editCustomer(Customer customer) {
        String sql = "UPDATE customers SET username = ?, password = ?, firstName = ?, lastName = ?, address = ?, phoneNumber = ? WHERE customer_id = ?";
        executeCustomerUpdate(sql, customer);
    }

    private static void executeCustomerUpdate(String sql, Customer customer) {
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, customer.getUserName().toString());
            statement.setString(2, customer.getPassword().toString());
            statement.setString(3, customer.getFirstName().toString());
            statement.setString(4, customer.getLastName().toString());
            statement.setString(6, customer.getPhoneNumber().toString());
            statement.setString(5, customer.getAddress().toString());
            if (sql.startsWith("UPDATE")) {
                statement.setInt(7, customer.getId());
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean uniqueUsername(String username) {
        String sql = "SELECT COUNT(*) FROM customers c WHERE c.username = ?";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Customer> getCustomerByNameAndPassword(String username, String password) {
        String sql = "SELECT * FROM customers c WHERE c.username = ? AND c.password = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int customerId = resultSet.getInt("customer_id");

                    Customer customer = getCustomerById(customerId).orElse(null);
                    if (customer != null) {
                        return Optional.of(customer);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public static Optional<Customer> getCustomerById(int id) {
        String sql = "SELECT * FROM customers c where c.customer_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                // We have at least one record in the row
                int customerId = result.getInt("customer_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String phoneNumber = result.getString("phoneNumber");
                String address = result.getString("address");
                return Optional.of(new Customer.Builder()
                        .buildId(id)
                        .buildUserName(username)
                        .buildPassword(password)
                        .buildFirstName(firstName)
                        .buildLastName(lastName)
                        .buildPhoneNumber(phoneNumber)
                        .buildAddress(address)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
