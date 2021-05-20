package com.epam.training.ticketservice.database.repository;

import com.epam.training.ticketservice.database.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
    Movie findByTitle(String title);
}
