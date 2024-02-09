import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        connect();
        update(4, "24 CHEC");
        // insert(4, "24 chicken", "almar", "sarap-6969");
        delete(4);
        connect();
    }

    public static void connect(){

        String url = "jdbc:mysql://localhost:3306/businesses";
        String user = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();

            String sqlQuery = "SELECT * FROM business_venture";
            System.out.println("Connected to the database");

            resultSet = statement.executeQuery(sqlQuery);

            while(resultSet.next()){
                System.out.println(resultSet.getString("business_Name"));
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

    public static void insert(int Id, String b_name, String b_loc, String b_tin) {
        String url = "jdbc:mysql://localhost:3306/businesses";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "INSERT INTO business_venture (business_Id, business_Name, business_Loc, business_Tin) VALUES (?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sqlQuery);

            preparedStatement.setInt(1, Id);
            preparedStatement.setString(2, b_name);
            preparedStatement.setString(3, b_loc);
            preparedStatement.setString(4, b_tin);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("A new business has been inserted");
            } else {
                System.out.println("A new business has not been inserted");
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

    public static void update(int Id, String newBusinessName) {
        String url = "jdbc:mysql://localhost:3306/businesses";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "UPDATE business_venture SET business_Name = ? WHERE business_Id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setString(1, newBusinessName);
            preparedStatement.setInt(2, Id);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Business record updated successfully");
            } else {
                System.out.println("No business record found for the given ID");
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

    public static void delete(int Id) {
        String url = "jdbc:mysql://localhost:3306/businesses";
        String user = "root";
        String password = "";
    
        Connection connection = null;
        PreparedStatement preparedStatement = null;
    
        try {
            connection = DriverManager.getConnection(url, user, password);
            String sqlQuery = "DELETE FROM business_venture WHERE business_Id = ?";
            preparedStatement = connection.prepareStatement(sqlQuery);
    
            preparedStatement.setInt(1, Id);
    
            int rows = preparedStatement.executeUpdate();
    
            if (rows > 0) {
                System.out.println("Business record deleted successfully");
            } else {
                System.out.println("No business record found for the given ID");
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