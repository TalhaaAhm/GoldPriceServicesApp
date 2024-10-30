package com.postgrestest.goldpriceservice.service;

import com.postgrestest.goldpriceservice.entity.GoldPrice;
import com.postgrestest.goldpriceservice.repository.GoldPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoldPriceServiceTest {

    @Mock
    private GoldPriceRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GoldPriceService goldPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSyncGoldPrices() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);
        String url = "https://api.nbp.pl/api/cenyzlota/2023-01-01/2023-01-10";

        GoldPrice[] prices = new GoldPrice[] {
                new GoldPrice(LocalDate.of(2023, 1, 1), 100.0),
                new GoldPrice(LocalDate.of(2023, 1, 2), 101.0)
        };

        when(restTemplate.getForObject(url, GoldPrice[].class)).thenReturn(prices);

        // when
        goldPriceService.syncGoldPrices(startDate, endDate);

        // then
        verify(restTemplate).getForObject(url, GoldPrice[].class);
        verify(repository).saveAll(List.of(prices));
    }

    @Test
    void testGetGoldPrice_Found() {
        // given
        LocalDate date = LocalDate.of(2023, 1, 1);
        GoldPrice expectedPrice = new GoldPrice(date, 100.0);
        when(repository.findByDate(date)).thenReturn(Optional.of(expectedPrice));

        // when
        GoldPrice result = goldPriceService.getGoldPrice(date);

        // then
        assertNotNull(result);
        assertEquals(expectedPrice.getDate(), result.getDate());
        assertEquals(expectedPrice.getPrice(), result.getPrice());
    }

    @Test
    void testGetGoldPrice_NotFound() {
        // given
        LocalDate date = LocalDate.of(2023, 1, 1);
        when(repository.findByDate(date)).thenReturn(Optional.empty());

        // when & then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            goldPriceService.getGoldPrice(date);
        });
        assertEquals("Gold price not found for date: 2023-01-01", exception.getMessage());
    }

    @Test
    void testDeleteGoldPricesByRange() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 10);

        // when
        goldPriceService.deleteGoldPricesByRange(startDate, endDate);

        // then
        verify(repository).deleteByDateBetween(startDate, endDate);
    }

    @Test
    void testDeleteGoldPriceByValue() {
        // given
        Double price = 150.0;

        // when
        goldPriceService.deleteGoldPriceByValue(price);

        // then
        verify(repository).deleteByPrice(price);
    }
}
