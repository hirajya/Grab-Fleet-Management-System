    package Controller;

    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

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
    import model.amortization_table;
    import model.car_table;
    import model.driver_database;
    import model.maintenance_table;
    import model.quota_table;

    public class Notification {

        FXMLLoader loader;

        @FXML
        public Text Maintenance, Registration, Amortization, Quota;

        @FXML
        private VBox vbox;

        @FXML
        public Pane pane1, pane2, pane3, pane4, noNotif;

        @FXML
        public Text countnotif;

        @FXML
        public Circle notificationCircle;

        private static Map<String, Boolean> deletedNotifications = new HashMap<>();


        public void initialize() {
            updateNotificationCount();
            
        
            int totalOverdue = maintenance_table.countOverdueCars();
            if (totalOverdue == 0) {
                if (!isPaneDeleted(pane1)) {
                    vbox.getChildren().remove(pane1);
                    updateNotificationCount();
                    
                }
            } else {
                Maintenance.setText("Attention! " + totalOverdue + " vehicles are due for maintenance. Schedule a service to ensure optimal performance and safety.");
                if (!isPaneDeleted(pane1)) {
                    pane1.setVisible(true);
                    updateNotificationCount();
                } else {
                    vbox.getChildren().remove(pane1);
                    updateNotificationCount();
                    
                }
            }
        
            int totalExpired = car_table.countExpiredCars();
            if (totalExpired == 0) {
                if (!isPaneDeleted(pane2)) {
                    vbox.getChildren().remove(pane2);
                    updateNotificationCount();
                    
                }
            } else {
                Registration.setText("The registrations of " + totalExpired + " vehicles are expired. Please renew the registrations to ensure legal compliance and continued use of the vehicles.");
                if (!isPaneDeleted(pane2)) {
                    pane2.setVisible(true);
                    updateNotificationCount();
                    
                } else {
                    vbox.getChildren().remove(pane2);
                    updateNotificationCount();
                    
                }
            }
        
            int totalUnpaidAmortization = amortization_table.getTotalUnPaidCar();
            if (totalUnpaidAmortization == 0) {
                if (!isPaneDeleted(pane3)) {
                    vbox.getChildren().remove(pane3);
                    updateNotificationCount();
                    
                }
            } else {
                Amortization.setText("Reminder: Amortization payments for " + totalUnpaidAmortization + " vehicles are unpaid. Please settle promptly to maintain ownership and prevent additional charges.");
                if (!isPaneDeleted(pane3)) {
                    pane3.setVisible(true);
                    updateNotificationCount();
                    
                } else {
                    vbox.getChildren().remove(pane3);
                    updateNotificationCount();
                    
                }
            }
        
            int totalUnpaidQuota = quota_table.getTotalUnpaidQuota();
            if (totalUnpaidQuota == 0) {
                if (!isPaneDeleted(pane4)) {
                    vbox.getChildren().remove(pane4);
                    updateNotificationCount();
                    
                }
            } else {
                Quota.setText("Attention! " + totalUnpaidQuota + " drivers have unpaid quotas. Please address this promptly to ensure compliance and continuity in operations.");
                if (!isPaneDeleted(pane4)) {
                    pane4.setVisible(true);
                    updateNotificationCount();
                    
                } else {
                    vbox.getChildren().remove(pane4);
                    updateNotificationCount();
                    
                }
            }
        }

        public void removePane1(ActionEvent event) {
            hideAndShift(pane1);
        }

        public void removePane2(ActionEvent event) {
            hideAndShift(pane2);
        }

        public void removePane3(ActionEvent event) {
            hideAndShift(pane3);
        }

        public void removePane4(ActionEvent event) {
            hideAndShift(pane4);
        }

        private void hideAndShift(Pane pane) {
            pane.setVisible(false);
            vbox.getChildren().remove(pane);
            deletedNotifications.put(pane.getId(), true);
            updateNotificationCount();
            
        }

        public void updateNotificationCount() {
            int visiblePanes = 0;
            for (Node node : vbox.getChildren()) {
                if (node instanceof Pane && node.isVisible()) {
                    visiblePanes++;
                }
            }
            countnotif.setText("You have " + visiblePanes + " unread notifications");

            if (visiblePanes == 0) {
                noNotif.setVisible(true);
            } else {
                noNotif.setVisible(false);
            }
        }

        public boolean isPaneDeleted(Pane pane) {
            return deletedNotifications.containsKey(pane.getId()) && deletedNotifications.get(pane.getId());
        }

        public void GoToHome(ActionEvent event) throws IOException {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Initialize the FXMLLoader
            loader = new FXMLLoader(getClass().getResource("/View/Home.fxml"));
            Parent root = loader.load();

            // Get the Home controller instance
            Home homeController = loader.getController();

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
