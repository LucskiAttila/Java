package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.ListMoviesCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class ListMoviesCommandHandler {

    @ShellMethod(value = "List movies with properties", key = "list movies")
    public String listMovies() {
        ListMoviesCommand command = new ListMoviesCommand();
        return command.execute();
    }
}
