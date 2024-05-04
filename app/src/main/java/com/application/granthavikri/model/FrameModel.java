package com.application.granthavikri.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class FrameModel implements Serializable {

    @Exclude
    private String frame_id;

    private String name;

    private Timestamp date;

    @Nullable
    private String description;

    private int available_quantity;

    private double printed_price;

    private double discounted_price;

    private int sold_quantity;

    private int frame_size;


    public FrameModel() {
    }

    public FrameModel(String frame_id, String name, Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity, int frame_size) {
        this.frame_id = frame_id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
        this.frame_size = frame_size;
    }

    public FrameModel(String name, Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity, int frame_size) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
        this.frame_size = frame_size;
    }

    public String getFrame_id() {
        return frame_id;
    }

    public void setFrame_id(String frame_id) {
        this.frame_id = frame_id;
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

    public int getFrame_size() {
        return frame_size;
    }

    public void setFrame_size(int frame_size) {
        this.frame_size = frame_size;
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
