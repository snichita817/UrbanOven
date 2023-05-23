package repository;

import model.database.DatabaseConnection;
import model.person.Customer;
import model.person.Employee;
import service.AuditService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class EmployeeRepository {
    public static Optional<Employee> getEmployeeById(int id) {
        String sql = "SELECT * FROM employees c where c.employee_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                // We have at least one record in the row
                int employeeId = result.getInt("employee_id");
                String username = result.getString("username");
                String password = result.getString("password");
                String firstName = result.getString("firstName");
                String lastName = result.getString("lastName");
                String phoneNumber = result.getString("phoneNumber");
                String ranking = result.getString("ranking");
                double salary = result.getDouble("salary");
                AuditService.logAction(
                        "getEmployeeUd",
                        "SELECT",
                        "Retrieved employee with ID: " + id);
                return Optional.of(new Employee.Builder()
                        .buildId(employeeId)
                        .buildSalary(salary)
                        .buildRanking(ranking)
                        .buildUserName(username)
                        .buildPassword(password)
                        .buildFirstName(firstName)
                        .buildLastName(lastName)
                        .buildPhoneNumber(phoneNumber)
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "getEmployeeById",
                    "SQLException",
                    "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getEmployeeId",
                "SELECT",
                "Employee with ID " + id + " not found!");
        return Optional.empty();
    }

    public static Optional<Employee> getEmployeeByNameAndPassword(String username, String password) {
        String sql = "SELECT * FROM employees e WHERE e.username = ? AND e.password = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);

            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int employeeId = resultSet.getInt("employee_id");

                    Employee employee = getEmployeeById(employeeId).orElse(null);
                    if (employee != null) {
                        AuditService.logAction(
                                "getEmployeeByNameAndPassword",
                                "SELECT",
                                "Retrieved employee with username " + username);
                        return Optional.of(employee);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "getEmployeeByNameAndPassword",
                    "SQLException",
                    "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getEmployeeByNameAndPassword",
                "SELECT",
                "Employee with username " + username + " not found!");
        return Optional.empty();
    }

    public static void addEmployee(Customer customer, double salary, String ranking) {
        String sql = "INSERT INTO employees VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";
        CustomerRepository.deleteCustomer(customer.getId());
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, customer.getUserName().toString());
            statement.setString(2, customer.getPassword().toString());
            statement.setString(3, customer.getFirstName().toString());
            statement.setString(4, customer.getLastName().toString());
            statement.setDouble(5, salary);
            statement.setString(6, ranking);
            statement.setString(7, customer.getPhoneNumber().toString());

            statement.executeUpdate();
            AuditService.logAction(
                    "addEmployee",
                    "INSERT",
                    "Added new employee: " + customer.getUserName());
        } catch (SQLException e) {
            AuditService.logAction(
                    "addEmployee",
                    "INSERT",
                    "Failed to add new employee: " + customer.getUserName());
            e.printStackTrace();
        }
    }

    public static Optional<Integer> getEmployeeIdByUsername(String username) {
        String sql = "SELECT employee_id FROM employees e WHERE e.username = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, username);

            try(ResultSet result = statement.executeQuery()) {
                if(result.next()) {
                    // move cursor
                    int employeeId = result.getInt(1);
                    AuditService.logAction(
                            "getEmployeeIdByUsername",
                            "SELECT",
                            "Retrieved employee ID with username " + username);
                    return Optional.of(employeeId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "getEmployeeIdByUsername",
                    "SQLException",
                    "An exception occurred: " + e.getMessage());
        }
        AuditService.logAction(
                "getEmployeeIdByUsername",
                "SELECT",
                "Employee with username " + username + " not found");
        return Optional.empty();
    }

    public static void editEmployee(Employee employee) {
        String sql = "UPDATE employees " +
                "SET username = ?, password = ?, firstName = ?, " +
                "lastName = ?, salary = ?, ranking = ?, phoneNumber = ? " +
                "WHERE employee_id = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {

            statement.setString(1, employee.getUserName().toString());
            statement.setString(2, employee.getPassword().toString());
            statement.setString(3, employee.getFirstName().toString());
            statement.setString(4, employee.getLastName().toString());
            statement.setDouble(5, employee.getSalary());
            statement.setString(6, employee.getRanking().toString());
            statement.setString(7, employee.getPhoneNumber().toString());

            statement.setInt(8, employee.getEmployee_id());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "editEmployee",
                    "SQLException",
                    "An exception occurred: " + e.getMessage());
        }
    }

    public static void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, employeeId);
            statement.executeUpdate();
            AuditService.logAction("deleteEmployee", "DELETE", "Deleted employee with ID " + employeeId);
        } catch (SQLException e) {
            e.printStackTrace();
            AuditService.logAction(
                    "deleteEmployee",
                    "SQLException",
                    "An exception occurred: " + e.getMessage());
        }
    }
}