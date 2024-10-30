package com.postgrestest.goldpriceservice.repository;


import com.postgrestest.goldpriceservice.entity.GoldPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GoldPriceRepository extends JpaRepository<GoldPrice, Long> {
    Optional<GoldPrice> findByDate(LocalDate date);

    void deleteByDateBetween(LocalDate startDate, LocalDate endDate);

    void deleteByPrice(Double price);
}