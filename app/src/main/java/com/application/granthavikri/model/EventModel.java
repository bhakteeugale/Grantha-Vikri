package com.application.granthavikri.model;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EventModel implements Serializable {
    private String event_id;

    private String event_name;

    private Timestamp event_date;

    private String event_place;

    @Nullable
    private List<EventStockModel> event_stocks;

    @Nullable
    private List<EventGroupModel> event_groups;

    public EventModel() {
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Timestamp getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Timestamp event_date) {
        this.event_date = event_date;
    }

    public String getEvent_place() {
        return event_place;
    }

    public void setEvent_place(String event_place) {
        this.event_place = event_place;
    }

    @Nullable
    public List<EventStockModel> getEvent_stocks() {
        return event_stocks;
    }

    public void setEvent_stocks(@Nullable List<EventStockModel> event_stocks) {
        this.event_stocks = event_stocks;
    }

    @Nullable
    public List<EventGroupModel> getEvent_groups() {
        return event_groups;
    }

    public void setEvent_groups(@Nullable List<EventGroupModel> event_groups) {
        this.event_groups = event_groups;
    }
}
