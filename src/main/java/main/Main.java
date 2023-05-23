package main;
import model.*;
import model.database.DatabaseConnection;
import service.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        DatabaseConnection database =
                new DatabaseConnection("mysql", "3306", "urbanoven", "root", "root");
        try {
            database.connect();             // connect to the database
            database.initTables();          // initializing tables
        } catch (Exception e) {
            e.printStackTrace();
        }
        PizzeriaService.openShop();

        try {
            database.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
