package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository <Movie, String>{
    Movie findBytitle(String title);
}
