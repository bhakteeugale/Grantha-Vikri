package com.application.granthavikri.model;

import com.google.firebase.firestore.DocumentReference;

public class EventGroupModel {
    private String group_id;

    private String group_name;

    private int quantity_available;

    private int quantity_sold;

    private double group_price;

    public EventGroupModel() {
    }


    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(int quantity_available) {
        this.quantity_available = quantity_available;
    }

    public int getQuantity_sold() {
        return quantity_sold;
    }

    public void setQuantity_sold(int quantity_sold) {
        this.quantity_sold = quantity_sold;
    }

    public double getGroup_price() {
        return group_price;
    }

    public void setGroup_price(double group_price) {
        this.group_price = group_price;
    }
}
