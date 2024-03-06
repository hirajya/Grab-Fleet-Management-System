package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
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
import model.amortization;
import model.quota_table;
import model.object_model.Driver_Quota_obj;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

public class Driver_Quota {

    @FXML
    private TableView<model.object_model.Driver_Quota_obj> quota_table;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, Double> col_Amount;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, String> col_DriverName;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, Double> col_Balance;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, String> col_DueDate;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, String> col_LicenseNumber;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, Double> col_PaidAmount;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, Integer> col_RecordId;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, String> col_StartDate;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, String> col_Status;

    @FXML
    private ComboBox<String> statusOptions;

    @FXML
    private Button back2ViewButton;

    private ObservableList<Driver_Quota_obj> originalData;

    @FXML
    private Text deleteText;

    @FXML
    private Text UDQAmount, UDQLicenseNumber, UDQName;

    @FXML
    private TextField UDQPaidAmount;

    @FXML
    private DatePicker UDQStartDate, UDQDueDate;


    @FXML
    private TextField searchTextField;

    @FXML
    private Button updateButton;

    @FXML
    private Pane quota_view;

    @FXML
    private Pane updateCarQuotaPane;
    
    @FXML
    private Button updateButtonInsert;

    @FXML
    private Pane deletePane;

    @FXML
    private TextField confirmationTextField;

    @FXML
    private Button discardButtonDelete, deleteButton;

    @FXML
    private Button GoDeleteButton1;



    public void initialize() {
        setUpColumns();
        setUpComboBox();
        ObservableList<String> statusOptionsList = FXCollections.observableArrayList("All", "Paid", "Unpaid");
        statusOptions.setItems(statusOptionsList);
        statusOptions.setValue("All");  // Set default value

        // Handle ComboBox selection changes
        statusOptions.setOnAction(event -> filterTableByStatus());

        // Save the original data
        originalData = FXCollections.observableArrayList(model.quota_table.getQuotaData());

        // Populate TableView with data from the database
        quota_table.getItems().addAll(originalData);

        // Handle search on Enter key press
        searchTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                filterTableByStatus();
            }
        });

        // Set the selection mode to SINGLE to allow only one row to be selected at a time
        quota_table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Add a selection listener to the table
        quota_table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Handle the selected row, you can print the data or perform any other action
                printSelectedRowData(newSelection);
                bindSelectedRowData(newSelection);
            }
        });

        // Configure date pickers to display dates in the format you desire
        configureDatePickers();
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

    private void printSelectedRowData(model.object_model.Driver_Quota_obj selectedQuota) {
        System.out.println("Selected Row Data:");
        System.out.println("Record ID: " + selectedQuota.getRecordId());
        System.out.println("Driver Name: " + selectedQuota.getDriverName());
        System.out.println("License Number: " + selectedQuota.getLicenseNumber());
        System.out.println("Amount: " + selectedQuota.getAmount());
        System.out.println("Paid Amount: " + selectedQuota.getPaidAmount());
        System.out.println("Balance: " + selectedQuota.getBalance());
        System.out.println("Start Date: " + selectedQuota.getStartDate());
        System.out.println("Due Date: " + selectedQuota.getDueDate());
        System.out.println("Status: " + selectedQuota.getStatus());
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

        UDQStartDate.setConverter(converter);
        UDQDueDate.setConverter(converter);
    }

    @FXML
    private void deleteDriverQuota() {
        try {
            model.object_model.Driver_Quota_obj selectedQuota = quota_table.getSelectionModel().getSelectedItem();
    
            if (selectedQuota != null) {
                // Validate deletion
                boolean isDeleteConfirmed = isDeleteText(confirmationTextField);
    
                if (isDeleteConfirmed) {
                    int recordID = selectedQuota.getRecordId();
                    String deleteQuery = "DELETE FROM quota WHERE quota_RecordID = ?";
    
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grab-fleet-database", "root", "");
                         PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
    
                        deleteStatement.setInt(1, recordID);
                        int rowsAffected = deleteStatement.executeUpdate();
    
                        if (rowsAffected > 0) {
                            System.out.println("Row deleted successfully.");
                            refreshTable1();
                        } else {
                            System.out.println("Failed to delete row.");
                        }
                    }
                } else {
                    showNoDeleteMsg();
                    System.out.println("Deletion cancelled. Text does not match 'DELETE'.");
                }
            } else {
                showErrorAlert("Please select a row to delete.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void GoDeleteQuota() {
        confirmationTextField.clear();
        deleteText.setVisible(false);
        try {
            if (quota_table.getSelectionModel().getSelectedItem() == null) {
                showAlert("No Selected Data", "Please select a car from the table to delete.");
                return; // Exit the method if no item is selected
            }
            applyBlur(quota_view);
            deletePane.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error in GoQuotaView");
        }
    }

    

    public void GoDriverQuota() {
        
        deletePane.setVisible(false);
        removeBlur(quota_view);
        quota_view.setVisible(true);
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

    

    


    private void bindSelectedRowData(model.object_model.Driver_Quota_obj selectedQuota) {
        // Bind selected row data to the controls
        UDQStartDate.setValue(LocalDate.parse(selectedQuota.getStartDate()));
        UDQDueDate.setValue(LocalDate.parse(selectedQuota.getDueDate()));
        UDQPaidAmount.setText(String.valueOf(selectedQuota.getPaidAmount()));
        UDQAmount.setText(String.valueOf(selectedQuota.getAmount()));
        UDQLicenseNumber.setText(selectedQuota.getLicenseNumber());
        UDQName.setText(selectedQuota.getDriverName());
    }

    @FXML
    private void updateDriverQuota() {
        // Retrieve values from controls
        LocalDate newEndDate = UDQStartDate.getValue();
        LocalDate newMonthlyDueDate = UDQDueDate.getValue();
        double newPayment = Double.parseDouble(UDQPaidAmount.getText());

        // Perform the update in the database using the selected row's RecordID
        int recordID = quota_table.getSelectionModel().getSelectedItem().getRecordId();
        System.out.println("TANGINA ITO ID");
        System.out.println(recordID);

        // Replace "your_driver_quota_table" with the actual name of your driver quota table
        String updateQuery = "UPDATE quota SET quota_DDate = ?, quota_DDate = ?, quota_InputAmount = ?, "
                + "quota_Balance = (quota_InputAmount - ?), quota_Status = CASE WHEN (quota_InputAmount - ?) = 0 THEN 'Paid' ELSE 'Unpaid' END "
                + "WHERE quota_RecordID = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/grab-fleet-database", "root", "");
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDate(1, Date.valueOf(newEndDate));
            updateStatement.setDate(2, Date.valueOf(newMonthlyDueDate));
            updateStatement.setDouble(3, newPayment);
            updateStatement.setDouble(4, newPayment);
            updateStatement.setDouble(5, newPayment);
            updateStatement.setInt(6, recordID);

            // Execute the update
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Row updated successfully.");
                // Refresh the table to reflect the changes
                refreshTable();
            } else {
                System.out.println("Failed to update row.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        refreshTable1();
        switchToView();
    }



    private void setUpColumns() {
        System.err.println("Driver Quota Controller Initialized"); // Debug statement
        col_RecordId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        col_DriverName.setCellValueFactory(new PropertyValueFactory<>("driverName"));
        col_LicenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        col_Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_PaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        col_Balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col_DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));

        col_Status.setCellFactory(createStatusCellFactory());
    }

    private Callback<TableColumn<Driver_Quota_obj, String>, TableCell<Driver_Quota_obj, String>> createStatusCellFactory() {
        return new Callback<TableColumn<Driver_Quota_obj, String>, TableCell<Driver_Quota_obj, String>>() {
            @Override
            public TableCell<Driver_Quota_obj, String> call(TableColumn<Driver_Quota_obj, String> param) {
                return new TableCell<Driver_Quota_obj, String>() {
                    final Circle circle = new Circle(4);

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            setText(item);

                            if ("Unpaid".equals(item)) {
                                circle.setFill(Color.web("#FB1616")); // Set to #FB1616 for Red
                            } else {
                                circle.setFill(Color.web("#64E338")); // Set to #FBC916 for Green
                            }
                            setGraphic(circle);
                        }
                    }

                };
            }
        };
    }

    private void setUpComboBox() {
        ObservableList<String> statusOptionsList = FXCollections.observableArrayList("All", "Paid", "Unpaid");
        statusOptions.setItems(statusOptionsList);
        statusOptions.setValue("All");  // Default value
        statusOptions.setOnAction(event -> filterTableByStatus());
    }

    // public void switchToUpdate(){
    //     quota_view.setVisible(false);
    //     updateCarQuotaPane.setVisible(true);
    // }

    public void switchToUpdate() {
        if (quota_table.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Please select a row to update.");
            return;
        }
        quota_view.setVisible(false);
        updateCarQuotaPane.setVisible(true);
    }

    public void switchToView() {
        quota_view.setVisible(true);
        updateCarQuotaPane.setVisible(false);

    }

    

    private void filterTableByStatus() {
        String selectedStatus = statusOptions.getValue();
        String searchKeyword = searchTextField.getText().toLowerCase();

        // Clear existing items in the table
        quota_table.getItems().clear();

        // Get updated data based on the selected status and search keyword
        List<Driver_Quota_obj> filteredData = new ArrayList<>();
        for (Driver_Quota_obj item : originalData) {
            if (("All".equals(selectedStatus) || selectedStatus.equals(item.getStatus()))
                    && (item.getDriverName().toLowerCase().contains(searchKeyword))) {
                filteredData.add(item);
            }
        }

        // Add the filtered data to the table
        quota_table.getItems().addAll(filteredData);
    }

    @FXML
    private void refreshTable() {
        filterTableByStatus();
    }

    @FXML
    private void refreshTable1() {
        try {
            // Fetch updated data from the database
            List<Driver_Quota_obj> updatedData = model.quota_table.getQuotaData();

            // Clear existing items in the table and originalData list
            quota_table.getItems().clear();
            originalData.clear();

            // Add the updated data to the table and originalData list
            quota_table.getItems().addAll(updatedData);
            originalData.addAll(updatedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleSearch(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            filterTableByStatus();
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

    public void GoToD_Accounts(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Driver_Accounts.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

     public static List<String[]> getDriversWithBalance() {
        List<String[]> driversWithBalance = new ArrayList<>();

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);

            String sqlQuery = "SELECT CONCAT(driver.driver_FName, ' ', driver.driver_LName) AS full_name, driver.driver_CNumber, quota.quota_Balance FROM driver INNER JOIN quota ON driver.driver_LicenseNum = quota.driver_LicenseNum WHERE quota.quota_Balance > 0";
            preparedStatement = connection.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String[] driverInfo = new String[3];
                driverInfo[0] = resultSet.getString("full_name");
                driverInfo[1] = resultSet.getString("driver_CNumber");
                driverInfo[2] = resultSet.getString("quota_Balance");
                driversWithBalance.add(driverInfo);
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

        return driversWithBalance;
    }

    public static int getTotalPaidQuotaForCurrentMonth() {
        int totalPaidQuota = 0;

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(url, user, password);

            // Get current month and year
            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonthValue();
            int currentYear = currentDate.getYear();

            // Query to retrieve total paid quota amount for the current month
            String sqlQuery = "SELECT SUM(quota_InputAmount) AS total_paid FROM quota " +
                              "WHERE MONTH(quota_DDate) = ? AND YEAR(quota_DDate) = ? " +
                              "AND quota_Status = 'Paid'";
            
            preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setInt(1, currentMonth);
            preparedStatement.setInt(2, currentYear);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalPaidQuota = resultSet.getInt("total_paid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connections and resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalPaidQuota;
    }

    public static int getTotalUnpaidQuota() {
        int totalUnpaidQuota = 0;

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(url, user, password);

            // Query to retrieve total paid quota amount for the current month
            String sqlQuery = "SELECT COUNT(quota_RecordID) AS total_unpaid FROM quota " +
                              "WHERE quota_Status = 'Unpaid'";
            
            preparedStatement = connection.prepareStatement(sqlQuery);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalUnpaidQuota = resultSet.getInt("total_unpaid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close connections and resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalUnpaidQuota;
    }


    public static Map<Integer, Integer[]> getQuotaDataByWeeks() {
        Map<Integer, Integer[]> quotaDataByWeeks = new HashMap<>();

        String url = "jdbc:mysql://localhost:3306/grab-fleet-database";
        String user = "root";
        String password = "";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            
            connection = DriverManager.getConnection(url, user, password);

            LocalDate currentDate = LocalDate.now();
            int currentMonth = currentDate.getMonthValue();
            int currentYear = currentDate.getYear();

            LocalDate firstDayOfMonth = LocalDate.of(currentYear, currentMonth, 1);
            LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

            LocalDate startDate = firstDayOfMonth;
            LocalDate endDate = firstDayOfMonth.plusDays(6);

            for (int week = 1; week <= 4; week++) {
        
                String paidQuery = "SELECT SUM(quota_InputAmount) AS total_paid FROM quota " +
                                   "WHERE (quota_DDate BETWEEN ? AND ?) " +
                                   "AND MONTH(quota_DDate) = ? AND YEAR(quota_DDate) = ? " +
                                   "AND quota_Status = 'Paid'";
                
                preparedStatement = connection.prepareStatement(paidQuery);
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));
                preparedStatement.setInt(3, currentMonth);
                preparedStatement.setInt(4, currentYear);

                resultSet = preparedStatement.executeQuery();

                int totalPaidQuota = 0;
                if (resultSet.next()) {
                    totalPaidQuota = resultSet.getInt("total_paid");
                }

                String unpaidQuery = "SELECT SUM(quota_InputAmount) AS total_unpaid FROM quota " +
                                     "WHERE (quota_DDate BETWEEN ? AND ?) " +
                                     "AND MONTH(quota_DDate) = ? AND YEAR(quota_DDate) = ? " +
                                     "AND quota_Status = 'Unpaid'";
                
                preparedStatement = connection.prepareStatement(unpaidQuery);
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));
                preparedStatement.setInt(3, currentMonth);
                preparedStatement.setInt(4, currentYear);

                resultSet = preparedStatement.executeQuery();

                int totalUnpaidQuota = 0;
                if (resultSet.next()) {
                    totalUnpaidQuota = resultSet.getInt("total_unpaid");
                }

                Integer[] quotaAmounts = {totalPaidQuota, totalUnpaidQuota};
                quotaDataByWeeks.put(week, quotaAmounts);

                startDate = endDate.plusDays(1);
                endDate = startDate.plusDays(6);
            }
        } catch (SQLException e) {
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
        return quotaDataByWeeks;
    }

}

