package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.object_model.Driver_Quota_obj;


public class quota_table {
    private static String tableName = "quota";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert(9000, 2500, "2021-10-01", "2021-10-31", "B0222300753");
        // insert(5000, 5000, "2022-11-02", "2022-11-04", "B0233303753");
        // insert(5000, 3000, "2022-11-02", "2024-11-04", "B0211103753");
        insert(5000, 2000, "2022-11-02", "2023-09-04", "B0111103753");


        // // updateStr("B0222300753", "driver_Name", "Rodney Lei");
        // // updateInt("B0222300753", "boundary_InputAmount", 200);
        // // delete("B0222300753");
        // connect();
        System.out.println(getName("B0222300753"));
    }

    public static List<Driver_Quota_obj> getQuotaData() {
        List<Driver_Quota_obj> quotaList = new ArrayList<>();
    
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
    
            while (resultSet.next()) {
                // Extract data from the result set
                int recordId = resultSet.getInt("quota_RecordID");
                String licenseNumber = resultSet.getString("driver_LicenseNum");
                int amount = resultSet.getInt("quota_Amount");
                int paidAmount = resultSet.getInt("quota_InputAmount");
    
                // Calculate balance
                int balance = amount - paidAmount;
    
                String startDate = resultSet.getString("quota_SDate");
                String dueDate = resultSet.getString("quota_DDate");
    
                // Determine status
                String status = (paidAmount >= amount) ? "Paid" : "Unpaid";

                String dName = getName(licenseNumber);
    
                // Create Driver_Quota_obj and add it to the list
                Driver_Quota_obj quotaObj = new Driver_Quota_obj(recordId, dName,licenseNumber, amount, paidAmount, balance, startDate, dueDate, status);
                quotaList.add(quotaObj);
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
    
        return quotaList;
    }

    public static List<Driver_Quota_obj> getQuotaDataByStatus(String status) {
        List<Driver_Quota_obj> quotaList = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            // Modify the SQL query to include a WHERE clause for status
            String sqlQuery = "SELECT * FROM quota WHERE quota_Status = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, status);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Extract data from the result set
                int recordId = resultSet.getInt("quota_RecordID");
                String licenseNumber = resultSet.getString("driver_LicenseNum");
                String driverName = getName(licenseNumber);
                int amount = resultSet.getInt("quota_Amount");
                int paidAmount = resultSet.getInt("quota_InputAmount");
                int balance = resultSet.getInt("quota_Balance");
                String startDate = resultSet.getString("quota_SDate");
                String dueDate = resultSet.getString("quota_DDate");
                String quotaStatus = resultSet.getString("quota_Status");


                // Create Driver_Quota_obj and add it to the list
                Driver_Quota_obj quotaObj = new Driver_Quota_obj(recordId, driverName, licenseNumber, amount, paidAmount, balance, startDate, dueDate, quotaStatus);
                quotaList.add(quotaObj);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return quotaList;
    }

    public static String getName(String licenseNumber) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        String dName = "";
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
    
            // Modify the SQL query to include a WHERE clause for status
            String sqlQuery = "SELECT * FROM driver WHERE driver_LicenseNum = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, licenseNumber);
    
            resultSet = preparedStatement.executeQuery();
    
            // Check if there is any result
            if (resultSet.next()) {
                String dFName = resultSet.getString("driver_FName");
                String dMName = resultSet.getString("driver_MName");
                String dLName = resultSet.getString("driver_LName");
                dName = dFName + " " + dMName + " " + dLName;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        return dName;
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
                System.out.println(resultSet.getString("quota_RecordID"));
                System.out.println(resultSet.getInt("quota_Amount"));
                System.out.println(resultSet.getInt("quota_InputAmount"));
                System.out.println(resultSet.getString("quota_Balance"));
                System.out.println(resultSet.getString("quota_SDate"));
                System.out.println(resultSet.getString("quota_DDate"));
                System.out.println(resultSet.getString("quota_Status"));
                System.out.println(resultSet.getString("driver_LicenseNum"));
            
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

    public static void insert(int q_Amount, int q_InputAmount, String q_SDate, String q_DDate, String d_LicenseNum) throws ParseException {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String sqlQuery = "INSERT INTO quota (quota_Amount, quota_InputAmount, quota_Balance, quota_SDate, quota_DDate, quota_Status, driver_LicenseNum) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, q_Amount);
            preparedStatement.setInt(2, q_InputAmount);
            preparedStatement.setInt(3, q_Amount - q_InputAmount);
            preparedStatement.setString(4, q_SDate);
            preparedStatement.setString(5, q_DDate);
            if (q_InputAmount >= q_Amount) {
                preparedStatement.setString(6, "Paid");
            } else {
                preparedStatement.setString(6, "Unpaid");
            }
            preparedStatement.setString(7, d_LicenseNum);
            

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new boundary has been inserted");
            } else {
                System.out.println("A new boundary has not been inserted");
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
            String sqlQuery = "UPDATE quota SET " + columnName + " = ? WHERE " + "driver_LicenseNum = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newValue);
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver boundary] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
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
            String sqlQuery = "UPDATE quota SET " + columnName + " = ? WHERE driver_LicenseNum = ?";
    
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver boundary] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
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
            String sqlQuery = "UPDATE quota SET " + columnName + " = ? WHERE " + "driver_LicenseNumber = ?";

            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(dateFormat.parse(newValue).getTime()));
            preparedStatement.setString(2, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("[driver boundary] "+ columnName + " updated successfully" + " to " + newValue + " for driver license number: " + d_LicenseNum);
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
            String sqlQuery = "DELETE FROM quota WHERE driver_LicenseNum = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, d_LicenseNum);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Car boundary record deleted successfully");
            } else {
                System.out.println("No car boundary record found for the given ID:" + d_LicenseNum);
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