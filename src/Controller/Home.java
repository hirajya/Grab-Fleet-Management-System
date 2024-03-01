package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.amortization_table;
import model.car_table;
import model.driver_database;
import model.maintenance_table;
import model.quota_table;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Home {

    @FXML
    public Text currentDate, totalDrivers, totalCars, maintenanceOverdue, maintenanceDueSoon, maintenanceUpToDate, totalPaidQuotaText, totalPaidAmortization;

    @FXML
    public Circle  notificationCircleHome;

    private Notification notificationController;

    @FXML
    private TableView<DriverInfo> driverTableView;

    @FXML
    private TableColumn<DriverInfo, String> nameColumn;

    @FXML
    private TableColumn<DriverInfo, String> contactColumn;

    @FXML
    private TableColumn<DriverInfo, String> balanceColumn;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    @FXML
    public void initialize(){

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedDate = today.format(formatter);

        currentDate.setText(formattedDate.toUpperCase());

        int totaldriver = driver_database.countDrivers();
        totalDrivers.setText(Integer.toString(totaldriver));

        int totalcar = car_table.countCars();
        totalCars.setText(Integer.toString(totalcar));

        int totalOverdue = maintenance_table.countOverdueCars();
        maintenanceOverdue.setText(Integer.toString(totalOverdue));   
        
        int totalDueSoon = maintenance_table.countDueSoonCars();
        maintenanceDueSoon.setText(Integer.toString(totalDueSoon));  

        int totalUpToDate = maintenance_table.countUpToDateCars();
        maintenanceUpToDate.setText(Integer.toString(totalUpToDate));  

        int totalPaidQuota = quota_table.getTotalPaidQuotaForCurrentMonth();
        totalPaidQuotaText.setText(String.valueOf(totalPaidQuota));

        int totalPaidAmount = amortization_table.getTotalPaidAmortizationForCurrentMonth();
        totalPaidAmortization.setText(Integer.toString(totalPaidAmount));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        List<String[]> driversWithBalance = quota_table.getDriversWithBalance();

        for (String[] driverInfo : driversWithBalance) {
            driverTableView.getItems().add(new DriverInfo(driverInfo[0], driverInfo[1], driverInfo[2]));
        }

    }
    
    public static class DriverInfo {
        private final String name;
        private final String contact;
        private final String balance;

        public DriverInfo(String name, String contact, String balance) {
            this.name = name;
            this.contact = contact;
            this.balance = balance;
        }

        public String getName() {
            return name;
        }

        public String getContact() {
            return contact;
        }

        public String getBalance() {
            return balance;
        }
    }

    public void setNotificationController(Notification notificationController) {
        this.notificationController = notificationController;
        updateNotificationCircleVisibility();
    }

    private void updateNotificationCircleVisibility() {
        if (notificationController != null) {
            boolean hasNotifications = notificationController.notificationCircle.isVisible();
            notificationCircleHome.setVisible(hasNotifications);
        }
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

    public void GoToNotif(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Notification.fxml"));
       
        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}