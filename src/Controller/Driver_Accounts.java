package Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;

public class Driver_Accounts {

    @FXML
    public Pane addDriver, addDriver2, updateDriver, deleteDriver;

    public void showAddDriverPane(ActionEvent event) {
        addDriver.setVisible(true);
    }    

    public void showAddDriverPane2(ActionEvent event) {
        addDriver2.setVisible(true);
    }    

    public void showUpdateDriverPane(ActionEvent event) {
        updateDriver.setVisible(true);
    }    

    public void showDeleteDriverPane(ActionEvent event) {
        deleteDriver.setVisible(true);
    }   

    public void hideDeleteDriverPane(ActionEvent event) {
        deleteDriver.setVisible(false);
    }

     public void hideAddDriverPane(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Discard Input");
        
        Label contentLabel = new Label("Are you sure you want to discard the inputs?");
        contentLabel.setStyle("-fx-font-size: 14px;"); 

        dialog.getDialogPane().setContent(contentLabel);


        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/Images/attention.png")));
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);

        Label iconLabel = new Label("", iconView);

        HBox hBox = new HBox(iconLabel);
        HBox.setHgrow(iconLabel, Priority.ALWAYS);
        hBox.getChildren().add(new Label("Are you sure you want to discard the inputs?"));
        dialog.getDialogPane().setContent(hBox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            addDriver.setVisible(false);
            addDriver2.setVisible(false);
        }
    }
    
    public void hideUpdateDriverPane(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Confirmation");
        dialog.setHeaderText("Discard Input");
        
        Label contentLabel = new Label("Are you sure you want to discard the inputs?");
        contentLabel.setStyle("-fx-font-size: 14px;"); 

        dialog.getDialogPane().setContent(contentLabel);


        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

        ImageView iconView = new ImageView(new Image(getClass().getResourceAsStream("/Images/attention.png")));
        iconView.setFitWidth(35);
        iconView.setFitHeight(35);

        Label iconLabel = new Label("", iconView);

        HBox hBox = new HBox(iconLabel);
        HBox.setHgrow(iconLabel, Priority.ALWAYS);
        hBox.getChildren().add(new Label("Are you sure you want to discard the inputs?"));
        dialog.getDialogPane().setContent(hBox);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            updateDriver.setVisible(false);
        }
    }


    @FXML
    private DatePicker datePicker1, datePicker2, datePicker3, datePicker4, datePicker5, datePicker6;

    public void initialize() {
        setDatePickerFormat(datePicker1);
        setDatePickerFormat(datePicker2);
        setDatePickerFormat(datePicker3);
        setDatePickerFormat(datePicker4);
        setDatePickerFormat(datePicker5);
        setDatePickerFormat(datePicker6);
    }

    private void setDatePickerFormat(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, dateFormatter) : null;
            }
        });
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

    public void GoToD_Quota(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/View/Driver_Quota.fxml"));

        Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

    }

}