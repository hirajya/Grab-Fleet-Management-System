package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;
import java.sql.*;
import javafx.scene.control.TextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    @FXML
    private TextField searchTextField;

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

        StatusColumn.setCellFactory(createStatusCellFactory());


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