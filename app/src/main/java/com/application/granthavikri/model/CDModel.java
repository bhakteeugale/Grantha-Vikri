package com.application.granthavikri.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class CDModel implements Serializable {

    @Exclude
    private String cd_id;

    private String name;

    private Timestamp date;

    @Nullable
    private String description;

    private int available_quantity;

    private double printed_price;

    private double discounted_price;

    private int sold_quantity;


    public CDModel() {
    }

    public CDModel(String cd_id, String name, Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity) {
        this.cd_id = cd_id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
    }

    public CDModel(String name, Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
    }

    public String getCd_id() {
        return cd_id;
    }

    public void setCd_id(String cd_id) {
        this.cd_id = cd_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public int getAvailable_quantity() {
        return available_quantity;
    }

    public void setAvailable_quantity(int available_quantity) {
        this.available_quantity = available_quantity;
    }

    public double getPrinted_price() {
        return printed_price;
    }

    public void setPrinted_price(double printed_price) {
        this.printed_price = printed_price;
    }

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }
}
