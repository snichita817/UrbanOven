package repository;

import model.database.DatabaseConnection;
import model.product.Topping;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ToppingRepository {
    public static void addTopping(Topping topping) {
        String sql = "INSERT IGNORE INTO toppings VALUES (?, ?, ?)";
        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql))  {
            statement.setString(1, topping.getId());
            statement.setString(2, topping.getName().toString());
            statement.setDouble(3, topping.getPricePerUnit());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Topping> getToppingById(String topping_id) {
        String sql = "SELECT * FROM toppings t WHERE t.topping_id = ?";

        try(PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, topping_id);

            ResultSet result = statement.executeQuery();
            if(result.next()) {
                String id = result.getString("topping_id");
                String name = result.getString("name");
                double price = result.getDouble("price");

                return Optional.of(new Topping.Builder()
                        .buildId(id)
                        .buildName(name)
                        .buildPrice(price)
                        .build());
            }


        } catch (SQLException e ) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
