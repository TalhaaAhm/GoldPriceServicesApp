package com.postgrestest.goldpriceservice.dto;

import java.time.LocalDate;

public class GoldPriceResponse {

    private LocalDate date;
    private Double price;

    public GoldPriceResponse() {
    }

    public GoldPriceResponse(LocalDate date, Double price) {
        this.date = date;
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoldPriceResponse{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
