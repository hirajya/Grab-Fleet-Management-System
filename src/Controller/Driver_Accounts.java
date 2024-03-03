package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import model.DbConnect;
import model.Driver_Accounts_obj;
import model.amortization;
import model.driver;
import model.driver_table;
import model.object_model.Driver_Quota_obj;

public class Driver_Accounts {

    @FXML
    private TableView<model.Driver_Accounts_obj> driver_acc_table;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> licenseNumColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, Date> licenseNumExpiryColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> FNameColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> MNameColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> LNameColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> CNumColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> CPersonNumColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> SexColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, Date> BDateColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> HouseNumColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> BlockColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> StreetColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> BrgyColumn;

    @FXML
    private TableColumn<model.Driver_Accounts_obj, String> CityColumn;

    @FXML
    public TableColumn<model.Driver_Accounts_obj, String> CarPlateColumn;

    @FXML
    private TextArea addFName;

    @FXML
    private TextArea addMName;

    @FXML
    private TextArea addLName;

    @FXML
    private TextArea addLicenseNum;

    @FXML
    private TextArea addContactNum;

    @FXML
    private TextArea addCPersonNum;

    @FXML
    private TextArea addHouseNum; 

    @FXML
    private TextArea addBlock;

    @FXML
    private TextArea addStreet;

    @FXML
    private TextArea addBrgy;

    @FXML
    private TextArea addCity;

    @FXML
    private TextArea addSex;

    @FXML
    private TextArea addCarPlate;

    @FXML
    private DatePicker addBirthDate,addLicenseNumExpiry;

    
    

    private ObservableList<Driver_Accounts_obj> originalData;

    public void initialize() { 

        setUpColumns();
        
        originalData = FXCollections.observableArrayList(model.driver_table.getDriverData());

        driver_acc_table.getItems().addAll(originalData);

    }

    //display sa table
    private void setUpColumns() {
        System.err.println("Driver Accounts Initialized");

            licenseNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LicenseNum"));
            licenseNumExpiryColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LicenseExpiry"));
            FNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_FName"));
            MNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_MName"));
            LNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LName"));
            CNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_CNumber"));
            CPersonNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_CPersonNum"));
            SexColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Sex"));
            BDateColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Birthdate"));
            HouseNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_HouseNum"));
            StreetColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Street"));
            BrgyColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Brgy"));
            CityColumn.setCellValueFactory(new PropertyValueFactory<>("driver_City"));
            CarPlateColumn.setCellValueFactory(new PropertyValueFactory<>("car_Plate"));
        }
    
    //add driver feature
    public void add(ActionEvent event) {
        try {
            if (isValidData()) {
                String fName = addFName.getText();
                String mName = addMName.getText();
                String lName = addLName.getText();
                String licenseNum = addLicenseNum.getText();
                String licenseExpiry = addLicenseNumExpiry.getValue().toString();
                String contactNum = addContactNum.getText();
                String cPersonNum = addCPersonNum.getText();
                String houseNum = addHouseNum.getText();
                String block = addBlock.getText();
                String street = addStreet.getText();
                String brgy = addBrgy.getText();
                String city = addCity.getText();
                String sex = addSex.getText();
                String birthDate = addBirthDate.getValue().toString();
                String carPlate = addCarPlate.getText();
    
                try (Connection connection = DbConnect.getConnect()) {
                    // Insert into car table
                    String driverInsertQuery = "INSERT INTO driver (driver_LicenseNum, driver_LicenseExpiry, driver_CPersonNum, driver_CNumber, driver_HouseNum, driver_Block, driver_Brgy, driver_Street, driver_City, driver_Sex, driver_Birthdate, driver_FName, driver_MName, driver_LName, car_Plate) VALUES (?, ? , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
                    try (PreparedStatement driverStatement = connection.prepareStatement(driverInsertQuery)) {
                        driverStatement.setString(1, licenseNum);
                        driverStatement.setString(2, licenseExpiry);
                        driverStatement.setString(3, cPersonNum);
                        driverStatement.setString(4, contactNum);
                        driverStatement.setString(5, houseNum);
                        driverStatement.setString(6, block);
                        driverStatement.setString(7, brgy);
                        driverStatement.setString(8, street);
                        driverStatement.setString(9, city);
                        driverStatement.setString(10, brgy);
                        driverStatement.setString(11, city);
                        driverStatement.setString(12, sex);
                        driverStatement.setString(13, birthDate);
                        driverStatement.setString(14, fName);
                        driverStatement.setString(15, mName);
                        driverStatement.setString(16, lName);
                        driverStatement.setString(17, carPlate);
                        

                        driverStatement.executeUpdate();
                    }
                    showSuccessAlert("Data inserted successfully");
                }
            } else {
                showErrorAlert("Invalid data. Please check your input.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error inserting data", e);
        }
    }

    private void showErrorAlert(String message, SQLException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message + "\nError Details: " + e.getMessage()); // Display the error message from SQLException
        alert.showAndWait();
    }
    

    private boolean isValidData() {
        if (addFName.getText().isEmpty() || addMName.getText().isEmpty() || addLName.getText().isEmpty() ||
                addLicenseNum.getText().isEmpty() || addLicenseNumExpiry.getValue() == null || addContactNum.getText().isEmpty() ||
                addCPersonNum.getText().isEmpty() || addHouseNum.getText().isEmpty() || addBlock.getText().isEmpty() ||
                addStreet.getText().isEmpty() || addBrgy.getText().isEmpty() || addCity.getText().isEmpty() ||
                addSex.getText().isEmpty() || addBirthDate.getValue() == null || addCarPlate.getText().isEmpty()) {
            return false;
        }
        return true;
    }

    private void showSuccessAlert(String message) {
         Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(message);
             alert.showAndWait();
}       

    private void showErrorAlert(String message) {
     Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
             alert.showAndWait();
}

    /*private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
             alert.showAndWait();
}       */
        
    @FXML
    public Pane addDriver, addDriver2, updateDriver, deleteDriver;

    public void showAddDriverPane(ActionEvent event) {
        addDriver.setVisible(true);
    }    

    public void showAddDriverPane2(ActionEvent event) {
        addDriver2.setVisible(true);
    }    

    public void showUpdateDriverPane(ActionEvent event) {
        updateDriver.setVisible(true);
    }    

    public void showDeleteDriverPane(ActionEvent event) {
        deleteDriver.setVisible(true);
    }   

    public void hideDeleteDriverPane(ActionEvent event) {
        deleteDriver.setVisible(false);
    }

     public void hideAddDriverPane(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Discard Input");
        
        Label contentLabel = new Label("Are you sure you want to discard the inputs?");
        contentLabel.setStyle("-fx-font-size: 14px;"); 

        dialog.getDialogPane().setContent(contentLabel);


        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/Images/attention.png")));
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);

        Label iconLabel = new Label("", iconView);

        HBox hBox = new HBox(iconLabel);
        HBox.setHgrow(iconLabel, Priority.ALWAYS);
        hBox.getChildren().add(new Label("Are you sure you want to discard the inputs?"));
        dialog.getDialogPane().setContent(hBox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addDriver.setVisible(false);
            addDriver2.setVisible(false);
        }
    }
    
    public void hideUpdateDriverPane(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Discard Input");
        
        Label contentLabel = new Label("Are you sure you want to discard the inputs?");
        contentLabel.setStyle("-fx-font-size: 14px;"); 

        dialog.getDialogPane().setContent(contentLabel);


        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/Images/attention.png")));
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);

        Label iconLabel = new Label("", iconView);

        HBox hBox = new HBox(iconLabel);
        HBox.setHgrow(iconLabel, Priority.ALWAYS);
        hBox.getChildren().add(new Label("Are you sure you want to discard the inputs?"));
        dialog.getDialogPane().setContent(hBox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            updateDriver.setVisible(false);
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

    public void GoToD_Quota(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Driver_Quota.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

}