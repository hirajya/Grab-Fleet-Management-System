package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
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

    private ObservableList<Driver_Quota_obj> originalData;

    @FXML
    private TextField searchTextField;

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
                    final Circle circle = new Circle(8);

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
                                circle.setFill(Color.web("#FBC916")); // Set to #FBC916 for Green
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

}

