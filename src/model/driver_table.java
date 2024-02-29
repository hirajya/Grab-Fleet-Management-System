package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class driver_table {
    private static String tableName = "driver";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert("B0222300753", "09123456789", "09123456789", "Male", "Rodney", "Lei", "Lopez", "1999-12-12", 123, "Quezon City", "Lopez", "Lopez", "Lopez", "NAC2393", "2022-12-12", 1, 3);
        insert("B0233303753", "09123456789", "09123456789", "Female", "Jaena", "Lei", "Ara", "1989-11-12", 123, "Quezon City", "Lopez", "Lopez", "Lopez", "PAC2393", "2022-11-12", 1, 4);
        // insert("B0211103753", "09123456789", "09123456789", "Male", "Jaenaru", "Kairu", "Ara", "2001-11-12", 123, "Quezon City", "Lopez", "Lopez", "Lopez", "DAC2393", "2022-11-12", 1, 4);
        // insert("B0111103753", "09123456789", "09123456789", "Male", "Boki", "Kairu", "Shin", "2001-11-12", 123, "Quezon City", "Lopez", "Lopez", "Lopez", "TAC2393", "2022-11-12", 1, 4);

        // updateInt("B0222300753", "boundary_InputAmount", 200);
        // delete("B0222300753");
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
                System.out.println(resultSet.getString("driver_LicenseNum"));
                System.out.println(resultSet.getString("driver_CNumber"));
                System.out.println(resultSet.getString("driver_CPersonNum"));
                System.out.println(resultSet.getString("driver_Sex"));
                System.out.println(resultSet.getString("driver_FName"));
                System.out.println(resultSet.getString("driver_MName"));
                System.out.println(resultSet.getString("driver_LName"));
                System.out.println(resultSet.getString("driver_Birthdate"));
                System.out.println(resultSet.getString("driver_HouseNum"));
                System.out.println(resultSet.getString("driver_City"));
                System.out.println(resultSet.getString("driver_Street"));
                System.out.println(resultSet.getString("driver_Block"));
                System.out.println(resultSet.getString("driver_Brgy"));
                System.out.println(resultSet.getString("car_Plate"));
                System.out.println(resultSet.getString("driver_LicenseExpiry"));
                System.out.println(resultSet.getString("admin_Id"));
                System.out.println(resultSet.getString("quota_RecordID"));


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

    public static void insert(String d_LicenseNum, String d_CNumber, String d_CPersonNum, String d_Sex, String d_FName, String d_MName, String L_Name, String d_Birthdate, int d_HouseNum, String d_City, String d_Street, String d_Block, String d_Brgy, String c_Plate, String d_LicenseExpiry, int a_Id, int q_RecordID) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String sqlQuery = "INSERT INTO driver (driver_LicenseNum, driver_CNumber, driver_CPersonNum, driver_Sex, driver_FName, driver_MName, driver_LName, driver_Birthdate, driver_HouseNum, driver_City, driver_Street, driver_Block, driver_Brgy, car_Plate, driver_LicenseExpiry, admin_Id, quota_RecordID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setString(1, d_LicenseNum);
            preparedStatement.setString(2, d_CNumber);
            preparedStatement.setString(3, d_CPersonNum);
            preparedStatement.setString(4, d_Sex);
            preparedStatement.setString(5, d_FName);
            preparedStatement.setString(6, d_MName);
            preparedStatement.setString(7, L_Name);
            preparedStatement.setTimestamp(8, new java.sql.Timestamp(dateFormat.parse(d_Birthdate).getTime()));
            preparedStatement.setInt(9, d_HouseNum);
            preparedStatement.setString(10, d_City);
            preparedStatement.setString(11, d_Street);
            preparedStatement.setString(12, d_Block);
            preparedStatement.setString(13, d_Brgy);
            preparedStatement.setString(14, c_Plate);
            preparedStatement.setTimestamp(15, new java.sql.Timestamp(dateFormat.parse(d_LicenseExpiry).getTime()));
            preparedStatement.setInt(16, a_Id);
            preparedStatement.setInt(17, q_RecordID);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new driver has been inserted");
            } else {
                System.out.println("A new driver has not been inserted");
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

    

    public static void updateStr(String d_LicenseNum, String columnName, String newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE driver SET " + columnName + " = ? WHERE " + "driver_LicenseNum = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
            } else {
                System.out.println("No record found for the given " + columnName + " for driver license number: " + d_LicenseNum);
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

    public static void updateInt(String d_LicenseNum, String columnName, int newValue) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE driver SET " + columnName + " = ? WHERE driver_LicenseNum = ?";
    
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
            } else {
                System.out.println("No record found for the given " + columnName + " for driver license number: " + d_LicenseNum);
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
    

    public static void updateDatetime(String d_LicenseNum, String columnName, String newValue) throws ParseException {
        // new val format: yyyy-MM-dd
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String sqlQuery = "UPDATE driver SET " + columnName + " = ? WHERE " + "driver_LicenseNumber = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateFormat.parse(newValue).getTime()));
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
            } else {
                System.out.println("No record found for the given " + columnName + " for driver license number: " + d_LicenseNum);
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

    public static void delete(String d_LicenseNum) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "DELETE FROM driver WHERE driver_LicenseNum = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Car driver record deleted successfully");
            } else {
                System.out.println("No car driver record found for the given ID:" + d_LicenseNum);
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