package repository;

import model.Order;
import model.database.DatabaseConnection;
import model.person.Customer;
import model.product.Drink;
import model.product.Pizza;
import model.product.Product;
import model.product.Topping;
import service.AuditService;

import java.sql.*;
import java.util.*;

public class OrderRepository {
    public static void addOrder(Order order) {
        String sql = "INSERT INTO orders VALUES (NULL, ?, ?, ?, ?, ?)";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            System.out.println(order.getCustomer().getId());
//            statement.setInt(1, order.getOrderId());
            statement.setInt(1, order.getCustomer().getId());
            statement.setDouble(2, order.getTotalCost());
            statement.setString(3, order.getOrderStatus().toString());

            java.util.Date orderTime = order.getOrderTime();
            java.util.Date expectedTime = order.getExpectedTime();

            // Convert to java.sql.Date
            java.sql.Timestamp orderSqlDate = new java.sql.Timestamp(orderTime.getTime());
            java.sql.Timestamp expectedSqlDate = new java.sql.Timestamp(expectedTime.getTime());

            // Set the converted dates in the prepared statement
            statement.setTimestamp(4, orderSqlDate);
            statement.setTimestamp(5, expectedSqlDate);
            statement.executeUpdate();

            List<Product> orderProducts = order.getProducts();

            // Retrieve the generated order ID
            try( ResultSet generatedKeys = statement.getGeneratedKeys()  ) {
                if(generatedKeys.next())
                {
                    // Insert products to the associative entity
                    for(Product product : orderProducts) {
                        if (product instanceof Pizza) {
                            List<Topping> pizzaToppings = product.getToppings();
                            String sqlPizza = "INSERT INTO order_pizzas VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement statement1 = DatabaseConnection.getConnection().prepareStatement(sqlPizza)) {
                                statement1.setInt(1, generatedKeys.getInt(1));
                                statement1.setInt(2, product.getId());

                                // Create a Hashtable to store columnId and quantity
                                Hashtable<Integer, Double> toppingsHashtable = new Hashtable<Integer, Double>();

                                // Init the Hashtable with default values
                                for(int columnId = 1; columnId <= 10; columnId++) {
                                    toppingsHashtable.put(columnId, 0.0);
                                }

                                for (Topping topping : pizzaToppings) {
                                    // Extract the id of the product
                                    // That will be the columnId = productId + 2
                                    int columnId = Integer.parseInt(topping.getId().substring(1));
                                    double quantity = topping.getDoubleQuantity();
                                    toppingsHashtable.put(columnId, quantity);
                                }
                                // Set the values from the Hashtable in the PreparedStatement
                                for (int columnId : toppingsHashtable.keySet()) {

                                    double quantity = toppingsHashtable.get(columnId);
                                    statement1.setDouble(columnId + 2 , quantity);
                                }
                                statement1.setInt(13, product.getSize());
                                statement1.executeUpdate();
                            }
                        }
                        else if (product instanceof Drink) {
                            String sqlDrink = "INSERT INTO order_drinks VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                            List<Topping> drinkToppings = product.getToppings();
                            try (PreparedStatement statement1 = DatabaseConnection.getConnection().prepareStatement(sqlDrink)) {
                                statement1.setInt(1, generatedKeys.getInt(1));
                                statement1.setInt(2, product.getId());

                                // Create a Hashtable to store columnId and quantity
                                Hashtable<Integer, Integer> toppingsHashtable = new Hashtable<Integer, Integer>();

                                // Init the Hashtable with default values
                                for(int columnId = 1; columnId <= 6; columnId++) {
                                    toppingsHashtable.put(columnId, 0);
                                }

                                for (Topping topping : drinkToppings) {
                                    // Extract the id of the product
                                    // That will be the columnId = productId + 2
                                    int columnId = Integer.parseInt(topping.getId().substring(1));
                                    int quantity = topping.getIntQuantity();
                                    toppingsHashtable.put(columnId, quantity);
                                }
                                // Set the values from the Hashtable in the PreparedStatement
                                for (int columnId : toppingsHashtable.keySet()) {

                                    int quantity = toppingsHashtable.get(columnId);
                                    statement1.setInt(columnId + 2 , quantity);
                                }
                                statement1.setInt(9, product.getSize());
                                statement1.executeUpdate();
                                AuditService.logAction(
                                        "addOrder",
                                        "INSERT",
                                        "Added order with ID: " + order.getOrderId());
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<List<Order>> getOrdersById(int orderId) {
        String sql = "SELECT * FROM orders o WHERE o.order_id = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Order order = buildOrderFromResultSet(result, orderId);
                    orders.add(order);
                }
            }
            AuditService.logAction(
                    "getOrdersById",
                    "SELECT",
                    "Retrieved list of orders with orderID: " + orderId);
            return Optional.of(orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<List<Order>> getOrdersByUserId(int customerId) {
        String sql = "SELECT * FROM orders o WHERE o.customer_id = ?";
        List<Order> userOrder = new ArrayList<>();
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, customerId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int orderId = result.getInt("order_id");
                    Order order = buildOrderFromResultSet(result, orderId);
                    userOrder.add(order);
                }
            }
            AuditService.logAction(
                    "getOrdersByUserId",
                    "SELECT",
                    "Retrieved list of orders of customer with customerID: " + customerId);
            return Optional.of(userOrder);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static Optional<List<Order>> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orderList = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            try(ResultSet resultSet = statement.executeQuery()) {
                while(resultSet.next()) {
                    int orderId = resultSet.getInt("order_id");
                    Order order = buildOrderFromResultSet(resultSet, orderId);
                    orderList.add(order);
                }
            }
            AuditService.logAction(
                    "getOrdersByUserId",
                    "SELECT",
                    "Retrieved list of all orders");
            return Optional.of(orderList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static Order buildOrderFromResultSet(ResultSet result, int orderId) throws SQLException {
        int customerId = result.getInt("customer_id");
        Customer customer = CustomerRepository.getCustomerById(customerId).orElse(null);
        double totalCost = result.getDouble("totalCost");
        String orderStatus = result.getString("orderStatus");
        java.util.Date orderTime = result.getTimestamp("orderTime");
        java.util.Date expectedTime = result.getTimestamp("expectedTime");

        List<Product> products = new ArrayList<>(); // Create a list of products

        // Retrieve the pizzas for the order
        Optional<List<Pizza>> pizzas = PizzaRepository.getPizzasByOrderId(orderId);
        if (pizzas.isPresent()) {
            products.addAll(pizzas.get()); // Add all pizzas to the products list
        }

        // Retrieve the drinks for the order
        Optional<List<Drink>> drinks = DrinkRepository.getDrinksByOrderId(orderId);
        if (drinks.isPresent()) {
            products.addAll(drinks.get());
        }

        return new Order.Builder()
                .buildOrderId(orderId)
                .buildCustomer(customer)
                .buildProducts(products)
                .buildTotalCost(totalCost)
                .buildExpectedTime(expectedTime)
                .buildOrderTime(orderTime)
                .buildOrderStatus(orderStatus)
                .build();
    }

    public static Optional<List<Order>> getOrdersBetweenDates(Calendar startCal, Calendar endCal) {
        startCal.add(Calendar.DAY_OF_MONTH, -1);

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(
                "SELECT * FROM orders WHERE orderTime >= ? AND orderTime <= ?")) {
            java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startCal.getTimeInMillis());
            java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endCal.getTimeInMillis());
            statement.setTimestamp(1, startTimestamp);
            statement.setTimestamp(2, endTimestamp);

            List<Order> ordersBetweenDates = new ArrayList<>();
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int orderId = result.getInt("order_id");
                    Optional<List<Order>> orders = getOrdersById(orderId);
                    if (orders.isPresent()) {
                        ordersBetweenDates.addAll(orders.get());
                    }
                }
            }
            AuditService.logAction(
                    "getOrdersBetweenDates",
                    "SELECT",
                    "Retrieved list of all orders between dates " + startCal + " and " + endCal);
            return Optional.of(ordersBetweenDates);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET orderStatus = ? WHERE order_id = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, orderId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                AuditService.logAction(
                        "updateOrderStatusToDelivered",
                        "UPDATE",
                        "Updated order status to " + status + " for order ID: " + orderId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuditService.logAction(
                "updateOrderStatusToDelivered",
                "UPDATE",
                "Failed to update order status to " + status + " for order ID: " + orderId);
        return false;
    }
}
