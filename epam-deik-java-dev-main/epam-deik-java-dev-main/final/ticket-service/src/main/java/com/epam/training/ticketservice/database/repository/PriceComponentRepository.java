package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import org.springframework.data.repository.CrudRepository;

public interface PriceComponentRepository extends CrudRepository<PriceComponent, String>{
}
