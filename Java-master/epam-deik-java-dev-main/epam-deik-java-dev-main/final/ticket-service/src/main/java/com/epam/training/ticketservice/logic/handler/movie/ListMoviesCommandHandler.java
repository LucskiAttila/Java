package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.database.entity.Movie;
import com.epam.training.ticketservice.logic.command.movie.ListMoviesCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

import java.util.List;

@ShellComponent
public class ListMoviesCommandHandler {

    private final ListMoviesCommand listMoviesCommand;

    public ListMoviesCommandHandler(ListMoviesCommand listMoviesCommand) {
        this.listMoviesCommand = listMoviesCommand;
    }

    @ShellMethod(value = "List movies with properties", key = "list movies")
    public String listMovies() {
        List<Movie> movies = listMoviesCommand.operate();
        if(movies.isEmpty()) {
            return "There are no movies at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (Movie movie : movies) {
                if (movie == movies.get(movies.size() - 1)) {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append(" minutes)");
                } else {
                    result.append(StringUtils.capitalize(movie.getTitle())).append(" (").append(movie.getGenre()).append(", ").append(movie.getDurationInMinutes()).append(" minutes)\n");
                }
            }
            return result.toString();
        }
    }
}
