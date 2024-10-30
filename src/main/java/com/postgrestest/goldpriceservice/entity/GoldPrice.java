package com.postgrestest.goldpriceservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
public class GoldPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Date cannot be null")
    @PastOrPresent(message = "Date must be in the past or today")
    @Column(nullable = false)
    @JsonProperty("data")
    private LocalDate date;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be a positive number")
    @Column(nullable = false)
    @JsonProperty("cena")
    private Double price;

    public GoldPrice() {
    }

    public GoldPrice(LocalDate date, Double price) {
        this.date = date;
        this.price = price;
    }

    public GoldPrice(Long id, LocalDate date, Double price) {
        this.id = id;
        this.date = date;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return "GoldPrice{" +
                "id=" + id +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
