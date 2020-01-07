package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
public class EditProfileController implements Initializable {
    public TextField username;
    public PasswordField password;
    public TextField phone;
    public Button register;
    public Button back;
    public Button profilepic1;
    public File file;
    public Desktop desktop = Desktop.getDesktop();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1,ext2);

        profilepic1.setOnAction((ActionEvent t) -> {
            //Button profilepic1 = new Button("Open a Picture...");
            Stage picstage = (Stage) profilepic1.getScene().getWindow();
            file = fileChooser.showOpenDialog(picstage);
        });

//        final GridPane inputGridPane = new GridPane();
//        GridPane.setConstraints(profilepic1, 0, 1);
//        inputGridPane.setHgap(6);
//        inputGridPane.setVgap(6);
//        inputGridPane.getChildren().addAll(profilepic1);
//        Pane abc = new VBox(12);
//        abc.getChildren().addAll(inputGridPane);
//        //abc.setPadding(new Insets(12, 12, 12, 12));
//
//        picstage.setScene(new Scene(abc));
//        picstage.setTitle("Insert Image Into Database");
//        picstage.show();

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
                    preparedStatement.setBinaryStream(4, fin, (int)(file.length()));
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

    private void openFile() {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(EditProfileController.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}
