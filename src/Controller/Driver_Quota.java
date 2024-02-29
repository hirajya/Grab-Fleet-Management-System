package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

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
import javafx.stage.Stage;
import javafx.util.Duration;
import model.object_model.Driver_Quota_obj;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Driver_Quota {

    @FXML
    private TableView<model.object_model.Driver_Quota_obj> quota_table;

    @FXML
    private TableColumn<model.object_model.Driver_Quota_obj, Double> col_Amount;

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

    // public Driver_Quota() {
    //     System.out.println("Driver_Quota Controller instance created.");
    //     initialize(null, null);
    // }

    public void initialize() {
        // Initialize columns
        System.err.println("Driver Quota Controller Initialized"); // Debug statement
        col_RecordId.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        col_LicenseNumber.setCellValueFactory(new PropertyValueFactory<>("licenseNumber"));
        col_Amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        col_PaidAmount.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        col_Balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        col_StartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        col_DueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        col_Status.setCellValueFactory(new PropertyValueFactory<>("status"));


        // Populate TableView with data from the database
        List<Driver_Quota_obj> quotaData = model.quota_table.getQuotaData();
        System.out.println("Quota Data Size: " + quotaData.size()); // Debug statement
        quota_table.getItems().addAll(quotaData);
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

