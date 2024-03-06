package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.management.Notification;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.amortization_table;
import model.car_table;
import model.driver;
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
    public Text currentDate, totalDrivers, totalCars, maintenanceOverdue, maintenanceDueSoon, maintenanceUpToDate, 
    totalPaidQuotaText, totalPaidAmortization, carCountAmortization, carAvailabilityText, carAvailability, monthAmortization, 
    liveClock, carTotalFleet, quotaMonth, barValue;

    @FXML
    public Circle  notificationCircle;

    @FXML
    private Notification notificationController;

    @FXML
    public TextField searchCarPlate;

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
    private Pane barValuePane;


    @FXML
    public void initialize(){

        LocalDate today = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedDate = today.format(formatter);

        currentDate.setText(formattedDate.toUpperCase());

        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        String monthName = today.format(monthFormatter);

        monthAmortization.setText(monthName.toUpperCase());

        quotaMonth.setText(monthName.toUpperCase());

        int totaldriver = Driver_Accounts.countDrivers();
        totalDrivers.setText(Integer.toString(totaldriver));

        int totalcar = Car_Accounts.countCars();
        totalCars.setText(Integer.toString(totalcar));

        int totalOverdue = Car_Maintenance.countOverdueCars();
        maintenanceOverdue.setText(Integer.toString(totalOverdue));   
        
        int totalDueSoon = Car_Maintenance.countDueSoonCars();
        maintenanceDueSoon.setText(Integer.toString(totalDueSoon));  

        int totalUpToDate = Car_Maintenance.countUpToDateCars();
        maintenanceUpToDate.setText(Integer.toString(totalUpToDate));  

        int totalPaidQuota = Driver_Quota.getTotalPaidQuotaForCurrentMonth();
        totalPaidQuotaText.setText(String.valueOf(totalPaidQuota));

        int totalPaidAmount = Car_Amortization.getTotalUnPaidAmortizationForCurrentMonth();
        totalPaidAmortization.setText(Integer.toString(totalPaidAmount));

        int carCount = Car_Amortization.getTotalUnPaidCarForCurrentMonth();
        carCountAmortization.setText(Integer.toString(carCount) + " Cars");

        int availableCarCount = Car_Accounts.countAvailableCars();
        carAvailability.setText(Integer.toString(availableCarCount));

        int unavailableCarCount = Car_Accounts.countTotalFleet();
        carTotalFleet.setText(Integer.toString(unavailableCarCount));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        List<String[]> driversWithBalance = Driver_Quota.getDriversWithBalance();

        for (String[] driverInfo : driversWithBalance) {
            driverTableView.getItems().add(new DriverInfo(driverInfo[0], driverInfo[1], driverInfo[2]));
        }

        Timeline clockTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE hh:mm:ss a MMMM dd, yyyy");

        String formattedTime = now.format(dateTimeFormatter);

        liveClock.setText(formattedTime);

        }));
        clockTimeline.setCycleCount(Animation.INDEFINITE);
        clockTimeline.play(); 

        xAxis.setLabel("Week");
        yAxis.setLabel("Amount");


        Map<Integer, Integer[]> quotaDataByWeeks = Driver_Quota.getQuotaDataByWeeks();

        XYChart.Series<String, Number> paidSeries = new XYChart.Series<>();
        paidSeries.setName("Paid");
        XYChart.Series<String, Number> unpaidSeries = new XYChart.Series<>();
        unpaidSeries.setName("Unpaid");

        for (Map.Entry<Integer, Integer[]> entry : quotaDataByWeeks.entrySet()) {
            Integer[] quotaAmounts = entry.getValue();
            paidSeries.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), quotaAmounts[0]));
            unpaidSeries.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), quotaAmounts[1]));
        }

        barChart.getData().addAll(paidSeries, unpaidSeries);

        barChart.setLegendVisible(true);
        barChart.setLegendSide(Side.RIGHT);

        for (XYChart.Series<String, Number> series : barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    Tooltip.install(node, new Tooltip(String.valueOf(data.getYValue())));
                    node.setOnMouseEntered(event -> {
                    });
                    node.setOnMouseExited(event -> {
                    });
                }
            }
        }

    nameColumn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
    contactColumn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");
    balanceColumn.setStyle("-fx-background-color: #FFFFFF; -fx-text-fill: black;");

    }
    
    @FXML
    public void searchCar(ActionEvent event) {
        String plateNumber = searchCarPlate.getText();
        String availability = Car_Accounts.searchCarAvailability(plateNumber);
        carAvailabilityText.setText(availability);
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

    
    public void GoToC_Accounts(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Car_Accounts.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

    public void GoToD_Accounts(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Driver_Quota.fxml"));

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
    
}