package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.DbConnect;
import model.amortization;
import model.amortization_table;
import model.object_model.Driver_Quota_obj;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Callback;


public class Car_Amortization implements Initializable {

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private DatePicker monthlyDueDatePicker;

    @FXML
    private TextField paymentTextField;

    @FXML
    private Button backButtonCarAmortization;

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
    private Button updateCarAmorButton;

    @FXML
    private ComboBox<String> filterComboBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private Pane updateCarAmortizationPane;

    @FXML
    private Pane carAmortizationTablePane;

    @FXML
    private Button updateCarAmortizationButton;

    @FXML
    private Text UCarPlate, UStartDate;

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

        // Set the selection mode to SINGLE to allow only one row to be selected at a time
        amortizationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Add a selection listener to the table
        amortizationTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Handle the selected row, you can print the data or perform any other action
                printSelectedRowData(newSelection);
                bindSelectedRowData(newSelection);
            }
        });

        // Configure date pickers to display dates in the format you desire
        configureDatePickers();
    }

    private void printSelectedRowData(amortization selectedAmortization) {
        System.out.println("Selected Row Data:");
        System.out.println("Record ID: " + selectedAmortization.getAmortization_RecordID());
        System.out.println("Car Plate: " + selectedAmortization.getCar_Plate());
        System.out.println("Start Date: " + selectedAmortization.getAmortization_SDate());
        System.out.println("Due Date: " + selectedAmortization.getAmortization_DDate());
        System.out.println("End Date: " + selectedAmortization.getAmortization_EDate());
        System.out.println("Payment: " + selectedAmortization.getAmortization_Payment());
        System.out.println("Status: " + selectedAmortization.getAmortization_Status());
    }

    public void GoCarAmortization() {
        updateCarAmortizationPane.setVisible(false);
        carAmortizationTablePane.setVisible(true);
    }

    public void updateDetails() {
        amortization selectedAmortization = amortizationTable.getSelectionModel().getSelectedItem();
        if (selectedAmortization != null) {
            UCarPlate.setText(selectedAmortization.getCar_Plate());
            UStartDate.setText(selectedAmortization.getAmortization_SDate().toString());
        }
    }

    public void GoUpdateCarAmortization() {
        updateDetails();
        updateCarAmortizationPane.setVisible(true);
        carAmortizationTablePane.setVisible(false);
        System.out.println("Update Car Amortization");
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

        StatusColumn.setCellFactory(createStatusCellFactory());


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

        endDatePicker.setConverter(converter);
        monthlyDueDatePicker.setConverter(converter);
    }

    private void bindSelectedRowData(amortization selectedAmortization) {
        // Bind selected row data to the controls
        endDatePicker.setValue(selectedAmortization.getAmortization_EDate().toLocalDate());
        monthlyDueDatePicker.setValue(selectedAmortization.getAmortization_DDate().toLocalDate());
        paymentTextField.setText(String.valueOf(selectedAmortization.getAmortization_Payment()));
    }

    @FXML
    private void updateCarAmortization() {
        // Retrieve values from controls
        LocalDate newEndDate = endDatePicker.getValue();
        LocalDate newMonthlyDueDate = monthlyDueDatePicker.getValue();
        int newPayment = Integer.parseInt(paymentTextField.getText());

        // Perform the update in the database using the selected row's RecordID
        int recordID = amortizationTable.getSelectionModel().getSelectedItem().getAmortization_RecordID();

        // Your update SQL query goes here
        String updateQuery = "UPDATE amortization SET amortization_EDate = ?, amortization_DDate = ?, amortization_Payment = ? WHERE amortization_RecordID = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setDate(1, Date.valueOf(newEndDate));
            updateStatement.setDate(2, Date.valueOf(newMonthlyDueDate));
            updateStatement.setInt(3, newPayment);
            updateStatement.setInt(4, recordID);

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
        GoCarAmortization();
    }

    private Callback<TableColumn<amortization, String>, TableCell<amortization, String>> createStatusCellFactory() {
        return new Callback<TableColumn<amortization, String>, TableCell<amortization, String>>() {
            @Override
            public TableCell<amortization, String> call(TableColumn<amortization, String> param) {
                return new TableCell<amortization, String>() {
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
                                circle.setFill(Color.web("#FB1616")); // red
                            } else {
                                circle.setFill(Color.web("#64e338")); // green
                            }
                            setGraphic(circle);
                        }
                    }

                };
            }
        };
    }

    @FXML
    private void refreshTable() {
        try {
            amortizationList.clear();
    
            String selectedFilter = filterComboBox.getValue();
            String searchKeyword = searchTextField.getText();
    
            // Set default value to "All" if selectedFilter is null
            if (selectedFilter == null) {
                selectedFilter = "All";
                filterComboBox.setValue("All");
            }
    
            if (selectedFilter.equals("All")) {
                query = "SELECT * FROM amortization WHERE car_Plate LIKE ?";
            } else if (selectedFilter.equals("Paid")) {
                query = "SELECT * FROM amortization WHERE amortization_Status = 'Paid' AND car_Plate LIKE ?";
            } else if (selectedFilter.equals("Unpaid")) {
                query = "SELECT * FROM amortization WHERE amortization_Status = 'Unpaid' AND car_Plate LIKE ?";
            }
    
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchKeyword + "%");
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

    @FXML
private void handleSearch(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {
        refreshTable();
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

    public void GoToC_Amortization(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Amortization.fxml"));

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