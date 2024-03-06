package Controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Login {

    @FXML
    private ImageView image1;

    @FXML
    private MediaView mediaView;

    @FXML
    TextField usernameField;

    @FXML
    PasswordField passwordField;

    @FXML
    public Text errorText;

    public void initialize() {

        Image image1 = new Image(getClass().getResourceAsStream("/Images/logo.png"));
        this.image1.setImage(image1);

        String videoPath = getClass().getResource("/Images/login.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(Duration.ZERO);
        });
    
        mediaPlayer.play();
    }
    
    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.equals("admin001") && password.equals("admin123!@#")) {

            MediaPlayer mediaPlayer = mediaView.getMediaPlayer();
            if (mediaPlayer != null) {
                mediaPlayer.stop(); 
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/View/Home.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            errorText.setText("Invalid username or password. Please try again.");
        }
    }
}