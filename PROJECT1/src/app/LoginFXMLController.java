package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginFXMLController implements Initializable {

    public static Integer Employee_id;
    @FXML
    private AnchorPane pane;
    @FXML
    private Button connect_button;
    @FXML
    private Text message;
    @FXML
    private TextField user_id;
    @FXML
    private PasswordField password;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.addDraggableNode(this.pane);
    }

    @FXML
    public void connects(ActionEvent event) {
        if (verifyFields()) {
            message.setText("Complete all fields");
            return;
        }

        String user = user_id.getText();

        try {
            Connection connection = new OracleConnection("Projects", "123").getConnection();
            Statement stmt = connection.createStatement();
            String query = "SELECT EMPLOYEE_ID,NAME,PASSWORD FROM EMPLOYEE";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (user.equals(rs.getString(2)) && password.getText().equals(rs.getString(3))) {
                    Employee_id = rs.getInt(1);
                    message.setText("Connection is success !");
                    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/navigators.fxml"))));
                    stage.centerOnScreen();
                }

            }
            message.setText("Invalid username/password");

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
            message.setText("Invalid username/password");
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    private boolean verifyFields() {
        return user_id.getText().trim().isEmpty() || password.getText().trim().isEmpty();
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void register(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/CustomerRegister.fxml"))));
    }

    public void openMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/main1.fxml"))));

    }
}
