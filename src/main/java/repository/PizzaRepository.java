package repository;

import model.database.DatabaseConnection;
import model.product.Pizza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public Optional<Pizza> getPizzaById(int id) {
        String sql = "SELECT * FROM pizzas p WHERE p.pizza_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // We have at leas one record in the row

                int pizzaId = result.getInt("pizza_id");
                String name = result.getString("name");
                Double price = result.getDouble("price");
                int size = result.getInt("size");

                return Optional.of(new Pizza.Builder()
                        .buildId(pizzaId)
                        .buildName(name)
                        .buildSize(size)
                        .build());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


    public static List<Pizza> getAllPizzas() {
        List<Pizza> pizzas = new ArrayList<>();

        String sql = "SELECT * FROM pizzas";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int pizzaId = result.getInt("pizza_id");
                String name = result.getString("name");
                Double price = result.getDouble("price");
                int size = result.getInt("size");

                pizzas.add(new Pizza.Builder()
                        .buildId(pizzaId)
                        .buildName(name)
                        .buildPrice(price)
                        .buildSize(size)
                        .build());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pizzas;
    }

}
