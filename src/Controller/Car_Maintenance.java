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

     private ObservableList<maintenance> originalData;

    @FXML
    private TextField searchTextField;



    public void initialize() {
        setUpColumns();
        setUpComboBox();
        ObservableList<String> statusOptionsList = FXCollections.observableArrayList("All", "Due Soon", "Up to Date", "Overdue");
         statusOptions.setItems(statusOptionsList);
         statusOptions.setValue("All");  // Set default value

        // // Handle ComboBox selection changes
         statusOptions.setOnAction(event -> filterTableByStatus());



        // // Save the original data
         originalData = FXCollections.observableArrayList(model.maintenance_table.getMaintenanceData());

        // // Populate TableView with data from the database
         maintenance_table.getItems().addAll(originalData);

        // // Handle search on Enter key press
         searchTextField.setOnKeyPressed(event -> {
             if (event.getCode() == KeyCode.ENTER) {
                 filterTableByStatus();
             }
         });
    }

    public void setUpColumns() {
        System.err.println("Car Maintenance Controller Initialized"); // Debug statement
        MaintenanceIDcolumn.setCellValueFactory(new PropertyValueFactory<>("maintenanceId"));
        CarSeriesColumn.setCellValueFactory(new PropertyValueFactory<>("carSeries"));
        CarPlateColumn.setCellValueFactory(new PropertyValueFactory<>("carPlate"));
        LicenseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        ChangeOilColumn.setCellValueFactory(new PropertyValueFactory<>("changeOil"));
        ChangeBeltColumn.setCellValueFactory(new PropertyValueFactory<>("changeBelt"));
        StatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
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

    public void setUpComboBox() {
        ObservableList<String> statusOptionsList = FXCollections.observableArrayList("All", "Due Soon", "Up to Date", "Overdue");
        statusOptions.setItems(statusOptionsList);
        statusOptions.setValue("All");  // Default value
        statusOptions.setOnAction(event -> filterTableByStatus());
    }

    public void filterTableByStatus() {
        String selectedStatus = statusOptions.getValue();
        String searchKeyword = searchTextField.getText().toLowerCase();

        // Clear existing items in the table
        maintenance_table.getItems().clear();

        // Get updated data based on the selected status and search keyword
        List<maintenance> filteredData = new ArrayList<>();
        for (maintenance item : originalData) {
            if (("All".equals(selectedStatus) || selectedStatus.equals(item.getStatus()))
                    && (item.getCarPlate().toLowerCase().contains(searchKeyword))) {
                filteredData.add(item);
            }
        }

        // Add the filtered data to the table
        maintenance_table.getItems().addAll(filteredData);
    }

    @FXML
    public void refreshTable() {
        filterTableByStatus();
    }

    @FXML
    public void handleSearch(KeyEvent event) {
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

}

