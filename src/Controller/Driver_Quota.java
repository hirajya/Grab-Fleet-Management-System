package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
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

