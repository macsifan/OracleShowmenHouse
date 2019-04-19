package app;

public class Orders {
    private int orders_id;
    private String name;
    private String place;
    private String event;
    private int price;
    private int guest;
    private String info;
    private String date;
    private String status;
    private String method;

    public Orders(int orders_id, String name, String place, String event, int price, int guest, String info, String date, String status, String method) {
        this.orders_id = orders_id;
        this.name = name;
        this.place = place;
        this.event = event;
        this.price = price;
        this.guest = guest;
        this.info = info;
        this.date = date;
        this.status = status;
        this.method = method;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getEvent() {
        return event;
    }

    public int getPrice() {
        return price;
    }

    public int getGuest() {
        return guest;
    }

    public String getInfo() {
        return info;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getMethod() {
        return method;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
