package com.application.granthavikri.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class SaleModel {
    private String sale_id;

    private Timestamp time;

    private double total_amount;

    private List<UpdateEventItemsModel> items_sold;

    private int inventory_count;

    private int group_count;

    private String payment_method;

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public List<UpdateEventItemsModel> getItems_sold() {
        return items_sold;
    }

    public void setItems_sold(List<UpdateEventItemsModel> items_sold) {
        this.items_sold = items_sold;
    }

    public int getInventory_count() {
        return inventory_count;
    }

    public void setInventory_count(int inventory_count) {
        this.inventory_count = inventory_count;
    }

    public int getGroup_count() {
        return group_count;
    }

    public void setGroup_count(int group_count) {
        this.group_count = group_count;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
