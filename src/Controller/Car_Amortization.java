package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DbConnect;
import model.amortization;
import model.amortization_table;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Car_Amortization implements Initializable {
    @FXML
    private TableView<amortization> amortizationTable;
    @FXML
    private TableColumn<amortization, Integer> RecordIDcolumn;
    @FXML
    private TableColumn<amortization, String> SDatecolumn; 
    @FXML
    private TableColumn<amortization, String> DDatecolumn;
    @FXML 
    private TableColumn<amortization, String> EDatecolumn;
    @FXML
    private TableColumn<amortization, Integer> PaymentColumn;
    @FXML
    private TableColumn<amortization, String> CarPlateColumn;
    @FXML
    private TableColumn<amortization, String> StatusColumn;

    @FXML
    private ComboBox<String> filterComboBox;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    amortization amortization = null;

    ObservableList<amortization> amortizationList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       loadDate();
       setupFilterComboBox();
    }



    private void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        RecordIDcolumn.setCellValueFactory(new PropertyValueFactory<amortization, Integer>("amortization_RecordID"));
        CarPlateColumn.setCellValueFactory(new PropertyValueFactory<amortization, String>("car_Plate"));
        SDatecolumn.setCellValueFactory(new PropertyValueFactory<amortization, String>("amortization_SDate"));
        DDatecolumn.setCellValueFactory(new PropertyValueFactory<amortization, String>("amortization_DDate"));
        EDatecolumn.setCellValueFactory(new PropertyValueFactory<amortization, String>("amortization_EDate"));
        PaymentColumn.setCellValueFactory(new PropertyValueFactory<amortization, Integer>("amortization_Payment"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<amortization, String>("amortization_Status"));

    }

    @FXML
private void refreshTable() {
    try {
        amortizationList.clear();

        String selectedFilter = filterComboBox.getValue();

        // Set default value to "All" if selectedFilter is null
        if (selectedFilter == null) {
            selectedFilter = "All";
            filterComboBox.setValue("All");
        }

        if (selectedFilter.equals("All")) {
            query = "SELECT * FROM amortization";
        } else if (selectedFilter.equals("Paid")) {
            query = "SELECT * FROM amortization WHERE amortization_Status = 'Paid'";
        } else if (selectedFilter.equals("Unpaid")) {
            query = "SELECT * FROM amortization WHERE amortization_Status = 'Unpaid'";
        }

        preparedStatement = connection.prepareStatement(query);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            amortizationList.add(new amortization(
                    resultSet.getInt("amortization_RecordID"),
                    resultSet.getString("car_Plate"),
                    resultSet.getDate("amortization_SDate"),
                    resultSet.getDate("amortization_DDate"),
                    resultSet.getDate("amortization_EDate"),
                    resultSet.getInt("amortization_Payment"),
                    resultSet.getString("amortization_Status")
            ));
        }
        amortizationTable.setItems(amortizationList);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    



    


    public void GoToHome(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void GoToC_Accounts(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Accounts.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void GoToC_Maintenance(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Maintenance.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void GoToD_Accounts(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Driver_Accounts.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    private void setupFilterComboBox() {
        filterComboBox.setItems(FXCollections.observableArrayList("All", "Paid", "Unpaid"));
        filterComboBox.setValue("All");
    }
    
    @FXML
    private void handleFilterChange(ActionEvent event) {
        refreshTable();
    }

    

    
}