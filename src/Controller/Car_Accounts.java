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
import javafx.stage.Stage;
import javafx.util.Duration;
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

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    amortization amortization = null;

    ObservableList<car> carList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
       
         setupSeriesComboBox();
            setupKindComboBox();
            setupYearComboBox();
            setupColorComboBox();
            setupRegStatusComboBox();
            setupAvailabilityComboBox();
            loadDate();
    }



    private void loadDate() {
        connection = DbConnect.getConnect();
        refreshTable();
        carplateColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Plate"));
        CRcolumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_CR"));
        ORcolumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_OR"));
        seriesColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Series"));
        kindColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Kind"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<car, Integer>("car_Year"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Color"));
        registrationColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_RegDate"));
        expiryColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Expiry"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_RegStatus"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<car, String>("car_Availability"));


    }

    

    @FXML
private void refreshTable() {
    try {
        carList.clear();

        carList.clear();

        setDefaultComboBoxValue(seriesComboBox, "All");
        setDefaultComboBoxValue(kindComboBox, "All");
        setDefaultComboBoxValue(yearComboBox, "All");
        setDefaultComboBoxValue(colorComboBox, "All");
        setDefaultComboBoxValue(RegstatusComboBox, "All");
        setDefaultComboBoxValue(AvailabilityComboBox, "All");

        String selectedSeries = seriesComboBox.getValue() != null ? seriesComboBox.getValue() : "All";
        String selectedKind = kindComboBox.getValue() != null ? kindComboBox.getValue() : "All";
        String selectedYear = yearComboBox.getValue() != null ? yearComboBox.getValue() : "All";
        String selectedColor = colorComboBox.getValue() != null ? colorComboBox.getValue() : "All";
        String selectedRegStatus = RegstatusComboBox.getValue() != null ? RegstatusComboBox.getValue() : "All";
        String selectedAvailability = AvailabilityComboBox.getValue() != null ? AvailabilityComboBox.getValue() : "All";
       

        String searchKeyword = searchTextField.getText();

        // Update the query based on the selected criteria
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM car WHERE car_Plate LIKE ?");

        if (!selectedSeries.equals("All")) {
            queryBuilder.append(" AND car_Series LIKE ?");
        }

        if (!selectedKind.equals("All")) {
            queryBuilder.append(" AND car_Kind LIKE ?");
        }

        if (!selectedYear.equals("All")) {
            queryBuilder.append(" AND car_Year LIKE ?");
        }

        if (!selectedColor.equals("All")) {
            queryBuilder.append(" AND car_Color LIKE ?");
        }

        if (!selectedRegStatus.equals("All")) {
            queryBuilder.append(" AND car_RegStatus LIKE ?");
        }

        if (!selectedAvailability.equals("All")) {
            queryBuilder.append(" AND car_Availability LIKE ?");
        }

        preparedStatement = connection.prepareStatement(queryBuilder.toString());
        preparedStatement.setString(1, "%" + searchKeyword + "%");

        int parameterIndex = 2;

        if (!selectedSeries.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedSeries + "%");
        }

        if (!selectedKind.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedKind + "%");
        }

        if (!selectedYear.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedYear + "%");
        }

        if (!selectedColor.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedColor + "%");
        }

        if (!selectedRegStatus.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedRegStatus + "%");
        }

        if (!selectedAvailability.equals("All")) {
            preparedStatement.setString(parameterIndex++, "%" + selectedAvailability + "%");
        }

        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            carList.add(new car(
                resultSet.getString("car_Plate"),
                resultSet.getString("car_CR"),
                resultSet.getString("car_OR"),
                resultSet.getString("car_Series"),
                resultSet.getInt("car_Year"),
                resultSet.getString("car_Kind"),
                resultSet.getString("car_Color"),
                resultSet.getDate("car_RegDate"),
                resultSet.getDate("car_Expiry"),
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

    private void setupSeriesComboBox() {
        seriesComboBox.setItems(FXCollections.observableArrayList("All", "Crosswind", "Adventure","Innova","Mirage","Vios","Avanza","Stargazer"));
        seriesComboBox.setValue("All");
    }

    private void setupKindComboBox() {
        kindComboBox.setItems(FXCollections.observableArrayList("All", "MPV", "Sedan"));
        kindComboBox.setValue("All");
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
    
    private void setDefaultComboBoxValue(ComboBox<String> comboBox, String defaultValue) {
        if (comboBox != null) {
            String selectedValue = comboBox.getValue();
            if (selectedValue == null) {
                comboBox.setValue(defaultValue);
            }
        }
    }
    
    
}