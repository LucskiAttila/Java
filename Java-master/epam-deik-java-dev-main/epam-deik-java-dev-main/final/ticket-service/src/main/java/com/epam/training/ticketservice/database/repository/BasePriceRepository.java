package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.BasePrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasePriceRepository extends JpaRepository<BasePrice, Integer> {
}
