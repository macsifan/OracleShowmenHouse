package app;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import static app.LoginFXMLController.Employee_id;

public class NavigatorsFXMLController implements Initializable {

    public Connection connection;
    @FXML
    private Pane pane1, pane2, pane3, pane4, pane5;
    @FXML
    private JFXButton btn1, btn2, btn3, btn4, btn5;
    @FXML
    private VBox pane;
    @FXML
    private LineChart<?, ?> line_chart;
    @FXML
    private PieChart pie_chart1, pie_chart2, pie_chart3;
    @FXML
    private TableView<Customer> emp_table;

    @FXML
    private TableColumn<Customer, Integer> col_id, col_phone;
    @FXML
    private TableColumn<Customer, String> col_name, col_surname, col_email;
    @FXML
    private TextField emp_search, emp_search1;
    @FXML
    private TableView<Orders> emp_table1;
    @FXML
    private TableColumn<Orders, String> col_Info, col_Date_Payment, col_Status_Payment, col_Method_Name, col_CityName, col_Place_Name, col_Date_of_Event;
    @FXML
    private TableColumn<Orders, Integer> col_id1, col_Price, col_Guest;

    @FXML
    private Label ename, edolz;

    private ObservableList<Customer> employees = FXCollections.observableArrayList();
    private ObservableList<Orders> employees1 = FXCollections.observableArrayList();

    FilteredList filter = new FilteredList(employees);
    FilteredList filter1 = new FilteredList(employees1);
    int counter = 0, counter1 = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.addDraggableNode(this.pane);
        try {
            connection = new OracleConnection("Projects", "123").getConnection();
            if (counter < 1) {
                buildTable();
                counter = 2;
            }
            pane4.toFront();
            buildInfo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlerMenuButton(ActionEvent event) throws SQLException {
        if (event.getSource() == btn3) {
            if (counter1 < 1) {
                buildTable1();
                counter1 = 2;
            }
            pane3.toFront();
        } else if (event.getSource() == btn4) {

            pane4.toFront();
        } else if (event.getSource() == btn5) {
            statsLineChart();
            statsFirstPieChart();
            statsSecondPieChart();
//            statsThirdPieChart();
            pane5.toFront();
        }
    }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public void buildInfo() throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "SELECT employee.name,employee.surname,position.name,position.salary FROM EMPLOYEE  \n" +
                "JOIN POSITION ON position.position_id = employee.employee_id\n" +
                " WHERE EMPLOYEE_ID=" + Employee_id;
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        ename.setText(rs.getString(1) + " " + rs.getString(2));
        edolz.setText(rs.getString(3));
    }

    @FXML
    private void employeeSearch() {
        emp_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filter.setPredicate((Predicate<? super Customer>) (Customer customer) -> {

                if (newValue.isEmpty() || newValue == null) return true;
                else if (customer.getName().contains(newValue) || customer.getSurname().contains(newValue) || customer.getEmail().contains(newValue) || Integer.toString(customer.getPhone()).contains(newValue)) {
                    return true;
                }

                return false;
            });
        });

        emp_table.setItems(filter);
    }

    private void buildTable() throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "SELECT customer.customer_id,customer.name,customer.surname,departments.description, customer.phone FROM CUSTOMER JOIN departments ON departments.id =customer.customer_id  WHERE customer.employee_id=" + Employee_id + " ORDER BY customer.customer_id ASC";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next())
            employees.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5)));

        col_id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customer_id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        col_surname.setCellValueFactory(new PropertyValueFactory<Customer, String>("surname"));
        col_email.setCellValueFactory(new PropertyValueFactory<Customer, String>("email"));
        col_phone.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("phone"));
        emp_table.setItems(employees);
    }

    @FXML
    private void employeeSearch1() {
        emp_search1.textProperty().addListener((observable, oldValue, newValue) -> {
            filter1.setPredicate((Predicate<? super Orders>) (Orders orders) -> {

                if (newValue.isEmpty() || newValue == null) return true;
                else if (orders.getName().contains(newValue) || orders.getPlace().contains(newValue) || Integer.toString(orders.getPrice()).contains(newValue)) {
                    return true;
                }

                return false;
            });
        });

        emp_table1.setItems(filter1);
    }


    private void buildTable1() throws SQLException {
        Statement stmt = connection.createStatement();
        String query = "SELECT events.event_id,city.city_name,place.place_name,events.date_of_event,events.price,events.number_of_guest, event_type.info_about_event,fee_schedules.date_of_payment,fee_schedules.status_of_payment,payment.method_name FROM EVENTS \n" +
                "                JOIN ORDERS ON orders.event_id = events.event_id\n" +
                "                JOIN PLACE ON events.place_id=place.place_id \n" +
                "                JOIN CITY ON place.city_id =city.city_id\n" +
                "                JOIN EVENT_TYPE ON events.type_id=event_type.type_id\n" +
                "                JOIN fee_schedules  ON fee_schedules.orders_id=orders.orders_id \n" +
                "                JOIN PAYMENT ON fee_schedules.method_id=payment.method_id\n" +
                "                WHERE ORDERS.EMPLOYEE_ID= " + Employee_id +
                "                ORDER BY events.event_id ASC";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next())
            employees1.add(new Orders(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4).substring(0, 11), rs.getInt(5), rs.getInt(6), rs.getString(7), rs.getString(8).substring(0, 11), rs.getString(9), rs.getString(10)));

        col_id1.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("orders_id"));
        col_CityName.setCellValueFactory(new PropertyValueFactory<Orders, String>("name"));
        col_Place_Name.setCellValueFactory(new PropertyValueFactory<Orders, String>("place"));
        col_Date_of_Event.setCellValueFactory(new PropertyValueFactory<Orders, String>("event"));
        col_Price.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("price"));
        col_Guest.setCellValueFactory(new PropertyValueFactory<Orders, Integer>("guest"));
        col_Info.setCellValueFactory(new PropertyValueFactory<Orders, String>("info"));
        col_Date_Payment.setCellValueFactory(new PropertyValueFactory<Orders, String>("date"));
        col_Status_Payment.setCellValueFactory(new PropertyValueFactory<Orders, String>("status"));
        col_Method_Name.setCellValueFactory(new PropertyValueFactory<Orders, String>("method"));
        emp_table1.setItems(employees1);
    }

    private void statsLineChart() throws SQLException {
        line_chart.getData().clear();
        Statement stmt = connection.createStatement();
        String query = "SELECT city.city_name,events.price,events.number_of_guest FROM EVENTS\n" +
                "JOIN ORDERS ON orders.event_id =events.event_id\n" +
                "JOIN PLACE ON place.place_id = events.place_id\n" +
                "JOIN CITY ON place.city_id=city.city_id\n" +
                "WHERE ORDERS.EMPLOYEE_ID=" + Employee_id;
        ResultSet rs = stmt.executeQuery(query);
        HashMap<String, XYChart.Series> data = new HashMap<>();

        while (rs.next()) {
            if (!data.containsKey(rs.getString(1))) {
                XYChart.Series series = new XYChart.Series();
                series.setName(rs.getString(1));
                series.getData().add(new XYChart.Data(rs.getString(3), rs.getInt(2)));
                data.put(rs.getString(1), series);
            } else {
                System.out.println("asd");

                XYChart.Series series = data.get(rs.getString(1));
                series.getData().add(new XYChart.Data(rs.getString(2), rs.getInt(3)));
                data.put(rs.getString(1), series);
            }
        }

        for (XYChart.Series series : data.values()) {
            line_chart.getData().add(series);
        }
    }

    private void statsFirstPieChart() throws SQLException {
        pie_chart1.getData().clear();
        Statement stmt = connection.createStatement();
        String query = "SELECT events.date_of_event,events.price FROM EVENTS \n" +
                "JOIN ORDERS ON orders.orders_id = events.event_id\n" +
                "JOIN EVENT_TYPE ON events.event_id=event_type.type_id\n" +
                "WHERE ORDERS.EMPLOYEE_ID=" + Employee_id;
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            PieChart.Data slice = new PieChart.Data(rs.getString(1).substring(0, 11), rs.getInt(2));

            pie_chart1.getData().add(slice);
            pie_chart1.setLabelsVisible(false);
        }

    }


    private void statsSecondPieChart() throws SQLException {
        pie_chart2.getData().clear();
        Statement stmt = connection.createStatement();
        String query = "SELECT place.place_name,events.number_of_guest FROM EVENTS \n" +
                "JOIN ORDERS ON orders.orders_id = events.event_id\n" +
                "JOIN PLACE ON events.event_id=place.place_id \n" +
                "WHERE ORDERS.EMPLOYEE_ID=" + Employee_id;
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            PieChart.Data slice = new PieChart.Data(rs.getString(1), rs.getInt(2));
            pie_chart2.getData().add(slice);
        }
        pie_chart2.setLabelsVisible(false);


    }

}
