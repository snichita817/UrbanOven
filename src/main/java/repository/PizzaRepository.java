package repository;

import model.database.DatabaseConnection;
import model.product.Pizza;
import model.product.Topping;
import service.AuditService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PizzaRepository {

    public static void addPizza(Pizza pizza) {
        String sql = "INSERT IGNORE INTO pizzas VALUES (?, ?, ?, ?)";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, pizza.getId());
            statement.setString(2, pizza.getName().toString());
            statement.setDouble(3, pizza.getPrice());
            statement.setInt(4, pizza.getSize());
            statement.executeUpdate();
            AuditService.logAction(
                    "addPizza",
                    "INSERT",
                    "Added pizza with ID: " + pizza.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Topping> getToppingsForPizzaId(int pizzaId, int orderId) {
        String sql = "SELECT * FROM order_pizzas WHERE order_id = ? AND pizza_id = ?";
        List<Topping> pizzaToppings = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, pizzaId);

            try(ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    for(int i = 3; i<=columnCount; i++) {
                        // which is also conveniently topping id
                        String columnName = metaData.getColumnName(i);

                        double quantity = resultSet.getDouble(columnName);
                        if(quantity > 0) {
                            Topping predefined = ToppingRepository
                                    .getToppingById(columnName).orElse(null);
                            if (predefined != null) {
                                Topping t = new Topping.Builder()
                                        .buildName(predefined.getName().toString())
                                        .buildDoubleQuantity(quantity)
                                        .buildPrice(predefined.getPricePerUnit())
                                        .buildId(predefined.getId())
                                        .build();
                                pizzaToppings.add(t);
                            }

                        }

                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuditService.logAction(
                "getToppingsForPizzaId",
                "SELECT",
                "Got pizza toppings list of pizzaId " + pizzaId + " and orderId " + orderId);
        return pizzaToppings;
    }

    public static Optional<Pizza> getPizzaById(int pizza_id, int order_id) {
        String sql = "SELECT * FROM pizzas p WHERE p.pizza_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, pizza_id);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // We have at leas one record in the row

                int pizzaId = result.getInt("pizza_id");
                String name = result.getString("name");
                int size = getPizzaSize(pizza_id, order_id);
                List<Topping> toppings = getToppingsForPizzaId(pizza_id, order_id);

                AuditService.logAction(
                        "getPizzaById",
                        "SELECT",
                        "Got pizza from orderId " + order_id);

                return Optional.of(new Pizza.Builder()
                        .buildId(pizzaId)
                        .buildName(name)
                        .buildSize(size)
                        .buildTopping((ArrayList<Topping>) toppings)
                        .build());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static int getPizzaSize(int pizza_id, int order_id) {
        String sql = "SELECT size FROM order_pizzas WHERE pizza_id = ? AND order_id = ?";
        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, pizza_id);
            statement.setInt(2, order_id);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                AuditService.logAction(
                        "getPizzaSize",
                        "SELECT",
                        "Got pizza size for pizzaId " + pizza_id );
                return result.getInt("size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Returns the list of pizza with ingredients for the orderID
    public static Optional<List<Pizza>> getPizzasByOrderId(int orderId) {
        String sql = "SELECT * FROM order_pizzas op WHERE op.order_id = ?";
        List<Pizza> pizzas = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);

            // Going through all pizza orders with the corresponding order_id
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int pizzaId = result.getInt("pizza_id");

                    Pizza p = getPizzaById(pizzaId, orderId).orElse(null);

                    pizzas.add(p);
                }
            }
            AuditService.logAction(
                    "getPizzasByOrderId",
                    "SELECT",
                    "Got pizzas for orderId " + orderId );
            return Optional.of(pizzas);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        AuditService.logAction(
                "getPizzasByOrderId",
                "SELECT",
                "No pizzas found for orderID " + orderId );
        return Optional.empty();
    }


}
