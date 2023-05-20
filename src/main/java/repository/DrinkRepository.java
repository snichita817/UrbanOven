package repository;

import model.database.DatabaseConnection;
import model.product.Drink;
import model.product.Pizza;
import model.product.Topping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DrinkRepository {
    public static void addDrink(Drink drink) {
        String sql = "INSERT IGNORE INTO drinks VALUES (?, ?, ?, ?, ?)";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, drink.getId());
            statement.setString(2, drink.getName().toString());
            statement.setDouble(3, drink.getPrice());
            statement.setInt(4, drink.getSize());
            statement.setBoolean(5, drink.isWithAlcohol());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Topping> getToppingsForDrinkId(int drinkId, int orderId) {
        String sql = "SELECT * FROM order_drinks WHERE order_id = ? AND drink_id = ?";
        List<Topping> drinkToppings = new ArrayList<>();

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);
            statement.setInt(2, drinkId);

            try(ResultSet resultSet = statement.executeQuery()) {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    for(int i = 3; i<=columnCount; i++) {
                        String columnName = metaData.getColumnName(i);

                        int quantity = resultSet.getInt(columnName);
                        if(quantity > 0) {
                            Topping predefined = ToppingRepository
                                    .getToppingById(columnName).orElse(null);
                            if (predefined != null) {
                                Topping t = new Topping.Builder()
                                        .buildName(predefined.getName().toString())
                                        .buildIntQuantity(quantity)
                                        .buildPrice(predefined.getPricePerUnit())
                                        .buildId(predefined.getId())
                                        .build();
                                drinkToppings.add(t);
                            }

                        }

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinkToppings;
    }

    public static Optional<Drink> getDrinkById(int drink_id, int order_id) {
        String sql = "SELECT * FROM drinks d where d.drink_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, drink_id);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // We have at least one record in the row
                int drinkId = result.getInt("drink_id");
                String name = result.getString("name");
                int size = getDrinkSize(drinkId, order_id);
                List<Topping> toppings = getToppingsForDrinkId(drinkId, order_id);
                boolean withAlcohol = result.getBoolean("withAlcohol");
                return Optional.of(new Drink.Builder()
                        .buildId(drinkId)
                        .buildName(name)
                        .buildSize(size)
                        .buildWithAlcohol(withAlcohol)
                        .buildTopping(toppings)
                        .build());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static int getDrinkSize(int drink_id, int order_id) {
        String sql = "SELECT size FROM order_drinks WHERE drink_id = ? AND order_id = ?";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, drink_id);
            statement.setInt(2, order_id);
            ResultSet result = statement.executeQuery();

            if(result.next()) {
                return result.getInt("size");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Optional<List<Drink>> getDrinksByOrderId(int orderId) {
        String sql = "SELECT * FROM order_drinks od WHERE od.order_id = ?";
        List<Drink> drinks = new ArrayList<>();
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, orderId);

            // Going through all pizza orders with the corresponding order_id
            try(ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    int drinkId = result.getInt("drink_id");

                    Drink d = getDrinkById(drinkId, orderId).orElse(null);

                    drinks.add(d);
                }
            }
            return Optional.of(drinks);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static List<Drink> getAllDrinks() {
        List<Drink> drinks = new ArrayList<>();

        String sql = "SELECT * FROM drinks";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                int drinkId = result.getInt("drink_id");
                String name = result.getString("name");
                Double price = result.getDouble("price");
                int size = result.getInt("size");
                Boolean withAlcohol = result.getBoolean("withAlcohol");

                drinks.add(new Drink.Builder()
                        .buildId(drinkId)
                        .buildName(name)
                        .buildPrice(price)
                        .buildSize(size)
                        .buildWithAlcohol(withAlcohol)
                        .build());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drinks;
    }
}
