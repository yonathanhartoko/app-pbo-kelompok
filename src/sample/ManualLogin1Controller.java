package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: FERREL JOHN FERNANDO
 * Date: 03/10/2019.
 * Time: 2:03.
 * To change this template use File | Settings | File Templates.
 */
public class ManualLogin1Controller implements Initializable {
    public Button manlogin;
    public Button mandaftar;
    public Button manedit;
    public Button back;
    public Button next;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manlogin.setOnAction(event -> {
            Stage stage = (Stage) manlogin.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("ManualLogin1.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mandaftar.setOnAction(event -> {
            Stage stage = (Stage) mandaftar.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("ManualDaftar1.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        manedit.setOnAction(event -> {
            Stage stage = (Stage) manedit.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("ManualEdit1.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        next.setOnAction(event -> {
            Stage stage = (Stage) next.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("ManualLogin2.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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
