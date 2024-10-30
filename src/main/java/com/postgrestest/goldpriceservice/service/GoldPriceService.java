package com.postgrestest.goldpriceservice.service;

import com.postgrestest.goldpriceservice.entity.GoldPrice;
import com.postgrestest.goldpriceservice.repository.GoldPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class GoldPriceService {

    private static final String API_URL = "https://api.nbp.pl/api/cenyzlota/";

    @Autowired
    private GoldPriceRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void syncGoldPrices(LocalDate startDate, LocalDate endDate) {
        String url = API_URL + startDate + "/" + endDate;
        System.out.println("Fetching from URL: " + url);

        GoldPrice[] prices = restTemplate.getForObject(url, GoldPrice[].class);
        repository.saveAll(List.of(prices));
    }

    @Cacheable(value = "goldPriceCache", key = "#date")
    public GoldPrice getGoldPrice(LocalDate date) {
        return repository.findByDate(date)
                .orElseThrow(() -> new RuntimeException("Gold price not found for date: " + date));
    }

    @Transactional
    public void deleteGoldPricesByRange(LocalDate startDate, LocalDate endDate) {
        repository.deleteByDateBetween(startDate, endDate);
    }

    @Transactional
    public void deleteGoldPriceByValue(Double price) {
        repository.deleteByPrice(price);
    }

    @Transactional
    public GoldPrice saveGoldPrice(GoldPrice goldPrice) {
        return repository.save(goldPrice);
    }
}
