package com.postgrestest.goldpriceservice.dto;

import java.time.LocalDate;

public class GoldPriceRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    public GoldPriceRequest() {
    }

    public GoldPriceRequest(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "GoldPriceRequest{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}