package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.BasePrice;
import org.springframework.data.repository.CrudRepository;

public interface BasePriceRepository extends CrudRepository <BasePrice, Integer>{
}
