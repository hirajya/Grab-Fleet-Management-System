package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.DbConnect;
import model.amortization;
import model.amortization_table;
import model.car;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Car_Accounts implements Initializable {
    @FXML
    private TableView<car> carTable;
    @FXML
    private TableColumn<car, String> carplateColumn;
    @FXML
    private TableColumn<car, String> CRcolumn; 
    @FXML
    private TableColumn<car, String> ORcolumn;
    @FXML 
    private TableColumn<car, String> seriesColumn;
    @FXML
    private TableColumn<car, String> kindColumn;
    @FXML
    private TableColumn<car, Integer> yearColumn;
    @FXML
    private TableColumn<car, String> colorColumn;
    @FXML
    private TableColumn<car, String> registrationColumn;
    @FXML
    private TableColumn<car, String> expiryColumn;
    @FXML
    private TableColumn<car, String> statusColumn;
    @FXML
    private TableColumn<car, String> availabilityColumn;

    @FXML
    private TextField addPlate;

    @FXML
    private TextField addCRNum;

    @FXML 
    private TextField addORNum;

    @FXML
    private TextField addSeries;

    @FXML
    private TextField addKind;

    @FXML
    private TextField addYearModel;

    @FXML 
    private TextField addColor;

    @FXML 
    private DatePicker addCarReg;

    @FXML
    private DatePicker addCarRegExpiry;

    @FXML
    private DatePicker addCarChangeOil;

    @FXML
    private DatePicker addCarChangeBelt;

    @FXML
    private DatePicker addAmortizationSDate;

    @FXML
    private DatePicker addAmortizationEDate;

    @FXML
    private DatePicker addAmortizationDDate;

    @FXML
    private TextField addAmortizationPayment;

    @FXML
    private TextField updateCarPlate, updateCRNum, updateORNum, updateSeries, updateKind, updateYearModel, updateColor;

    @FXML
    private DatePicker updateCarReg, updateCarRegExpiry;



    @FXML
    private ComboBox<String> seriesComboBox;

    @FXML
    private ComboBox<String> kindComboBox;

    @FXML
    private ComboBox<String> yearComboBox;

    @FXML
    private ComboBox<String> colorComboBox;

    @FXML
    private ComboBox<String> RegstatusComboBox;

    @FXML
    private ComboBox<String> AvailabilityComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button addCarButton;

    @FXML
    private Pane addCarPane;

    @FXML
    private Pane addCarPane2;

    @FXML
    private Pane carAccPane;

    @FXML 
    private Pane updateCarPane;

    @FXML
    private Button backButtonCarAcc;

    @FXML
    private Button deleteButton, discardButtonDelete;

    @FXML
    private Text deleteText;

    @FXML
    private TextField confirmationTextField;

    @FXML
    private Pane deletePane;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    car car = null;

    ObservableList<car> carList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadDate();
         setupSeriesComboBox();
           setupKindComboBox();
            setupYearComboBox();
            setupColorComboBox();
        setupRegStatusComboBox();
           setupAvailabilityComboBox();

            // Set the selection mode to SINGLE to allow only one row to be selected at a time
        carTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Add a selection listener to the table
        carTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Handle the selected row, you can print the data or perform any other action
                printSelectedRowData(newSelection);
                bindSelectedRowData(newSelection);
            }
        });

        // Configure date pickers to display dates in the format you desire
        configureDatePickers();
           
    }

    private void printSelectedRowData(car selectedCar) {
        System.out.println("Selected: " + selectedCar.getCar_Plate() + " " + selectedCar.getCar_CRNum() + " " + selectedCar.getCar_Series() + " " + selectedCar.getCar_Kind() + " " + selectedCar.getCar_YearModel() + " " + selectedCar.getCar_Color() + " " + selectedCar.getCar_ORNum() + " " + selectedCar.getCar_Registration() + " " + selectedCar.getCar_RegExpiry() + " " + selectedCar.getCar_RegStatus() + " " + selectedCar.getCar_Availability());
    }

    private void configureDatePickers() {
        // Configure StringConverter to format dates in the desired way
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        // Set the string converter to the date picker
        updateCarReg.setConverter(converter);
        updateCarRegExpiry.setConverter(converter);

    }

    private void bindSelectedRowData(car selectedCar) {
        // Bind selected row data to the controls
        updateCarPlate.setText(selectedCar.getCar_Plate());
        updateCRNum.setText(selectedCar.getCar_CRNum());
        updateORNum.setText(selectedCar.getCar_ORNum());
        updateSeries.setText(selectedCar.getCar_Series());
        updateKind.setText(selectedCar.getCar_Kind());
        updateYearModel.setText(String.valueOf(selectedCar.getCar_YearModel()));
        updateColor.setText(selectedCar.getCar_Color());
        updateCarReg.setValue(selectedCar.getCar_Registration().toLocalDate());
        updateCarRegExpiry.setValue(selectedCar.getCar_RegExpiry().toLocalDate());
        
    }

    public void updateCarAccounts(ActionEvent event){

        if (carTable.getSelectionModel().getSelectedItem().getCar_Plate() == null) {
            showAlert("No Selected Data", "Please select a car from the table to update.");
            return; // Exit the method if no item is selected
        }
        String newPlate = updateCarPlate.getText();
        String newCRNum = updateCRNum.getText();
        String newORNum = updateORNum.getText();
        String newSeries = updateSeries.getText();
        String newKind = updateKind.getText();
        int newYearModel = Integer.parseInt(updateYearModel.getText());
        String newColor = updateColor.getText();
        LocalDate newReg = updateCarReg.getValue();
        LocalDate newRegExpiry = updateCarRegExpiry.getValue();

        String carPlate = carTable.getSelectionModel().getSelectedItem().getCar_Plate();

        String updateCarQuery = "UPDATE car SET car_Plate = ?, car_CRNum = ?, car_ORNum = ?, car_Series = ?, car_Kind = ?, car_YearModel = ?, car_Color = ?, car_Registration = ?, car_RegExpiry = ? WHERE car_Plate = ?";
        try (Connection connection = DbConnect.getConnect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateCarQuery)) {
                preparedStatement.setString(1, newPlate);
                preparedStatement.setString(2, newCRNum);
                preparedStatement.setString(3, newORNum);
                preparedStatement.setString(4, newSeries);
                preparedStatement.setString(5, newKind);
                preparedStatement.setInt(6, newYearModel);
                preparedStatement.setString(7, newColor);
                preparedStatement.setDate(8, java.sql.Date.valueOf(newReg));
                preparedStatement.setDate(9, java.sql.Date.valueOf(newRegExpiry));
                preparedStatement.setString(10, carPlate);
                preparedStatement.executeUpdate();
            }
            showSuccessAlert("Car updated successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error updating car");
        }
        GoCarAccounts();
        refreshTable();



    }
    
    public void deleteCarAccs() {
        try {
            car selectedCar = carTable.getSelectionModel().getSelectedItem();
    
            if (selectedCar != null) {
                String confirmationText = confirmationTextField.getText().trim();
    
                if (!"DELETE".equalsIgnoreCase(confirmationText)) {
                    showNoDeleteMsg();
                    System.out.println("Deletion cancelled. Text does not match 'DELETE'.");
                    return;
                }
    
                String carPlateRecord = selectedCar.getCar_Plate();
                String deleteQuery = "DELETE FROM car WHERE car_Plate = ?";
    
                try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grab-fleet-database", "root", "");
                     PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
    
                    deleteStatement.setString(1, carPlateRecord);
                    int rowsAffected = deleteStatement.executeUpdate();
    
                    if (rowsAffected > 0) {
                        System.out.println("Row deleted successfully.");
                        GoCarView();
                        refreshTable();
                    } else {
                        System.out.println("Failed to delete row.");
                    }
                }
            } else {
                showErrorAlert("Please select a row to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void GoCarView() {
        
        deletePane.setVisible(false);
        removeBlur(carAccPane);
        carAccPane.setVisible(true);
    }

    public static void applyBlur(Pane pane) {
        BoxBlur boxBlur = new BoxBlur();
        boxBlur.setWidth(5);
        boxBlur.setHeight(5);
        boxBlur.setIterations(3);

        pane.setEffect(boxBlur);
    }

    public static void removeBlur(Pane pane) {
        pane.setEffect(null);
    }

    public static boolean isDeleteText(TextField textField) {
        String text = textField.getText().trim();
        return "DELETE".equalsIgnoreCase(text);
    }

    public void showNoDeleteMsg () {
        deleteText.setVisible(true);
    }

    public void GoDeleteCarAcc() {
        confirmationTextField.clear();
        deleteText.setVisible(false);
        try {
            if (carTable.getSelectionModel().getSelectedItem() == null) {
                showAlert("No Selected Data", "Please select a car from the table to delete.");
                return; // Exit the method if no item is selected
            }
            applyBlur(carAccPane);
            deletePane.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error in GoQuotaView");
        }
    }


    public void discardUpdate(ActionEvent event) {
        // Clear all text fields
        addPlate.clear();
        addCRNum.clear();
        addORNum.clear();
        addSeries.clear();
        addKind.clear();
        addYearModel.clear();
        addColor.clear();
        addCarReg.setValue(null);
        addCarRegExpiry.setValue(null);
        addCarChangeOil.setValue(null);
        addCarChangeBelt.setValue(null);
        addAmortizationSDate.setValue(null);
        addAmortizationEDate.setValue(null);
        addAmortizationDDate.setValue(null);
        addAmortizationPayment.clear();
        GoCarAccounts();
        refreshTable();
    }
    public void save(ActionEvent event) {
        try {
            if (isValidData()) {
                String plate = addPlate.getText();
                String CRNum = addCRNum.getText();
                String ORNum = addORNum.getText();
                String series = addSeries.getText();
                String kind = addKind.getText();
                int yearModel = Integer.parseInt(addYearModel.getText());
                String color = addColor.getText();
                String registration = addCarReg.getValue().toString();
                String regExpiry = addCarRegExpiry.getValue().toString();
                String changeOil = addCarChangeOil.getValue().toString();
                String changeBelt = addCarChangeBelt.getValue().toString();
                String amortizationSDate = addAmortizationSDate.getValue().toString();
                String amortizationEDate = addAmortizationEDate.getValue().toString();
                String amortizationDDate = addAmortizationDDate.getValue().toString();
                int amortizationPayment = Integer.parseInt(addAmortizationPayment.getText());
    
                try (Connection connection = DbConnect.getConnect()) {
                    // Insert into car table
                    String carInsertQuery = "INSERT INTO car (car_Plate, car_CRNum, car_Series, car_Kind, car_YearModel, car_Color, car_ORNum, car_RegExpiry, car_Registration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
                    try (PreparedStatement carStatement = connection.prepareStatement(carInsertQuery)) {
                        carStatement.setString(1, plate);
                        carStatement.setString(2, CRNum);
                        carStatement.setString(3, series);
                        carStatement.setString(4, kind);
                        carStatement.setInt(5, yearModel);
                        carStatement.setString(6, color);
                        carStatement.setString(7, ORNum);
                        carStatement.setDate(8, java.sql.Date.valueOf(regExpiry));
                        carStatement.setDate(9, java.sql.Date.valueOf(registration));
    
                        carStatement.executeUpdate();
                    }
    
                    // Insert into maintenance table
                    String maintenanceInsertQuery = "INSERT INTO maintenance (maintenance_ChangeOil, maintenance_ChangeBelt, car_Plate) VALUES (?, ?, ?)";
                    try (PreparedStatement maintenanceStatement = connection.prepareStatement(maintenanceInsertQuery)) {
                        maintenanceStatement.setDate(1, java.sql.Date.valueOf(changeOil));
                        maintenanceStatement.setDate(2, java.sql.Date.valueOf(changeBelt));
                        maintenanceStatement.setString(3, plate);
                        maintenanceStatement.executeUpdate();
                    }
    
                    // Insert into amortization table
                    String amortizationInsertQuery = "INSERT INTO amortization (amortization_SDate, amortization_DDate, amortization_EDate, amortization_Payment, car_Plate) " +
                            "VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement amortizationStatement = connection.prepareStatement(amortizationInsertQuery)) {
                        amortizationStatement.setDate(1, java.sql.Date.valueOf(amortizationSDate));
                        amortizationStatement.setDate(2, java.sql.Date.valueOf(amortizationDDate));
                        amortizationStatement.setDate(3, java.sql.Date.valueOf(amortizationEDate));
                        amortizationStatement.setInt(4, amortizationPayment);
                        amortizationStatement.setString(5, plate);
                        amortizationStatement.executeUpdate();
    
                    }
                    showSuccessAlert("Data inserted successfully");
    
                }
    
            } else {
                showErrorAlert("Invalid data. Please check your input.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Error inserting data");
        }
        GoCarAccounts();
        refreshTable();
    }
    
    private boolean isValidData() {
        if (addPlate.getText().isEmpty() || addCRNum.getText().isEmpty() || addSeries.getText().isEmpty() ||
                addKind.getText().isEmpty() || addYearModel.getText().isEmpty() || addColor.getText().isEmpty() ||
                addCarReg.getValue() == null || addCarRegExpiry.getValue() == null) {
            return false;
        }
    
        try {
            int yearModel = Integer.parseInt(addYearModel.getText());
            int amortizationPayment = Integer.parseInt(addAmortizationPayment.getText());
    
            if (yearModel < 1900 || yearModel > 2024 || amortizationPayment <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
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

private void showAlert(String title, String content) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(content);
    alert.showAndWait();
}

   



    private void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        carplateColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Plate"));
        CRcolumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_CRNum"));
        seriesColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Series"));
        kindColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Kind"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<car, Integer>("car_YearModel"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Color"));
        ORcolumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_ORNum"));
        registrationColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Registration"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_RegExpiry"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_RegStatus"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Availability"));
    }

    

    @FXML
    public void refreshTable() {
        try {
            carList.clear();
    
            String selectedSeries = seriesComboBox.getValue();
            String selectedKind = kindComboBox.getValue();
            String selectedYearModel = yearComboBox.getValue();
            String selectedColor = colorComboBox.getValue();
            String selectedRegStatus = RegstatusComboBox.getValue();
            String selectedAvailability = AvailabilityComboBox.getValue();
            String searchKeyword = searchTextField.getText();
    
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM car WHERE car_Plate LIKE ?");
    
            if (selectedSeries != null && !selectedSeries.equals("All")) {
                queryBuilder.append(" AND car_Series = ?");
            }
    
            if (selectedKind != null && !selectedKind.equals("All")) {
                queryBuilder.append(" AND car_Kind = ?");
            }
    
            if (selectedYearModel != null && !selectedYearModel.equals("All")) {
                queryBuilder.append(" AND car_YearModel = ?");
            }
    
            if (selectedColor != null && !selectedColor.equals("All")) {
                queryBuilder.append(" AND car_Color = ?");
            }
    
            if (selectedRegStatus != null && !selectedRegStatus.equals("All")) {
                queryBuilder.append(" AND car_RegStatus = ?");
            }
    
            if (selectedAvailability != null && !selectedAvailability.equals("All")) {
                queryBuilder.append(" AND car_Availability = ?");
            }
    
            preparedStatement = connection.prepareStatement(queryBuilder.toString());
            preparedStatement.setString(1, "%" + searchKeyword + "%");
    
            int parameterIndex = 2;
    
            if (selectedSeries != null && !selectedSeries.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedSeries);
            }
    
            if (selectedKind != null && !selectedKind.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedKind);
            }
    
            if (selectedYearModel != null && !selectedYearModel.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedYearModel);
            }
    
            if (selectedColor != null && !selectedColor.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedColor);
            }
    
            if (selectedRegStatus != null && !selectedRegStatus.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedRegStatus);
            }
    
            if (selectedAvailability != null && !selectedAvailability.equals("All")) {
                preparedStatement.setString(parameterIndex++, selectedAvailability);
            }
    
            resultSet = preparedStatement.executeQuery();
    
            while (resultSet.next()) {
                carList.add(new car(
                        resultSet.getString("car_Plate"),
                        resultSet.getString("car_CRNum"),
                        resultSet.getString("car_Series"),
                        resultSet.getString("car_Kind"),
                        resultSet.getInt("car_YearModel"),
                        resultSet.getString("car_Color"),
                        resultSet.getString("car_ORNum"),
                        resultSet.getDate("car_RegExpiry"),
                        resultSet.getDate("car_Registration"),
                        resultSet.getString("car_RegStatus"),
                        resultSet.getString("car_Availability")
                ));
            }
            carTable.setItems(carList);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    

        


    @FXML
private void handleSearch(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        refreshTable();
    }
}

    
    public void GoCarAccounts(){
        carAccPane.setVisible(true);
        addCarPane.setVisible(false);
        addCarPane2.setVisible(false);
        updateCarPane.setVisible(false);

    }

    public void GoAddCar() {
        carAccPane.setVisible(false);
        addCarPane.setVisible(true);
    }

    public void GoCarAcc() {
        carAccPane.setVisible(true);
        addCarPane.setVisible(false);
    }

    public void GoUpdateCar() {
        try {
            if (carTable.getSelectionModel().getSelectedItem() == null) {
                showAlert("No Selected Data", "Please select a car from the table to update.");
                return; // Exit the method if no item is selected
            }
    
            updateCarPane.setVisible(true);
            carAccPane.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error in GoUpdateCar");
        }
    }
    

    public void GoAddCar2(){
        addCarPane2.setVisible(true);
        addCarPane.setVisible(false);
        carAccPane.setVisible(false);
    }

    public void GoBackAddCar(){
        addCarPane2.setVisible(false);
        addCarPane.setVisible(true);
        carAccPane.setVisible(false);
    }

    
    


    public void GoToHome(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void GoToC_Amortization(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Amortization.fxml"));

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

    public void GoToNotif(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Notification.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void GoToAdmin(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Notification.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    private void setupSeriesComboBox() {
        seriesComboBox.setItems(FXCollections.observableArrayList("All", "Crosswind", "Adventure","Innova","Mirage","Vios","Avanza","Stargazer"));
        seriesComboBox.setValue("All");
    }

    private void setupKindComboBox() {
        kindComboBox.setItems(FXCollections.observableArrayList("All", "MPV", "Sedan"));
        kindComboBox.setValue("Kind");
    }

    private void setupYearComboBox() {
        yearComboBox.setItems(FXCollections.observableArrayList("All", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023"));    
        yearComboBox.setValue("All");
    }

    private void setupColorComboBox() {
        colorComboBox.setItems(FXCollections.observableArrayList("All", "Black", "White", "Red", "Blue", "Green", "Yellow", "Orange", "Violet", "Indigo"));
        colorComboBox.setValue("All");
    }

    private void setupRegStatusComboBox() {
        RegstatusComboBox.setItems(FXCollections.observableArrayList("All", "Up to date", "Due Soon", "Expired"));
        RegstatusComboBox.setValue("All");
    }

    private void setupAvailabilityComboBox() {
        AvailabilityComboBox.setItems(FXCollections.observableArrayList("All", "Available", "Unavailable"));
        AvailabilityComboBox.setValue("All");
        
    }

    
    @FXML
    private void handleFilterChange(ActionEvent event) {
        refreshTable();
    }
    
    
}