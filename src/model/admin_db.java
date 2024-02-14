package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class admin_db {
    private static String tableName = "admin";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert(1, "Michael", "Balubar", "Angelo", "09278819922", "admin123", "admin123@");
        // updateStr(1, "admin_FName", "Rodney");
        // updateInt(1, "admin_Id", 0);
        // delete(1);
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
                System.out.println(resultSet.getString("admin_Id"));
                System.out.println(resultSet.getString("admin_FNAME"));
                System.out.println(resultSet.getString("admin_MNAME"));
                System.out.println(resultSet.getString("admin_LNAME"));
                System.out.println(resultSet.getString("admin_ContactInfo"));
                System.out.println(resultSet.getString("admin_Username"));
                System.out.println(resultSet.getString("admin_Password"));

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

    public static void insert(String a_FName, String a_MName, String a_LName, String a_ContactInfo, String a_username, String a_password) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "INSERT INTO admin (admin_FName, admin_MName, admin_LName, admin_ContactInfo, admin_Username, admin_Password) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, a_FName);
            preparedStatement.setString(2, a_MName);
            preparedStatement.setString(3, a_LName);
            preparedStatement.setString(4, a_ContactInfo);
            preparedStatement.setString(5, a_username);
            preparedStatement.setString(6, a_password);

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

    public static void updateStr(int Id, String columnName, String newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE admin SET " + columnName + " = ? WHERE " + "admin_Id = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newValue);
            preparedStatement.setInt(2, Id);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[admin] "+ columnName + " updated successfully" + " to " + newValue + " for admin_Id: " + Id);
            } else {
                System.out.println("No record found for the given " + columnName + " for admin_Id: " + Id);
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

    public static void updateInt(int Id, String columnName, int newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE admin SET " + columnName + " = ? WHERE admin_Id = ?";
    
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, newValue);
            preparedStatement.setInt(2, Id);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[admin] " + columnName + " updated successfully to " + newValue + " for admin_Id: " + Id);
            } else {
                System.out.println("No record found for the given " + columnName + " for admin_Id: " + Id);
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
    

    public static void delete(int a_Id) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "DELETE FROM admin WHERE admin_Id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, a_Id);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Admin record deleted successfully");
            } else {
                System.out.println("No admin record found for the given ID:" + a_Id);
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