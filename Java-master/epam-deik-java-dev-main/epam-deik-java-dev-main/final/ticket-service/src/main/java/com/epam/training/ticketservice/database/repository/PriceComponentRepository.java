package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceComponentRepository extends JpaRepository<PriceComponent, String> {
    PriceComponent findByName(String name);
}
