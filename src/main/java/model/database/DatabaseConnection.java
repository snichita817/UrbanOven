package model.database;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private String path_to_init = "src\\main\\java\\model\\init.sql";
    public String url;
    private String driver;                              // mysql
    private String port;                                // 3306
    private String database;                            // urbanoven
    private String username;                            // root
    private String password;                            // root
    private Connection connection;
    public DatabaseConnection(String driver, String port, String database, String username, String password)
    {
        if(instance != null) {
            throw new RuntimeException("A database instance is already running");
        }
        else {
            instance = this;
        }

        this.driver = driver;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        createConnectionString();

        System.out.println("A database instance created successfully");
    }

    private void createConnectionString() {
        this.url = "jdbc:" + this.driver + "://localhost:" + this.port + "/" + this.database;
    }

    public void connect() throws Exception {
        try {
            this.connection = DriverManager.getConnection(this.url, this.username, this.password);
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        System.out.println("A connection to the MySQL database established successfully!");
    }

    public void disconnect() throws Exception {
        try {
            this.connection.close();
        }
        catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }

        System.out.println("The connection to the database closed successfully!");
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void initTables() throws SQLException, IOException {
        Statement statement = this.getConnection().createStatement();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path_to_init));
            StringBuilder sb = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            String initSeq = sb.toString();

            // Execute the SQL script
            String[] statements = initSeq.split(";");

            for(String sql : statements) {
                statement.execute(sql);         // executing the block of script
            }

            System.out.println("Tables created successfully!");
            System.out.println("Default values inserted successfully!");
            statement.close();
        }
        catch (IOException e) {
            System.err.println("Error reading/closing init.sql file: " + e.getMessage());
            throw new IOException(e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error connecting to the database " + e.getMessage());
            throw new SQLException(e.getMessage());
        }
    }
}
