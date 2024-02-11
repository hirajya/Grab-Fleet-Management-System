package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class car_database {
    private static String tableName = "car";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert("BAC5522", "375365253", "2675593", "2024-02-08", "2024-02-01", "Innova 2.8 E", "MPV", 2022, "Silver", "2022-02-01", "2022-02-01", 1);
        // updateStr("BAC5522", "car_Series", "BMW 3 Series");
        // updateInt("BAC5522", "admin_Id", 2);
        delete("BAC5522");
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
                System.out.println(resultSet.getString("car_CRNum"));
                System.out.println(resultSet.getString("car_ORNum"));
                System.out.println(resultSet.getString("car_RegExpiry"));
                System.out.println(resultSet.getString("car_Registration"));
                System.out.println(resultSet.getString("car_Series"));
                System.out.println(resultSet.getString("car_Kind"));
                System.out.println(resultSet.getInt("car_YearModel"));
                System.out.println(resultSet.getString("car_Color"));
                System.out.println(resultSet.getString("car_ChangeOil"));
                System.out.println(resultSet.getString("car_ChangeBelt"));
                System.out.println(resultSet.getString("admin_Id"));
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

    public static void insert(String c_Plate, String c_CRNum, String c_ORNum, String c_RegExpiry, String c_Registration, String c_Series, String c_Kind, int c_YearModel, String c_Color, String c_ChangeOil, String c_ChangeBelt, int a_Id) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String sqlQuery = "INSERT INTO car (car_Plate, car_CRNum, car_ORNum, car_RegExpiry, car_Registration, car_Series, car_Kind, car_YearModel, car_Color, car_ChangeOil, car_ChangeBelt, admin_Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, c_Plate);
            preparedStatement.setString(2, c_CRNum);
            preparedStatement.setString(3, c_ORNum);
            preparedStatement.setString(4, c_RegExpiry);
            preparedStatement.setString(5, c_Registration);
            preparedStatement.setString(6, c_Series);
            preparedStatement.setString(7, c_Kind);
            preparedStatement.setInt(8, c_YearModel);
            preparedStatement.setString(9, c_Color);

            preparedStatement.setTimestamp(10, new java.sql.Timestamp(dateFormat.parse(c_ChangeOil).getTime()));
            preparedStatement.setTimestamp(11, new java.sql.Timestamp(dateFormat.parse(c_ChangeBelt).getTime()));

            preparedStatement.setInt(12, a_Id);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new admin has been inserted");
            } else {
                System.out.println("A new admin has not been inserted");
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
            String sqlQuery = "UPDATE car SET " + columnName + " = ? WHERE " + "car_Plate = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car] "+ columnName + " updated successfully" + " to " + newValue + " for car_plate: " + c_plate);
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
            String sqlQuery = "UPDATE car SET " + columnName + " = ? WHERE car_Plate = ?";
    
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car] " + columnName + " updated successfully to " + newValue + " for car_Plate: " + c_plate);
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
            String sqlQuery = "UPDATE car SET " + columnName + " = ? WHERE " + "car_Plate = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setTimestamp(10, new java.sql.Timestamp(dateFormat.parse(newValue).getTime()));
            preparedStatement.setString(2, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[car] "+ columnName + " updated successfully" + " to " + newValue + " for car_plate: " + c_plate);
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
            String sqlQuery = "DELETE FROM car WHERE car_Plate = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, c_plate);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Car record deleted successfully");
            } else {
                System.out.println("No car record found for the given ID:" + c_plate);
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