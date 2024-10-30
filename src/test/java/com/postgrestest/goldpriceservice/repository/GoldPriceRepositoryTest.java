package com.postgrestest.goldpriceservice.repository;

import com.postgrestest.goldpriceservice.entity.GoldPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class GoldPriceRepositoryTest {

    @Autowired
    private GoldPriceRepository repository;

    private GoldPrice goldPrice1;
    private GoldPrice goldPrice2;

    @BeforeEach
    void setUp() {
        goldPrice1 = new GoldPrice(LocalDate.of(2023, 1, 1), 100.0);
        goldPrice2 = new GoldPrice(LocalDate.of(2023, 1, 2), 105.0);

        repository.save(goldPrice1);
        repository.save(goldPrice2);
    }

    @Test
    void testFindByDate_Found() {
        // when
        Optional<GoldPrice> result = repository.findByDate(LocalDate.of(2023, 1, 1));

        // then
        assertTrue(result.isPresent());
        assertEquals(100.0, result.get().getPrice());
    }

    @Test
    void testFindByDate_NotFound() {
        // when
        Optional<GoldPrice> result = repository.findByDate(LocalDate.of(2023, 1, 3));

        // then
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteByDateBetween() {
        // given
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 1, 2);

        // when
        repository.deleteByDateBetween(startDate, endDate);

        // then
        assertTrue(repository.findByDate(LocalDate.of(2023, 1, 1)).isEmpty());
        assertTrue(repository.findByDate(LocalDate.of(2023, 1, 2)).isEmpty());
    }

    @Test
    void testDeleteByPrice() {
        // when
        repository.deleteByPrice(105.0);

        // then
        assertTrue(repository.findByDate(LocalDate.of(2023, 1, 2)).isEmpty());
    }

    @Test
    void testSaveGoldPrice() {
        // given
        GoldPrice newPrice = new GoldPrice(LocalDate.of(2023, 1, 3), 110.0);

        // when
        GoldPrice savedPrice = repository.save(newPrice);

        // then
        assertNotNull(savedPrice);
        assertEquals(110.0, savedPrice.getPrice());
        assertTrue(repository.findByDate(LocalDate.of(2023, 1, 3)).isPresent());
    }
}
