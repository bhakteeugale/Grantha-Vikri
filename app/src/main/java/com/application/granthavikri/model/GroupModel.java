package com.application.granthavikri.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupModel implements Serializable {
    private String group_id;

    private String group_name;

    private Timestamp group_date;

    private float group_price;

    private List<StockModel> group_stocks;

    public GroupModel() {
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

    public Timestamp getGroup_date() {
        return group_date;
    }

    public void setGroup_date(Timestamp group_date) {
        this.group_date = group_date;
    }

    public float getGroup_price() {
        return group_price;
    }

    public void setGroup_price(float group_price) {
        this.group_price = group_price;
    }

    public List<StockModel> getGroup_stocks() {
        return group_stocks;
    }

    public void setGroup_stocks(List<StockModel> group_stocks) {
        this.group_stocks = group_stocks;
    }
}
