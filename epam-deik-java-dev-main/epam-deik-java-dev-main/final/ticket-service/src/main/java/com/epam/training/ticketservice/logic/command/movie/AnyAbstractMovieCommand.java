package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.database.repository.MovieRepository;
import com.epam.training.ticketservice.logic.command.AnyAbstract;

import java.util.List;

public abstract class AnyAbstractMovieCommand extends AnyAbstract {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    Movie movies;

    private String type = "movies";

    @Override
    public String getType() {
        return type;
    }

    @Override
    protected List<Movie> list() {
        return movieRepository.findAll();
    }

    @Override
    protected String getElements() {
        StringBuilder result = new StringBuilder();
        for (Movie movie : movies) {
            result.append(capitalize(movie.getTitle())).append("(").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append(" minutes)\n");
        }
        return result.toString();
    }
}
