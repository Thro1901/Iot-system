package repository;

import models.Sensor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Database {
    Properties p = new Properties();


    public Database() throws IOException {
        p.load(new FileInputStream("src/config.properties"));
        String hostName = p.getProperty("hostName");
        String dbName = p.getProperty("dbName");
        String user = p.getProperty("user");
        String password = p.getProperty("password");
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
                + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);

    }
    public List<Sensor> getLatest(int limit){
        Connection connection = null;
        List<Sensor> sensorList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(p.getProperty("url"));
            String schema = connection.getSchema();

            String selectSql = "SELECT TOP " + limit  + " * FROM Measurements ORDER BY Created DESC";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                while (resultSet.next())
                {
                    sensorList.add(new Sensor(resultSet.getInt("Id"),resultSet.getDouble("Temperature"),resultSet.getDouble("Humidity"),resultSet.getDate("Created").toString(),resultSet.getTime("Created").toString()));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sensorList;
    }
    public List<Sensor> getBetweenDates(String first, String second){
        Connection connection = null;
        List<Sensor> sensorList = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(p.getProperty("url"));
            String schema = connection.getSchema();

            String selectSql = "SELECT * FROM Measurements  WHERE Created >= '"+first+" 00:00:00.0' AND Created < '"+second+" 23:59:59.999' order by created desc;";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectSql)) {

                while (resultSet.next())
                {
                    sensorList.add(new Sensor(resultSet.getInt("Id"),resultSet.getDouble("Temperature"),resultSet.getDouble("Humidity"),resultSet.getDate("Created").toString(),resultSet.getTime("Created").toString()));
                }
                connection.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sensorList;
    }

}