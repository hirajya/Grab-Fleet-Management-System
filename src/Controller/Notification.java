package Controller;

import java.io.IOException;
import java.rmi.server.LoaderHandler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.driver_database;

public class Notification {

    FXMLLoader loader;

    @FXML
    private VBox vbox;

    @FXML
    public Pane pane1, pane2, pane3, pane4;

    @FXML
    public Text countnotif;

    @FXML
    public Circle notificationCircle;


    @FXML
    public void initialize() {
        updateNotificationCount();
        updateNotificationIndicator();
    }

    public void removePane1(ActionEvent event) {
        hideAndShift(pane1);
        updateNotificationCount();
    }
    
    public void removePane2(ActionEvent event) {
        hideAndShift(pane2);
        updateNotificationCount();
    }
    
    public void removePane3(ActionEvent event) {
        hideAndShift(pane3);
        updateNotificationCount();
    }
    
    public void removePane4(ActionEvent event) {
        hideAndShift(pane4);
        updateNotificationCount();
    }

    private void hideAndShift(Pane pane) {
        pane.setVisible(false);
        VBox.setMargin(pane, null);
        vbox.getChildren().remove(pane);
    }
    
    @FXML
    private void updateNotificationCount() {
        int visiblePanes = 0;
        for (Node node : vbox.getChildren()) {
            if (node instanceof Pane && node.isVisible()) {
                visiblePanes++;
            }
        }
        countnotif.setText("You have " + visiblePanes + " unread notifications");
    }

    public void markAllAsRead(ActionEvent event) {
        vbox.getChildren().clear();
        updateNotificationCount();
        updateNotificationIndicator();
    }

    @FXML 
    public void updateNotificationIndicator() {
        boolean hasNotifications = !vbox.getChildren().isEmpty();
        notificationCircle.setVisible(hasNotifications);
    }

    public void GoToHome(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Initialize the FXMLLoader
        loader = new FXMLLoader(getClass().getResource("/View/Home.fxml"));
        Parent root = loader.load();

        // Get the Home controller instance
        Home homeController = loader.getController();

        // Pass the Notification controller instance to Home controller (assuming it has a setter)
        homeController.setNotificationController(this); // Assuming there's a setter in Home

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
