package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
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
import java.sql.*;
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
    public TextField editusername;
    public PasswordField editpassword;
    public TextField editphone;
    public Button update;
    public Button back;
    public Integer count;
    public String username;
    public String phone;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection connection = DataHelper.connect();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT (*) AS total FROM mercubuana.public.session";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                count = resultSet.getInt("total");
            }
            String getuser = "SELECT username FROM mercubuana.public.session WHERE id='"+count+"'";
            ResultSet hasilusername = statement.executeQuery(getuser);
            while (hasilusername.next()){
                username = hasilusername.getString("username");
            }
            String getphone = "SELECT phone FROM mercubuana.public.session WHERE id='"+count+"'";
            ResultSet hasilphone = statement.executeQuery(getphone);
            while (hasilphone.next()){
                phone = hasilphone.getString("phone");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        editusername.setText(username);
        editpassword.setText(phone);

        update.setOnAction(event -> {
            Stage stage = (Stage) update.getScene().getWindow();
            Alert alert;
            if ("".equals(editusername.getText()) || "".equals(editpassword.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "Please fill username/password!");
                alert.show();
            } else {
                Person person = new Person();
                person.setUsername(editusername.getText());
                person.setPassword(editpassword.getText());
                person.setPhone(editphone.getText());

                String insertData = "UPDATE mercubuana.public.user SET name = ?, password = ?, phone = ? WHERE id='"+count+"'";

                try (Connection connection2 = DataHelper.connect();
                     PreparedStatement preparedStatement = connection2.prepareStatement(insertData)) {
                    preparedStatement.setString(1, person.getUsername());
                    preparedStatement.setString(2, person.getPassword());
                    preparedStatement.setString(3, person.getPhone());
                    preparedStatement.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                alert = new Alert(Alert.AlertType.INFORMATION, "Account updated!");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    try {
                        AnchorPane page = FXMLLoader.load(getClass().getResource("InitialForm.fxml"));
                        Scene initialScene = new Scene(page);
                        stage.setScene(initialScene);
                    } catch (Exception ignored) {}
                }
            }
        });

        back.setOnAction(event -> {
            Stage stage = (Stage) back.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("InitialForm.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (Exception ignored) {}
        });
    }
}
