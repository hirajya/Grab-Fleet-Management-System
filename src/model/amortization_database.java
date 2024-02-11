package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class amortization_database {
    private static String tableName = "car";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert("BAC0522", "2022-12-20", "2023-12-20", "2024-12-20", 10000, true);
        // updateDatetime("BAC5522", "car_AmortizationDDate", "2000-01-01");
        // updateInt("BAC5522", "car_AmortizationPayment", 2000);
        // delete("BAC5522");
        delete("BAC0522");

        connect();
    }

    public static void connect(){

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            String sqlQuery = "SELECT * FROM " + tableName + "";
            System.out.println("Connected to the database");

            resultSet = statement.executeQuery(sqlQuery);

            while(resultSet.next()){
                System.out.println(resultSet.getString("car_Plate"));
                System.out.println(resultSet.getString("car_AmortizationSDate"));
                System.out.println(resultSet.getString("car_AmortizationDDate"));
                System.out.println(resultSet.getString("car_AmortizationEDate"));
                System.out.println(resultSet.getString("car_AmortizationPayment"));
                System.out.println(resultSet.getString("car_WeeklyPaymentDoneStatus"));

            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void insert(String c_Plate, String c_AmortizationSDate, String c_AmortizationDDate, String c_AmortizationEDate, int c_AmortizationPayment, Boolean c_WeeklyPaymentDoneStatus) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String sqlQuery = "INSERT INTO amortization (car_Plate, car_AmortizationSDate, car_AmortizationDDate, car_AmortizationEDate, car_AmortizationPayment, car_WeeklyPaymentDoneStatus) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, c_Plate);
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(dateFormat.parse(c_AmortizationSDate).getTime()));
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(dateFormat.parse(c_AmortizationDDate).getTime()));
            preparedStatement.setTimestamp(4, new java.sql.Timestamp(dateFormat.parse(c_AmortizationEDate).getTime()));
            preparedStatement.setInt(5, c_AmortizationPayment);
            preparedStatement.setBoolean(6, c_WeeklyPaymentDoneStatus);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new amortization has been inserted");
            } else {
                System.out.println("A new amortization has not been inserted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    

    public static void updateStr(String c_plate, String columnName, String newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE amortization SET " + columnName + " = ? WHERE " + "car_Plate = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car amortization] "+ columnName + " updated successfully" + " to " + newValue + " for car_plate: " + c_plate);
            } else {
                System.out.println("No record found for the given " + columnName + " for car_plate: " + c_plate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateInt(String c_plate, String columnName, int newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE amortization SET " + columnName + " = ? WHERE car_Plate = ?";
    
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car amortization] " + columnName + " updated successfully to " + newValue + " for car_Plate: " + c_plate);
            } else {
                System.out.println("No record found for the given " + columnName + " for car_Plate: " + c_plate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    public static void updateDatetime(String c_plate, String columnName, String newValue) throws ParseException {
        // new val format: yyyy-MM-dd
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sqlQuery = "UPDATE amortization SET " + columnName + " = ? WHERE " + "car_Plate = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateFormat.parse(newValue).getTime()));
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car amortization] "+ columnName + " updated successfully" + " to " + newValue + " for car_plate: " + c_plate);
            } else {
                System.out.println("No record found for the given " + columnName + " for car_plate: " + c_plate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(String c_plate) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "DELETE FROM amortization WHERE car_Plate = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Car amortization record deleted successfully");
            } else {
                System.out.println("No car amortization record found for the given ID:" + c_plate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}