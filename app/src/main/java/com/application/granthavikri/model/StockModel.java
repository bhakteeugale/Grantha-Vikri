package com.application.granthavikri.model;


import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.sql.Time;

public class StockModel implements Serializable {
    private String stock_id;

    private String stock_name;

    private String type;


    public StockModel() {
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }
}
