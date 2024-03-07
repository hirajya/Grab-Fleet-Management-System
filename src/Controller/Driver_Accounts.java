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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
import model.amortization;
import model.driver;
import model.driver_table;
import model.object_model.Driver_Quota_obj;

public class Driver_Accounts {

    @FXML
    private TableView<driver> driverTable;

    @FXML
    private TableColumn<driver, String> licenseNumColumn;

    @FXML
    private TableColumn<driver, Date> licenseNumExpiryColumn;

    @FXML
    private TableColumn<driver, String> FNameColumn;

    @FXML
    private TableColumn<driver, String> MNameColumn;

    @FXML
    private TableColumn<driver, String> LNameColumn;

    @FXML
    private TableColumn<driver, String> CNumColumn;

    @FXML
    private TableColumn<driver, String> CPersonNumColumn;

    @FXML
    private TableColumn<driver, String> SexColumn;

    @FXML
    private TableColumn<driver, Date> BDateColumn;

    @FXML
    private TableColumn<driver, Integer> HouseNumColumn;

    @FXML
    private TableColumn<driver, String> BlockColumn;

    @FXML
    private TableColumn<driver, String> StreetColumn;

    @FXML
    private TableColumn<driver, String> BrgyColumn;

    @FXML
    private TableColumn<driver, String> CityColumn;

    @FXML
    public TableColumn<driver, String> CarPlateColumn;

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

    @FXML 
    private DatePicker datePicker3, datePicker4;

    @FXML
    private TextArea quotaAmount;

    @FXML
    private TextArea updateLicenseNum, updateFName, updateMName, updateLName, updateCNumber, updateCPersonNum, updateHouseNum, updateBlock, updateStreet, updateBrgy, updateCity, updateSex, updateCarPlate;

    @FXML
    private DatePicker updateBirthdate, updateLicenseExpiry;

    @FXML
    private TextField searchTextField;
    
    private static String tableName = "driver";


    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    driver driver = null;

    ObservableList<driver> driverList = FXCollections.observableArrayList();

    public void initialize() { 
        loadDate();

        driverTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        driverTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                printSelectedRowData(newSelection);
                bindSelectedRowData(newSelection);
            }
        });
        configureDatePickers();


    }
    @FXML
    public void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        licenseNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LicenseNum"));
        CNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_CNumber"));
        CPersonNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_CPersonNum"));
        SexColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Sex"));
        FNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_FName"));
        MNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_MName"));
        LNameColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LName"));
        BDateColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Birthdate"));
        HouseNumColumn.setCellValueFactory(new PropertyValueFactory<>("driver_HouseNum"));
        CityColumn.setCellValueFactory(new PropertyValueFactory<>("driver_City"));
        StreetColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Street"));
        BlockColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Block"));
        BrgyColumn.setCellValueFactory(new PropertyValueFactory<>("driver_Brgy"));
        CarPlateColumn.setCellValueFactory(new PropertyValueFactory<>("car_Plate"));
        licenseNumExpiryColumn.setCellValueFactory(new PropertyValueFactory<>("driver_LicenseExpiry"));
        
        }


    private void printSelectedRowData(driver selectedDriver){
        System.out.println("Selected row data: " + selectedDriver.getDriver_LicenseNum());
    }

    private void configureDatePickers() {
        StringConverter <LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
            
        };
        addBirthDate.setConverter(converter);
        addLicenseNumExpiry.setConverter(converter);
        datePicker3.setConverter(converter);
        datePicker4.setConverter(converter);

    }

    private void bindSelectedRowData(driver selectedDriver){
        updateLicenseNum.setText(selectedDriver.getDriver_LicenseNum());
        updateFName.setText(selectedDriver.getDriver_FName());
        updateMName.setText(selectedDriver.getDriver_MName());
        updateLName.setText(selectedDriver.getDriver_LName());
        updateCNumber.setText(selectedDriver.getDriver_CNumber());
        updateCPersonNum.setText(selectedDriver.getDriver_CPersonNum());
        updateHouseNum.setText(String.valueOf(selectedDriver.getDriver_HouseNum()));
        updateBlock.setText(selectedDriver.getDriver_Block());
        updateStreet.setText(selectedDriver.getDriver_Street());
        updateBrgy.setText(selectedDriver.getDriver_Brgy());
        updateCity.setText(selectedDriver.getDriver_City());
        updateSex.setText(selectedDriver.getDriver_Sex());
        updateCarPlate.setText(selectedDriver.getCar_Plate());
        updateBirthdate.setValue(selectedDriver.getDriver_Birthdate().toLocalDate());
        updateLicenseExpiry.setValue(selectedDriver.getDriver_LicenseExpiry().toLocalDate());
    }

    public void updateDriver(ActionEvent event){
        if (driverTable.getSelectionModel().getSelectedItem().getDriver_LicenseNum() != null) {
           
                String newlicenseNum = updateLicenseNum.getText();
                String newFName = updateFName.getText();
                String newMName = updateMName.getText();
                String newLName = updateLName.getText();
                String newContactNum = updateCNumber.getText();
                String newcPersonNum = updateCPersonNum.getText();
                int newHouseNum = Integer.parseInt(updateHouseNum.getText());
                String newBlock = updateBlock.getText();
                String newStreet = updateStreet.getText();
                String newBrgy = updateBrgy.getText();
                String newCity = updateCity.getText();
                String newSex = updateSex.getText();
                String newcarPlate = updateCarPlate.getText();
                LocalDate newbirthDate = updateBirthdate.getValue();
                LocalDate newlicenseExpiry = updateLicenseExpiry.getValue();

                String LicenseNum = driverTable.getSelectionModel().getSelectedItem().getDriver_LicenseNum();

                String updateCarQuery = "UPDATE driver SET driver_LicenseNum = ?, driver_FName = ?, driver_MName = ?, driver_LName = ?, driver_CNumber = ?, driver_CPersonNum = ?, driver_HouseNum = ?, driver_Block = ?, driver_Street = ?, driver_Brgy = ?, driver_City = ?, driver_Sex = ?, driver_Birthdate = ?, driver_LicenseExpiry = ?, car_Plate = ? WHERE driver_LicenseNum = ?";

                try (Connection connection = DbConnect.getConnect()) {
                    try (PreparedStatement preparedStatement = connection.prepareStatement(updateCarQuery)) {
                        preparedStatement.setString(1, newlicenseNum);
                        preparedStatement.setString(2, newFName);
                        preparedStatement.setString(3, newMName);
                        preparedStatement.setString(4, newLName);
                        preparedStatement.setString(5, newContactNum);
                        preparedStatement.setString(6, newcPersonNum);
                        preparedStatement.setInt(7, newHouseNum);
                        preparedStatement.setString(8, newBlock);
                        preparedStatement.setString(9, newStreet);
                        preparedStatement.setString(10, newBrgy);
                        preparedStatement.setString(11, newCity);
                        preparedStatement.setString(12, newSex);
                        preparedStatement.setDate(13, java.sql.Date.valueOf(newbirthDate));
                        preparedStatement.setDate(14, java.sql.Date.valueOf(newlicenseExpiry));
                        preparedStatement.setString(15, newcarPlate);
                        preparedStatement.setString(16, LicenseNum);
                        preparedStatement.executeUpdate();

                    }
                    showSuccessAlert("Driver updated successfully");
                } catch (SQLException e) {
                    e.printStackTrace();
                    showErrorAlert("Error updating driver");
                }
            }
            refreshTable();
        }

    public void deleteDriver(ActionEvent event) {
        
        }

    public void discardUpdate(ActionEvent event){
        updateLicenseNum.clear();
        updateFName.clear();
        updateMName.clear();
        updateLName.clear();
        updateCNumber.clear();
        updateCPersonNum.clear();
        updateHouseNum.clear();
        updateBlock.clear();
        updateStreet.clear();
        updateBrgy.clear();
        updateCity.clear();
        updateSex.clear();
        updateCarPlate.clear();
        updateBirthdate.getEditor().clear();
        updateLicenseExpiry.getEditor().clear();
    
    }

    public void addDriver(ActionEvent event){
        try {
            if (isValidData()){
                String fName = addFName.getText();
                String mName = addMName.getText();
                String lName = addLName.getText();
                String licenseNum = addLicenseNum.getText();
                LocalDate licenseExpiry = addLicenseNumExpiry.getValue();
                String contactNum = addContactNum.getText();
                String cPersonNum = addCPersonNum.getText();
                String houseNum = addHouseNum.getText();
                String block = addBlock.getText();
                String street = addStreet.getText();
                String brgy = addBrgy.getText();
                String city = addCity.getText();
                String sex = addSex.getText();
                LocalDate birthDate = addBirthDate.getValue();
                String carPlate = addCarPlate.getText();
                LocalDate qStart = datePicker3.getValue();
                LocalDate qEnd = datePicker4.getValue();
                int qAmount = Integer.parseInt(quotaAmount.getText());

            try (Connection connection = DbConnect.getConnect()){

                String driverInsertQuery = "INSERT INTO driver (driver_LicenseNum, driver_CNumber, driver_CPersonNum, driver_Sex, driver_FName, driver_MName, driver_LName, driver_Birthdate, driver_HouseNum, driver_City, driver_Street, driver_Block, driver_Brgy, car_Plate, driver_LicenseExpiry) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                
                try (PreparedStatement driverStatement = connection.prepareStatement(driverInsertQuery)){
                    driverStatement.setString(1, licenseNum);
                    driverStatement.setString(2, contactNum);
                    driverStatement.setString(3, cPersonNum);
                    driverStatement.setString(4,sex);
                    driverStatement.setString(5, fName);
                    driverStatement.setString(6, mName);
                    driverStatement.setString(7, lName);
                    driverStatement.setDate(8, java.sql.Date.valueOf(birthDate));
                    driverStatement.setString(9, houseNum);
                    driverStatement.setString(10, city);
                    driverStatement.setString(11, street);
                    driverStatement.setString(12, block);
                    driverStatement.setString(13, brgy);
                    driverStatement.setString(14, carPlate);
                    driverStatement.setDate(15, java.sql.Date.valueOf(licenseExpiry));

                    
                    driverStatement.executeUpdate();
                }

                String quotaInsertQuery = "INSERT INTO quota (quota_Amount, quota_SDate, quota_DDate, driver_LicenseNum, quota_Balance) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement quotaStatement = connection.prepareStatement(quotaInsertQuery)){
                    quotaStatement.setInt(1, qAmount);
                    quotaStatement.setDate(2, java.sql.Date.valueOf(qStart));
                    quotaStatement.setDate(3, java.sql.Date.valueOf(qEnd));
                    quotaStatement.setString(4, licenseNum);

                    int qBalance = qAmount - 0;
                    quotaStatement.setInt(5, qBalance);

                    quotaStatement.executeUpdate();
                }

                showSuccessAlert("Driver added successfully");
            } 
        } else {
                showErrorAlert("Invalid data. Please check your input.");
        }
    } catch (SQLException | NumberFormatException e) {
        e.printStackTrace();
        showErrorAlert("Error adding driver", e);
    }
}
    




    //display sa table
    @FXML
    public void refreshTable() {
        try {
            driverList.clear();
    
            // Check if searchTextField is not null before using it
            if (searchTextField != null) {
                String searchKeyword = searchTextField.getText();
    
                // Modify your SQL query to search by driver_FName
                String query = "SELECT * FROM driver WHERE driver_FName LIKE ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, "%" + searchKeyword + "%");
                resultSet = preparedStatement.executeQuery();
    
                while (resultSet.next()) {
                    driverList.add(new driver(
                            resultSet.getString("driver_LicenseNum"),
                            resultSet.getString("driver_CNumber"),
                            resultSet.getString("driver_CPersonNum"),
                            resultSet.getString("driver_Sex"),
                            resultSet.getString("driver_FName"),
                            resultSet.getString("driver_MName"),
                            resultSet.getString("driver_LName"),
                            resultSet.getDate("driver_Birthdate"),
                            resultSet.getInt("driver_HouseNum"),
                            resultSet.getString("driver_City"),
                            resultSet.getString("driver_Street"),
                            resultSet.getString("driver_Block"),
                            resultSet.getString("driver_Brgy"),
                            resultSet.getString("car_Plate"),
                            resultSet.getDate("driver_LicenseExpiry")
                    ));
                }
    
                driverTable.setItems(driverList);
    
            } else {
                // Handle the case when searchTextField is null
                System.out.println("searchTextField is null.");
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    

    @FXML
    private void handleSearch(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
            refreshTable();
        }
    }
    
    
    private void showErrorAlert(String message, Exception e) {
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
       public static int countDrivers() {
        int count = 0;
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM " + tableName)) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
