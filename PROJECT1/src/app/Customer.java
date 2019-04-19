package app;

public class Customer {

    private int customer_id;
    private String name;
    private String surname;
    private String email;
    private int phone;



    public Customer(int customer_id, String name, String surname, String email, int phone) {
        this.customer_id = customer_id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;


    }
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id( int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }



}
