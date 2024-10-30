package com.postgrestest.goldpriceservice.controller;

import com.postgrestest.goldpriceservice.entity.GoldPrice;
import com.postgrestest.goldpriceservice.service.GoldPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

class GoldPriceControllerTest {

    @Mock
    private GoldPriceService goldPriceService;

    @InjectMocks
    private GoldPriceController goldPriceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSyncPrices() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        // when
        goldPriceController.syncPrices(startDate, endDate);

        // then
        verify(goldPriceService).syncGoldPrices(eq(startDate), eq(endDate));
    }

    @Test
    void testGetPriceByDate() {
        // given
        LocalDate date = LocalDate.of(2023, 1, 1);
        GoldPrice expectedPrice = new GoldPrice();
        expectedPrice.setDate(date);
        expectedPrice.setPrice(100.0);

        when(goldPriceService.getGoldPrice(date)).thenReturn(expectedPrice);

        // when
        GoldPrice result = goldPriceController.getPriceByDate(date);

        // then
        assertNotNull(result);
        assertEquals(expectedPrice.getDate(), result.getDate());
        assertEquals(expectedPrice.getPrice(), result.getPrice());
    }

    @Test
    void testDeletePricesByRange() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 31);

        // when
        goldPriceController.deletePricesByRange(startDate, endDate);

        // then
        verify(goldPriceService).deleteGoldPricesByRange(eq(startDate), eq(endDate));
    }

    @Test
    void testDeletePriceByValue() {
        // given
        Double value = 150.0;

        // when
        goldPriceController.deletePriceByValue(value);

        // then
        verify(goldPriceService).deleteGoldPriceByValue(eq(value));
    }
}
