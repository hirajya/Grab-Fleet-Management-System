package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.scene.text.Text;
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
    public TextField confirmationTextField;

    @FXML
    public Text deleteText;

    @FXML
    private Button deleteButtonGoPush;


   
    /*String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    */

    private ObservableList<Driver_Accounts_obj> originalData;

    public void initialize() { 
        deleteText.setVisible(false);
        setUpColumns();
        
        originalData = FXCollections.observableArrayList(model.driver_table.getDriverData());

        driver_acc_table.getItems().addAll(originalData);

        // originalData = FXCollections.observableArrayList(model.driver_table.getDriverData());

        // List<Driver_Accounts_obj> driverData = model.driver_table.getDriverData();
        // driver_acc_table.getItems().addAll(driverData);

        setDatePickerFormat(datePicker1);
        setDatePickerFormat(datePicker2);
        setDatePickerFormat(datePicker3);
        setDatePickerFormat(datePicker4);
        setDatePickerFormat(datePicker5);
        setDatePickerFormat(datePicker6);
    }

    private void setDatePickerFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
    };
    
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

    public void hideDeleteDriverPane() {
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


    @FXML
    private DatePicker datePicker1, datePicker2, datePicker3, datePicker4, datePicker5, datePicker6;

    //public void initialize() {
        

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

    public void deleteCarDriver() throws ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Check if the text inside the confirmationTextField is "DELETE"
            if (isDeleteText(confirmationTextField)) {
                Driver_Accounts_obj selectedDriver = driver_acc_table.getSelectionModel().getSelectedItem();
                if (selectedDriver != null) {
                    String recordID = selectedDriver.getDriver_LicenseNum();
                    String deleteQuery = "DELETE FROM driver WHERE driver_LicenseNum = ?";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(url, user, password);
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                        deleteStatement.setString(1, recordID);
                        int rowsAffected = deleteStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("Row deleted successfully.");
                            hideDeleteDriverPane();
                            refreshTable();
                        } else {
                            System.out.println("Failed to delete row.");
                        }
                    }
                }
            } else {
                showNoDeleteMsg();
                System.out.println("Deletion cancelled. Text does not match 'DELETE'.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean isDeleteText(TextField textField) {
        String text = textField.getText().trim();
        return "DELETE".equalsIgnoreCase(text);
    }

    public void refreshTable() {
        // Clear existing data
        driver_acc_table.getItems().clear();

        // Fetch new data and add it to the table
        List<Driver_Accounts_obj> updatedData = model.driver_table.getDriverData();
        originalData.setAll(updatedData);

        // Add the updated data to the table
        driver_acc_table.getItems().addAll(originalData);
    }

    public void showNoDeleteMsg () {
        deleteText.setVisible(true);
    }

    



}