package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class signinhelpController implements Initializable {
    
    public Button back;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        back.setOnAction(event -> {
            Stage stage = (Stage) back.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("LoggedForm.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
