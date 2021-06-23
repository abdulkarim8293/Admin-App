package com.abdulkarim.adminapp.modal;

import java.io.Serializable;

public class MyOrder implements Serializable {

    private String id;
    private String price;
    private String user_id;
    private String order_status;
    private String delivery_charge;
    private String place_date;

    public MyOrder() {
    }

    public MyOrder(String id, String price, String user_id, String order_status, String delivery_charge, String place_date) {
        this.id = id;
        this.price = price;
        this.user_id = user_id;
        this.order_status = order_status;
        this.delivery_charge = delivery_charge;
        this.place_date = place_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getPlace_date() {
        return place_date;
    }

    public void setPlace_date(String place_date) {
        this.place_date = place_date;
    }
}
