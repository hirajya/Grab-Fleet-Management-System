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

//import model.object_model.Driver_Quota_obj;


public class maintenance_table {
    private static String tableName = "maintenance";


    public static void main(String[] args) throws Exception {
        // connect();
        // insert(9000, 2500, "2021-10-01", "2021-10-31", "B0222300753");
        // insert(5000, 5000, "2022-11-02", "2022-11-04", "B0233303753");
        // insert(5000, 3000, "2022-11-02", "2024-11-04", "B0211103753");
        //insert(5000, 2000, "2022-11-02", "2023-09-04", "B0111103753");


        // // updateStr("B0222300753", "driver_Name", "Rodney Lei");
        // // updateInt("B0222300753", "boundary_InputAmount", 200);
        // // delete("B0222300753");
        // connect();
        //System.out.println(getName("B0222300753"));
    }

    public static List<maintenance> getMaintenanceData() {
        List<maintenance> maintenanceList = new ArrayList<>();
    
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
                int maintenanceId = resultSet.getInt("maintenance_ID");
                String carPlate = resultSet.getString("car_Plate");
                String licenseNumber = resultSet.getString("driver_LicenseNum");
                String changeOil = resultSet.getString("maintenance_ChangeOil");
                String changeBelt = resultSet.getString("maintenance_ChangeBelt");

                // Calculate balance
                //int balance = amount - paidAmount;

                //String startDate = resultSet.getString("maintenance_SDate");
               //String dueDate = resultSet.getString("maintenance_DDate");

// Determine status based on change oil date
String status = "";
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
try {
    // Get the current date
    java.util.Date currentDate = new java.util.Date();
    
    // Parse the change oil date
    java.util.Date changeOilDate = dateFormat.parse(changeOil);
    
    // Calculate the difference in months between the current date and change oil date
    long diffInMonths = (currentDate.getTime() - changeOilDate.getTime()) / (1000L * 60L * 60L * 24L * 30L);
    
    if (diffInMonths < 6) {
        status = "Up to Date";
    } else if (diffInMonths >= 6 && diffInMonths < 6.5) {
        status = "Due Soon";
    } else {
        status = "Overdue";
    }
} catch (ParseException e) {
    e.printStackTrace();
}

// Continue with the rest of the code...
                


                // Determine status
               // Determine status
               // String status = (paidAmount >= amount) ? "Paid" : "Unpaid";


                String carSeries = getCarSeries(carPlate);

                // Create maintenance object and add it to the list
                maintenance maintenanceObj = new maintenance(maintenanceId, carSeries, carPlate, licenseNumber, changeOil, changeBelt, status);
                maintenanceList.add(maintenanceObj);
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
    
        return maintenanceList;
    }

    public static List<maintenance> getMaintenanceDataByStatus(String status) {
        List<maintenance> maintenanceList = new ArrayList<>();

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
            String sqlQuery = "SELECT * FROM maintenance WHERE maintenance_Status = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, status);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                // Extract data from the result set
                int maintenanceId = resultSet.getInt("maintenance_ID");
                String carPlate = resultSet.getString("car_Plate");
                String licenseNumber = resultSet.getString("driver_LicenseNum");
                String changeOil = resultSet.getString("maintenance_ChangeOil");
                String changeBelt = resultSet.getString("maintenance_ChangeBelt");

                //int recordId = resultSet.getInt("maintenance_RecordID");
                //String licenseNumber = resultSet.getString("driver_LicenseNum");
                String carSeries = getCarSeries(carPlate);
                //int amount = resultSet.getInt("maintenance_Amount");
                // int paidAmount = resultSet.getInt("maintenance_InputAmount");
                // int balance = resultSet.getInt("maintenance_Balance");
                // String startDate = resultSet.getString("maintenance_SDate");
                // String dueDate = resultSet.getString("maintenance_DDate");
                // String maintenanceStatus = resultSet.getString("maintenance_Status");


                // Create Driver_Maintenance_obj and add it to the list
                maintenance maintenanceObj = new maintenance(maintenanceId, carSeries, carPlate, licenseNumber, changeOil, changeBelt, status);
                maintenanceList.add(maintenanceObj);
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

        return maintenanceList;
    }

    public static String getCarSeries(String carPlate) {
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
    
        String carSeries = "";
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
    
            // Modify the SQL query to include a WHERE clause for status
            String sqlQuery = "SELECT * FROM car WHERE car_Plate = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, carPlate);
    
            resultSet = preparedStatement.executeQuery();
    
            // Check if there is any result
            if (resultSet.next()) {
                String cSeries = resultSet.getString("car_Series");
                //String dFName = resultSet.getString("driver_FName");
                carSeries = cSeries;
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
    
        return carSeries;
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
                System.out.println(resultSet.getInt("maintenance_ID"));
                System.out.println(resultSet.getString("car_Plate"));
                System.out.println(resultSet.getString("driver_LicenseNum"));
                System.out.println(resultSet.getString("maintenance_ChangeOil"));
                System.out.println(resultSet.getString("maintenance_ChangeBelt"));
                System.out.println(resultSet.getString("maintenance_Status"));
                // System.out.println(resultSet.getString("maintenance_RecordID"));
                // System.out.println(resultSet.getInt("maintenance_Amount"));
                // System.out.println(resultSet.getInt("maintenance_InputAmount"));
                // System.out.println(resultSet.getString("maintenance_Balance"));
                // System.out.println(resultSet.getString("maintenance_SDate"));
                // System.out.println(resultSet.getString("maintenance_DDate"));
                // System.out.println(resultSet.getString("maintenance_Status"));
                // System.out.println(resultSet.getString("driver_LicenseNum"));
            
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
    /////////
}