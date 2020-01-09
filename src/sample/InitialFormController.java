package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: FERREL JOHN FERNANDO
 * Date: 03/10/2019.
 * Time: 2:08.
 * To change this template use File | Settings | File Templates.
 */
public class InitialFormController implements Initializable {
    public Button logout;
    public Label greeting;
    public Button video;
    public Integer count;
    public String username;
//    public File pic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        FileChooser fileChooser = new FileChooser();
////        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
////        fileChooser.getExtensionFilters().addAll(ext1);
////
////        video.setOnAction(event -> {
////            Stage vidstage = (Stage) video.getScene().getWindow();
////            pic = fileChooser.showOpenDialog(vidstage);
////            BufferedImage bufferedImage;
////            try {
////                bufferedImage = ImageIO.read(pic);
////                File filepath = new File("picture.jpg");
////                ImageIO.write(bufferedImage, "JPG", filepath);
////                String insertData = "INSERT INTO mercubuana.public.media(media_path) VALUES(?)";
////                Connection connection = DataHelper.connect();
////                PreparedStatement preparedStatement = connection.prepareStatement(insertData);
////                preparedStatement.setString(1, filepath.getAbsolutePath());
////                preparedStatement.execute();
////            } catch (IOException | SQLException e) {
////                e.printStackTrace();
////            }
////        });
        Connection connection = DataHelper.connect();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT COUNT (*) AS total FROM mercubuana.public.session";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                count = resultSet.getInt("total");
            }
            String getuser = "SELECT username FROM mercubuana.public.session WHERE id='"+count+"'";
            ResultSet hasil = statement.executeQuery(getuser);
            while (hasil.next()){
                username = hasil.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        greeting.setText(username);

        logout.setOnAction(event -> {
            Stage stage = (Stage) logout.getScene().getWindow();
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
