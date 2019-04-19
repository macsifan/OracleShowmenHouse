package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;

public class CustomerRegisterController {

    @FXML
    private TextField customer_name;
    @FXML
    private TextField customer_sur;
    @FXML
    private TextField customer_email;
    @FXML
    private TextField customer_phone;
    @FXML
    private TextField customer_place;
    @FXML
    private TextField customer_information;
    @FXML
    private TextField customer_price;
    @FXML
    private TextField customer_guest;
    @FXML
    private TextField num;
    @FXML
    private TextField mon;
    @FXML
    private TextField yea;
    @FXML
    private TextField ccv;
    @FXML
    private DatePicker customer_date;
    @FXML
    private Button customer_register;
    @FXML
    private CheckBox customer_checkbox;
    @FXML
    private ChoiceBox customer_choisebox;

    public Label nameStatus;
    public Label surnameStatus;
    public Label creditCardStatus;
    public Label contactNumberStatus;
    public Label customerEmailStatus;
    public Label customerCountryStatus;
    public Label customerCityStatus;
    public Label customerPlaceStatus;
    public Label customerInfoStatus;
    public Label customerPriceStatus;
    public Label customerGuestStatus;
    public Label customerCardMonthStatus;
    public Label customerCardYearStatus;
    public Label customerCvvStatus;
    public Label customerPhoneStatus;
    private boolean conditionToSubmit = true;

    @FXML
    private void insertCustomer(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
        if (checkData()) {
            int res1 = 0;
            String city = (String) customer_choisebox.getValue();
            switch (city) {
                case ("Aktau"):
                    res1 = 1;
                    break;
                case ("Aktobe"):
                    res1 = 2;
                    break;
                case ("Atyrau"):
                    res1 = 3;
                    break;
                case ("Karaganda"):
                    res1 = 4;
                    break;
                case ("Kokshetau"):
                    res1 = 5;
                    break;
                case ("Kostanai"):
                    res1 = 6;
                    break;
                case ("Kyzylorda"):
                    res1 = 7;
                    break;
                case ("Pavlodar"):
                    res1 = 8;
                    break;
                case ("Almaty"):
                    res1 = 9;
                    break;
                case ("Taldykorgan"):
                    res1 = 10;
                    break;

                case ("Taraz"):
                    res1 = 11;
                    break;

                case ("Nursultan"):
                    res1 = 12;
                    break;

                case ("Uralsk"):
                    res1 = 13;
                    break;

                case ("Ust-Kamenogorsk"):
                    res1 = 14;
                    break;

            }
            System.out.println(res1);
            LocalDate s1 = customer_date.getValue();
            String s2 = s1.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String res = "";
            switch (s2.substring(3, 5)) {
                case "01":
                    res = "JAN";
                    break;
                case "02":
                    res = "FEB";
                    break;

                case "03":
                    res = "MAR";
                    break;

                case "04":
                    res = "APR";
                    break;

                case "05":
                    res = "MAY";
                    break;

                case "06":
                    res = "JUN";
                    break;

                case "07":
                    res = "JUL";
                    break;

                case "08":
                    res = "AUG";
                    break;

                case "09":
                    res = "SEP";
                    break;

                case "10":
                    res = "OCT";

                case "11":
                    res = "NOV";
                    break;

                case "12":
                    res = "DEC";
                    break;

            }
            String res12 = s2.substring(0, 2) + "-" + res + "-" + s2.substring(8, 10);


            CustomerDAO.insertCustomer(customer_name.getText(), customer_sur.getText(), customer_email.getText(), customer_phone.getText(), "", res1, customer_place.getText(), customer_information.getText(), res12, customer_price.getText(), customer_guest.getText(), customer_checkbox);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/SucReg.fxml"))));
            stage.centerOnScreen();
        }
    }


    public void checkbox(ActionEvent actionEvent) {
        if (customer_checkbox.isSelected()) {
            yea.setDisable(false);
            num.setDisable(false);
            mon.setDisable(false);
            ccv.setDisable(false);
        } else {
            yea.setDisable(true);
            num.setDisable(true);
            mon.setDisable(true);
            ccv.setDisable(true);
        }
    }

    private void checkInput() {


        //namefield activated
        if (customer_name.getText().isEmpty()) {
            nameStatus.setText("Enter the name");
            conditionToSubmit = false;
        } else if (!Pattern.matches("[a-zA-Z]+", customer_name.getText())) {
            nameStatus.setText("");
            nameStatus.setText("Letters only");
            conditionToSubmit = false;
        } else if (!customer_name.getText().isEmpty()) {
            nameStatus.setText("");
        }


        if (customer_email.getText().isEmpty()) {
            customerEmailStatus.setText("Enter the email");
            conditionToSubmit = false;

        } else if (!customer_email.getText().isEmpty()) {
            customerEmailStatus.setText("");
        }

        //surnamefield activated
        if (customer_sur.getText().isEmpty()) {
            surnameStatus.setText("Enter the surname");
            conditionToSubmit = false;
        } else if (!Pattern.matches("[a-zA-Z]+", customer_sur.getText())) {
            surnameStatus.setText("");
            surnameStatus.setText("Letters only");
            conditionToSubmit = false;
        } else if (!customer_sur.getText().isEmpty()) {
            surnameStatus.setText("");
        }

        //creditcardfield activated
        if (customer_checkbox.isSelected()) {
            if (num.getText().isEmpty()) {
                creditCardStatus.setText("Enter the number of credit card");
                conditionToSubmit = false;
            } else if ((!Pattern.matches("[0-9]+", num.getText())) ||
                    num.getText().length() != 16) {
                creditCardStatus.setText("");
                creditCardStatus.setText("16 numbers");
                conditionToSubmit = false;
            } else if (!num.getText().isEmpty()) {
                creditCardStatus.setText("");
            }
        }
        if (customer_place.getText().isEmpty()) {
            customerPlaceStatus.setText("Enter the place");
            conditionToSubmit = false;
        } else if (!customer_place.getText().isEmpty()) {
            customerPlaceStatus.setText("");
        }

        if (customer_price.getText().isEmpty()) {
            customerPriceStatus.setText("Enter the price");
            conditionToSubmit = false;
        } else if (!Pattern.matches("[0-9]+", customer_price.getText())) {
            customerPriceStatus.setText("");
            customerPriceStatus.setText("Numbers only");
            conditionToSubmit = false;
        } else if (!customer_price.getText().isEmpty()) {
            customerPriceStatus.setText("");
        }


        if (customer_guest.getText().isEmpty()) {
            customerGuestStatus.setText("Enter the guest");
            conditionToSubmit = false;
        } else if (!Pattern.matches("[0-9]+", customer_guest.getText())) {
            customerGuestStatus.setText("");
            customerGuestStatus.setText("Numbers only");
            conditionToSubmit = false;
        } else if (!customer_guest.getText().isEmpty()) {
            customerGuestStatus.setText("");
        }


        if (customer_information.getText().isEmpty()) {
            customerInfoStatus.setText("Enter the information");
            conditionToSubmit = false;
        } else if (!customer_information.getText().isEmpty()) {
            customerInfoStatus.setText("");
        }


        //contactnumberfield activated
        if (customer_phone.getText().isEmpty()) {
            contactNumberStatus.setText("Enter the contact number");
            conditionToSubmit = false;
        } else if (!Pattern.matches("[0-9]+", customer_phone.getText()) && customer_phone.getText().length() <= 9) {

            contactNumberStatus.setText("");
            contactNumberStatus.setText("Numbers only");
            conditionToSubmit = false;
        } else if (!customer_phone.getText().isEmpty()) {
            customerPhoneStatus.setText("");
        }


    }

    public boolean checkData() {
        conditionToSubmit = true;
        checkInput();
        if (conditionToSubmit == true) {
            return true;
        } else {
            return false;
        }
    }

    public void openMain(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("resources/main1.fxml"))));
        stage.centerOnScreen();
    }
}
