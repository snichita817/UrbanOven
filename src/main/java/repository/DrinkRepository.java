package repository;

import model.database.DatabaseConnection;
import model.product.Drink;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Optional<Drink> getDrinkById(int id) {
        String sql = "SELECT * FROM drinks d where d.drink_id = id";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                // We have at least one record in the row
                int drinkId = result.getInt("drink_id");
                String name = result.getString("name");
                Double price = result.getDouble("price");
                int size = result.getInt("size");
                boolean withAlcohol = result.getBoolean("withAlcohol");
                return Optional.of(new Drink.Builder()
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
