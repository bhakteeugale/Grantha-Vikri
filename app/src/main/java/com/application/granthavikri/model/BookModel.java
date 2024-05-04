package com.application.granthavikri.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.io.Serializable;

public class BookModel implements Serializable {
    private String book_id;

    private String name;

    private Timestamp date;

    @Nullable
    private String description;

    private int available_quantity;

    private double printed_price;

    private double discounted_price;

    private int sold_quantity;

    @Nullable
    private String cover_photo;

    @Nullable
    private String nickname;

    @Nullable
    private String edition;


    public BookModel() {
    }

    public BookModel(String book_id, String name, com.google.firebase.Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity, @Nullable String cover_photo, @Nullable String nickname, @Nullable String edition) {
        this.book_id = book_id;
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
        this.cover_photo = cover_photo;
        this.nickname = nickname;
        this.edition = edition;
    }

    public BookModel(String name, com.google.firebase.Timestamp date, @Nullable String description, int available_quantity, double printed_price, double discounted_price, int sold_quantity, @Nullable String cover_photo, @Nullable String nickname, @Nullable String edition) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.available_quantity = available_quantity;
        this.printed_price = printed_price;
        this.discounted_price = discounted_price;
        this.sold_quantity = sold_quantity;
        this.cover_photo = cover_photo;
        this.nickname = nickname;
        this.edition = edition;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
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

    @Nullable
    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(@Nullable String cover_photo) {
        this.cover_photo = cover_photo;
    }

    @Nullable
    public String getNickname() {
        return nickname;
    }

    public void setNickname(@Nullable String nickname) {
        this.nickname = nickname;
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

    @Nullable
    public String getEdition() {
        return edition;
    }

    public void setEdition(@Nullable String edition) {
        this.edition = edition;
    }

    public int getSold_quantity() {
        return sold_quantity;
    }

    public void setSold_quantity(int sold_quantity) {
        this.sold_quantity = sold_quantity;
    }
}
