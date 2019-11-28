package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by IntelliJ IDEA.
 * User: FERREL JOHN FERNANDO
 * Date: 03/10/2019.
 * Time: 2:03.
 * To change this template use File | Settings | File Templates.
 */
public class LoggedFormController implements Initializable {
    public TextField username;
    public PasswordField password;
    public Button signIn;
    public Button signUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signUp.setOnAction(event -> {
            Stage stage = (Stage) signUp.getScene().getWindow();
            try {
                AnchorPane page = FXMLLoader.load(getClass().getResource("RegisterForm.fxml"));
                Scene scene = new Scene(page);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        signIn.setOnAction(event -> {
            Stage stage = (Stage) signIn.getScene().getWindow();
            Alert alert;
            if (username.getText().equals("") || password.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please fill username/password!");
                alert.show();
            } else {
                Person person = new Person();
                String query = "SELECT * FROM mercubuana.public.user";

                try (Connection connection = DataHelper.connect();
                     Statement st = connection.createStatement();
                     ) {
                    ResultSet rs;

                    rs = st.executeQuery(query);

                    while (rs.next()) {
                        person.setUsername(rs.getString("name"));
                        person.setPassword(rs.getString("password"));
                        person.setPhone(rs.getString("phone"));
                    }

                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                if (person.getUsername() == null) {
                    alert = new Alert(Alert.AlertType.ERROR, "User aren't registered yet!");
                    alert.show();
                } else if (!person.getPassword().equals(this.password.getText())) {
                    alert = new Alert(Alert.AlertType.ERROR, "Username/Password not match!");
                    alert.show();
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialForm.fxml"));
                        stage.setScene(new Scene(
                                loader.load()
                        ));
                        InitialFormController controller = loader.getController();
                        controller.greeting.setText("Selamat datang, " + person.getUsername());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
