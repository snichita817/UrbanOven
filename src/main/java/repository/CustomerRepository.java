package repository;

import main.Main;
import model.database.DatabaseConnection;
import model.person.Customer;
import service.AuditService;

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

            if(sql.startsWith("UPDATE")) {
                AuditService.logAction("editCustomer", "UPDATE", "Customer updated successfully with customerID: " + customer.getId());
            }
            else {
                AuditService.logAction("addCustomer", "INSERT", "Customer added successfully with customerID b: " + customer.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction("executeCustomerUpdate", "INSERT/UPDATE", "An exception occurred: " + e.getMessage());
        }
    }

    public static boolean uniqueUsername(String username) {
        String sql = "SELECT COUNT(*) FROM customers c WHERE c.username = ?";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) {
                    int count = resultSet.getInt(1);
                    AuditService.logAction("uniqueUsername", "SELECT", "Unique username check successful");
                    return count == 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction("uniqueUsername", "SELECT", "An exception occurred: " + e.getMessage());
        }
        return false;
    }

    public static void deleteCustomer(int customerId) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, customerId);
            statement.executeUpdate();
            AuditService.logAction("deleteCustomer", "DELETE", "Deleted customer with ID " + customerId);
        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction("deleteCustomer", "DELETE", "An exception occurred: " + e.getMessage());
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
                        AuditService.logAction(
                                "getCustomerByNameAndPassword",
                                "SELECT",
                                "Retrieved customer with ID " + customerId);
                        return Optional.of(customer);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction("getCustomerByNameAndPassword", "SELECT", "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getCustomerByNameAndPassword",
                "SELECT",
                "Customer not found with user " + username);
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
                AuditService.logAction(
                        "getCustomerById",
                        "SELECT",
                        "Retrieved customer with ID " + customerId);
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
            AuditService.logAction(
                    "getCustomerById",
                    "SELECT",
                    "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getCustomerById",
                "SELECT",
                "Customer not found with ID " + id);
        return Optional.empty();
    }

    public static Optional<Integer> getCustomerIdByUsername(String username) {
        String sql = "SELECT customer_id FROM customers c WHERE c.username = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);

            try(ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    // move cursor
                    int customerId = result.getInt(1);
                    AuditService.logAction(
                            "getCustomerIdByUsername",
                            "SELECT",
                            "Retrieved customer ID with username " + username);
                    return Optional.of(customerId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "getCustomerIdByUsername",
                    "SELECT",
                    "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getCustomerIdByUsername",
                "SELECT",
                "Customer with username " + username + " not found");
        return Optional.empty();
    }
}
