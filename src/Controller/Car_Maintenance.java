package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.DbConnect;
import model.maintenance;
import model.maintenance_table;
import model.object_model.Driver_Quota_obj;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class Car_Maintenance {

    @FXML
    private TableView<model.maintenance> maintenance_table;

    @FXML
    private TableColumn<model.maintenance, Integer> MaintenanceIDcolumn;

    @FXML
    private TableColumn<model.maintenance, String> CarSeriesColumn;

    @FXML
    private TableColumn<model.maintenance, String> CarPlateColumn;

    @FXML
    private TableColumn<model.maintenance, String> LicenseNumberColumn;

    @FXML
    private TableColumn<model.maintenance, String> ChangeOilColumn;

    @FXML
    private TableColumn<model.maintenance, String> ChangeBeltColumn;

    @FXML
    private TableColumn<model.maintenance, String> StatusColumn;

     @FXML
     private ComboBox<String> statusOptions;

    @FXML
    private TextField searchTextField;

    @FXML 
    private Pane carMaintenancePane, updateCarMaintenancePane;

    @FXML
    private Text UCarPlate, ULicenseNum;

    @FXML 
    private DatePicker updateChangeOil, updateChangeBelt;

    private static String tableName = "maintenance";

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    maintenance maintenance = null;

    ObservableList<maintenance> maintenanceList = FXCollections.observableArrayList();
    

    public void initialize() {
        loadDate();

       setupFilterComboBox();
       

        maintenance_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        maintenance_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Show the selected item in the update pane
                printSelectedRowData(newSelection);
                bindSelectedRowData(newSelection);
            }
        });

        configureDatePickers();

    }

    public void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        System.err.println("Car Maintenance Controller Initialized"); // Debug statement
        MaintenanceIDcolumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceId"));
        CarSeriesColumn.setCellValueFactory(new PropertyValueFactory<>("carSeries"));
        CarPlateColumn.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        LicenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        ChangeOilColumn.setCellValueFactory(new PropertyValueFactory<>("changeOil"));
        ChangeBeltColumn.setCellValueFactory(new PropertyValueFactory<>("changeBelt"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
    }

    private void printSelectedRowData(maintenance selectedMaintenance){
        System.out.println("Selected Maintenance Record:");
        System.out.println("Maintenance ID: " + selectedMaintenance.getMaintenanceId());
        System.out.println("Car Series: " + selectedMaintenance.getCarSeries());
        System.out.println("Car Plate: " + selectedMaintenance.getCarPlate());
        System.out.println("License Number: " + selectedMaintenance.getLicenseNumber());
        System.out.println("Change Oil: " + selectedMaintenance.getChangeOil());
        System.out.println("Change Belt: " + selectedMaintenance.getChangeBelt());
        System.out.println("Status: " + selectedMaintenance.getStatus());
    }

    public void configureDatePickers(){
        
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

        updateChangeOil.setConverter(converter);
        updateChangeBelt.setConverter(converter);
    }

    private void bindSelectedRowData(maintenance selectedMaintenance){
        UCarPlate.setText(selectedMaintenance.getCarPlate());
        ULicenseNum.setText(selectedMaintenance.getLicenseNumber());
        updateChangeOil.setValue(LocalDate.parse(selectedMaintenance.getChangeOil()));
        updateChangeBelt.setValue(LocalDate.parse(selectedMaintenance.getChangeBelt()));
    }

    @FXML
    private void updateCarMaintenance(){
        LocalDate newchangeOil = updateChangeOil.getValue();
        LocalDate newchangeBelt = updateChangeBelt.getValue();

        maintenance selectedMaintenance = maintenance_table.getSelectionModel().getSelectedItem();
        int maintenanceId = selectedMaintenance.getMaintenanceId();

        String updateQuery = "UPDATE maintenance SET maintenance_changeOil = ?, maintenance_changeBelt = ? WHERE maintenance_RecordID = ?";

        try (Connection connection = DbConnect.getConnect()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, newchangeOil.toString());
                preparedStatement.setString(2, newchangeBelt.toString());
                preparedStatement.setInt(3, maintenanceId);
                preparedStatement.executeUpdate();
                System.out.println("Maintenance record updated successfully");
            }
            showSuccessAlert("Car Maintenance record updated successfully");

        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("An error occurred while updating the maintenance record");
        }
        GoCarMaintenance();
        refreshTable();
    }

    private void GoCarMaintenance(){
        updateCarMaintenancePane.setVisible(false);
        carMaintenancePane.setVisible(true);
    }

    private void showSuccessAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Callback<TableColumn<maintenance, String>, TableCell<maintenance, String>> createStatusCellFactory() {
        return new Callback<TableColumn<maintenance, String>, TableCell<maintenance, String>>() {
            @Override
            public TableCell<maintenance, String> call(TableColumn<maintenance, String> param) {
                return new TableCell<maintenance, String>() {
                    final Circle circle = new Circle(8);

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setText(item);

                            if ("Overdue".equals(item)) {
                                circle.setFill(Color.web("#FB1616")); // Set to #FB1616 for Red
                            } if ("Due Soon".equals(item)) {
                                    circle.setFill(Color.web("#FFFF00")); // Set for yellow
                            } else {
                                circle.setFill(Color.web("#FBC916")); // Set to #FBC916 for Green
                            }
                            setGraphic(circle);
                        }
                    }

                };
            }
        };
    }

   public void discardUpdate(){
        updateChangeBelt.setValue(null);
        updateChangeOil.setValue(null);
        GoCarMaintenance();
        refreshTable();

    
   }
   @FXML
   private void handleFilterChange(ActionEvent event) {
       refreshTable();
   }

 
   @FXML
public void refreshTable() {
    try {
        maintenanceList.clear();

        String selectedFilter = statusOptions.getValue();
        String searchKeyword = searchTextField.getText();

        if (selectedFilter == null) {
            selectedFilter = "All";
            statusOptions.setValue(selectedFilter);
        }

        // First query to retrieve car_Plate
        String carPlateQuery = "SELECT m.*, c.car_Series, c.car_Plate " +
                               "FROM maintenance m " +
                               "JOIN car c ON m.car_Plate = c.car_Plate " +
                               "WHERE m.car_Plate LIKE ?";

        preparedStatement = connection.prepareStatement(carPlateQuery);
        preparedStatement.setString(1, "%" + searchKeyword + "%");

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String carPlate = resultSet.getString("car_Plate");

            // Second query to retrieve driver_LicenseNum
            String licenseNumQuery = "SELECT driver_LicenseNum FROM driver WHERE car_Plate = ?";
            preparedStatement = connection.prepareStatement(licenseNumQuery);
            preparedStatement.setString(1, carPlate);

            ResultSet driverResultSet = preparedStatement.executeQuery();

            if (driverResultSet.next()) {
                maintenanceList.add(new maintenance(
                        resultSet.getInt("maintenance_RecordID"),
                        resultSet.getString("car_Series"),
                        carPlate,
                        driverResultSet.getString("driver_LicenseNum"),
                        resultSet.getString("maintenance_ChangeOil"),
                        resultSet.getString("maintenance_ChangeBelt"),
                        resultSet.getString("maintenance_MStatus")
                ));
            }
        }

        maintenance_table.setItems(maintenanceList);

    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}



   

                            


    

    @FXML
    public void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            refreshTable();
        }
    }

    private void setupFilterComboBox() {
        statusOptions.getItems().addAll("All", "Due Soon", "Up to Date", "Overdue");
        statusOptions.setValue("All");
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

    public void GoToC_Amortization(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Amortization.fxml"));

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

    public void GoUpdateMaintenance(){
        try{
            if (maintenance_table.getSelectionModel().getSelectedItem() == null){
                showAlert("No Maintenance Record Selected", "Please select a maintenance record to update");
                return;
            }
            updateCarMaintenancePane.setVisible(true);
            carMaintenancePane.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("An error occurred while trying to update the maintenance record");
        }
    }

    public void deleteMaintenance(ActionEvent event){
        try {
            if (maintenance_table.getSelectionModel().getSelectedItem() == null){
                showAlert("No Maintenance Record Selected", "Please select a maintenance record to delete");
                return;
            }
            int maintenanceId = maintenance_table.getSelectionModel().getSelectedItem().getMaintenanceId();
            String deleteQuery = "DELETE FROM maintenance WHERE maintenance_RecordID = ?";

            try (Connection connection = DbConnect.getConnect()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setInt(1, maintenanceId);
                    preparedStatement.executeUpdate();
                    System.out.println("Maintenance record deleted successfully");
                }
                showSuccessAlert("Car Maintenance record deleted successfully");

            } catch (SQLException e) {
                e.printStackTrace();
                showErrorAlert("An error occurred while deleting the maintenance record");
            }
            GoCarMaintenance();
            refreshTable();

        } catch(Exception e){
            e.printStackTrace();
            showErrorAlert("An error occurred while trying to delete the maintenance record");
        }
    }
    public static int countOverdueCars() {
        int count = 0;
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM " + tableName + " WHERE maintenance_MStatus = 'Overdue'")) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countDueSoonCars() {
        int count = 0;
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM " + tableName + " WHERE maintenance_MStatus = 'Due Soon'")) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int countUpToDateCars() {
        int count = 0;
        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS total FROM " + tableName + " WHERE maintenance_MStatus = 'Up to date'")) {

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    
}
   