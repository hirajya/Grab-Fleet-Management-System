package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class amortization_table {
    private static String tableName = "amortization";


    public static void main(String[] args) throws Exception {
        insert("2019-07-18", "2023-03-10", "2024-11-19", 17833, "BAC5522", "Unpaid");
        // updateStr("NAC6983", "amortization_SDate", "2022-02-11");
        //delete("NAC6983");
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
                System.out.println(resultSet.getString("amortization_RecordID"));
                System.out.println(resultSet.getString("amortization_SDate"));
                System.out.println(resultSet.getString("amortization_DDate"));
                System.out.println(resultSet.getString("amortization_EDate"));
                System.out.println(resultSet.getInt("amortization_Payment"));
                System.out.println(resultSet.getString("car_Plate"));
                System.out.println(resultSet.getString("amortization_Status"));


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

    public static void insert(String a_SDate, String a_DDate, String a_EDate, int a_Payment, String c_Plate, String a_Status) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String sqlQuery = "INSERT INTO amortization (amortization_SDate, amortization_DDate, amortization_EDate, amortization_Payment, car_Plate, amortization_Status) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateFormat.parse(a_SDate).getTime()));
            preparedStatement.setTimestamp(2, new java.sql.Timestamp(dateFormat.parse(a_DDate).getTime()));
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(dateFormat.parse(a_EDate).getTime()));
            preparedStatement.setInt(4, a_Payment);
            preparedStatement.setString(5, c_Plate);
            preparedStatement.setString(6, a_Status);


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