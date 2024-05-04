package com.application.granthavikri.model;

public class EventStockModel {
    private StockModel stock;

    private double price;

    private int quantity_available;

    private int quantity_sold;

    public EventStockModel() {
    }


    public StockModel getStock() {
        return stock;
    }

    public void setStock(StockModel stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
