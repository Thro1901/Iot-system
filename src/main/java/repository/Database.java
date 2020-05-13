package repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Properties;

public class Database {


    public static void main(String[] args) throws IOException {

        Properties p = new Properties();

        p.load(new FileInputStream("src/config.properties"));

        // Connect to database
        String hostName = p.getProperty("hostName");
        String dbName = p.getProperty("dbName");
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
                + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url);
            String schema = connection.getSchema();
            System.out.println("Successful connection - Schema: " + schema);

            System.out.println("Query data example:");
            System.out.println("=========================================");

            // Create and execute a SELECT SQL statement.
            String selectSql = "SELECT * FROM Measurements ";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                // Print results from select statement
                while (resultSet.next())
                {
                    System.out.println(resultSet.getInt("Id")+" "+resultSet.getDouble("Temperature")+" "+resultSet.getDouble("Humidity")+" "+resultSet.getDate("Created")+" "+resultSet.getTime("Created"));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

