package com.epam.training.ticketservice.logic.command.movie;

import com.epam.training.ticketservice.database.entity.Movie;

import java.util.List;

public class ListMoviesCommand extends AnyAbstractMovieCommand {

    @Override
    public String operate() {
        List<Movie> movies = list();
        if (movies != null) {
            return listTrue();
        }
        else {
            return listFalse(false);
        }
    }
}
