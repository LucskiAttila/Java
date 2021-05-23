package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListMoviesCommand {

    private final MovieRepository movieRepository;

    public ListMoviesCommand(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> operate() {
        return movieRepository.findAll();
    }
}
