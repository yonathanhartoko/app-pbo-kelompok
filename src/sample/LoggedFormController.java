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
public class LoggedFormController implements Initializable {
    public TextField username;
    public TextField finduser;
    public PasswordField password;
    public MenuItem help1;
    public MenuItem help2;
    public Button signIn;
    public Button signUp;
    public Button search;
    public Label searchresult;
    public Integer count;
    public String user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        help1.setOnAction(event -> {
            Stage stage = (Stage) signUp.getScene().getWindow();
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("signinhelp.fxml"));
                Scene scene = new Scene(pane);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        help2.setOnAction(event -> {
            Stage stage = (Stage) signUp.getScene().getWindow();
            try {
                AnchorPane pane = FXMLLoader.load(getClass().getResource("signuphelp.fxml"));
                Scene scene = new Scene(pane);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

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

        search.setOnAction(event -> {
            Connection connection = DataHelper.connect();
            try {
                Statement statement = connection.createStatement();
                String query = "SELECT COUNT (*) AS total FROM mercubuana.public.user WHERE name LIKE '%"+finduser.getText()+"%'";
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()){
                    count = resultSet.getInt("total");
                }
                Alert alert;
                if (count == 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION, "No user found!");
                    alert.show();
                } else {
                    alert = new Alert(Alert.AlertType.INFORMATION, count+" user found!");
                    alert.show();
                }
            } catch (SQLException e) {
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
                    String query2 = "INSERT INTO mercubuana.public.session(username) VALUES(?)";
                    try (Connection connection2 = DataHelper.connect();
                         PreparedStatement preparedStatement = connection2.prepareStatement(query2)){
                        preparedStatement.setString(1, person.getUsername());
                        preparedStatement.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getMessage());
                    }

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("InitialForm.fxml"));
                        stage.setScene(new Scene(
                                loader.load()
                        ));
                        InitialFormController controller = loader.getController();
//                        controller.greeting.setText(person.getUsername());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
