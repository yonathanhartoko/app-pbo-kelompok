package sample;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: FERREL JOHN FERNANDO
 * Date: 03/10/2019.
 * Time: 2:05.
 * To change this template use File | Settings | File Templates.
 */
public class RegisterFormController implements Initializable {
    public TextField username;
    public PasswordField password;
    public TextField phone;
    public Button register;
    public Button back;
    public Button profilepic1;
    public FileChooser fileChooser;
    public BufferedImage bf;
    public File file;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        profilepic1.setOnAction((ActionEvent t) -> {
            Stage stage = (Stage) profilepic1.getScene().getWindow();
            Pane bp = new VBox(12);
            Scene scene = new Scene(bp);
            stage.setScene(scene);
            stage.setTitle("Insert Image Into Database");
            stage.show();
            Button profilepic1 = new Button("Open a Picture...");
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
            FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
            fileChooser.getExtensionFilters().addAll(ext1,ext2);
            File file = fileChooser.showOpenDialog(stage);
        });

        register.setOnAction(event -> {
            Stage stage = (Stage) register.getScene().getWindow();
            Alert alert;
            if ("".equals(username.getText()) || "".equals(password.getText()) || file == null) {
                alert = new Alert(Alert.AlertType.ERROR, "Please fill username/password!");
                alert.show();
            } else {
                Person person = new Person();
                person.setUsername(username.getText());
                person.setPassword(password.getText());
                person.setPhone(phone.getText());

                String insertData = "INSERT INTO mercubuana.public.user(name, password, phone, image) VALUES(?, ?, ?, ?)";

                try (Connection connection = DataHelper.connect();
                     FileInputStream fin = new FileInputStream(file);
                     PreparedStatement preparedStatement = connection.prepareStatement(insertData)) {
                    preparedStatement.setString(1, person.getUsername());
                    preparedStatement.setString(2, person.getPassword());
                    preparedStatement.setString(3, person.getPhone());
                    preparedStatement.setBinaryStream(4, fin);
                    preparedStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                alert = new Alert(Alert.AlertType.INFORMATION, "Account registered!");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        AnchorPane page = FXMLLoader.load(getClass().getResource("LoggedForm.fxml"));
                        Scene initialScene = new Scene(page);
                        stage.setScene(initialScene);
                    } catch (Exception ignored) {}
                }
            }
        });

        back.setOnAction(event -> {
            Stage stage = (Stage) back.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("LoggedForm.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (Exception ignored) {}
        });
    }
}
