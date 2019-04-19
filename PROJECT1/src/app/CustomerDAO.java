package app;

import javafx.scene.control.CheckBox;

import java.sql.*;
import java.util.Random;

public class CustomerDAO {

    public static Connection connection;

    public static void insertCustomer(String customer_name, String customer_sur, String customer_email, String customer_phone, String customer_country, int customer_city, String customer_place, String customer_information, String customer_date, String customer_price, String customer_guest, CheckBox checkBox) throws SQLException, SQLSyntaxErrorException, ClassNotFoundException {
        int id = getCustomerId(customer_phone);
        Random r = new Random();
        int ran = 1 + r.nextInt(5);
        String sql = "insert into customer(customer_id, name, surname, email, phone,employee_id) values " +
                "('" + id + "','" + customer_name + "','" + customer_sur + "','" + customer_email + "','" + customer_phone + "','" + ran + "')";
        System.out.print(customer_city);
        String place = "begin" +
                "   pkg_1.proc_1('" + id + "','" + customer_place + "','" + customer_city + "');\n" +
                "    end;";
        String info = "insert into event_type(type_id, info_about_event) values ('" + id + "','" + customer_information + "')";
        String events = "insert into events(event_id, date_of_event,price,number_of_guest,type_id,place_id) values ('" + id + "','" + customer_date + "','" + customer_price + "','" + customer_guest + "','" + id + "','" + id + "')";

        String order = "insert into orders(orders_id, stat_of_importance,customer_id,employee_id,event_id) values ('" + id + "','" + 0 + "','" + id + "','" + ran + "','" + id + "')";
        String payment = "";
        String fee = "";
        if (checkBox.isSelected()) {
            payment = "insert into payment(method_id, method_name) values ('" + id + "','Credit')";
            fee = "insert into fee_schedules(payment_id, amount,date_of_payment,status_of_payment,method_id,orders_id) values ('" + id + "','200','" + customer_date + "','Paid','" + id + "','" + id + "')";
        } else {
            payment = "insert into payment(method_id, method_name) values ('" + id + "','Cash')";
            fee = "insert into fee_schedules(payment_id, amount,date_of_payment,status_of_payment,method_id,orders_id) values ('" + id + "','200','01-DEC-00','Not Paid','" + id + "','" + id + "')";

        }
        try {

            DBUtil.dbExecuteQuery(sql);
            DBUtil.dbExecuteQuery(place);
            DBUtil.dbExecuteQuery(info);
            DBUtil.dbExecuteQuery(events);
            DBUtil.dbExecuteQuery(order);
            DBUtil.dbExecuteQuery(payment);
            DBUtil.dbExecuteQuery(fee);
        } catch (SQLException e) {

            System.out.println("Exception occur while inserting " + e);
            e.printStackTrace();
            throw e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static int getCustomerId(String customer_phone) throws SQLException, ClassNotFoundException {
        Connection connection = new OracleConnection("Projects", "123").getConnection();
        Statement stmt = connection.createStatement();
        String query = "SELECT TYPE_ID FROM EVENT_TYPE";
        ResultSet rs = stmt.executeQuery(query);
        int a = 0;
        while (rs.next()) {
            a = rs.getInt(1);
        }
        return a + 1;
    }

}
