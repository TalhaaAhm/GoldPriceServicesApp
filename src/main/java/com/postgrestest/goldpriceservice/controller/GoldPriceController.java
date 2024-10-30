package com.postgrestest.goldpriceservice.controller;

import com.postgrestest.goldpriceservice.entity.GoldPrice;
import com.postgrestest.goldpriceservice.service.GoldPriceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/gold")
public class GoldPriceController {

    @Autowired
    public GoldPriceService service;

    @Operation(summary = "Fetch & sync gold prices from external API")
    @PostMapping("/sync")
    public void syncPrices(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        service.syncGoldPrices(startDate, endDate);
    }

    @Operation(summary = "Create a new gold price entry")
    @PostMapping
    public GoldPrice createGoldPrice(@RequestBody @Valid GoldPrice goldPrice) {
        return service.saveGoldPrice(goldPrice);
    }

    @Operation(summary = "Get gold price by date")
    @GetMapping("/{date}")
    public GoldPrice getPriceByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return service.getGoldPrice(date);
    }

    @Operation(summary = "Delete gold prices by date range")
    @DeleteMapping("/range")
    public void deletePricesByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        service.deleteGoldPricesByRange(startDate, endDate);
    }

    @Operation(summary = "Delete gold prices by value")
    @DeleteMapping("/price/{value}")
    public void deletePriceByValue(@PathVariable Double value) {
        service.deleteGoldPriceByValue(value);
    }
}
