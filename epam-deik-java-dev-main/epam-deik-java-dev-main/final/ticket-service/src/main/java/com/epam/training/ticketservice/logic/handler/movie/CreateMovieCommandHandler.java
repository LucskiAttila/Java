package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.CreateMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreateMovieCommandHandler {

    @ShellMethod(value = "Create movie with , genre and duration in minutes", key = "create movie")
    public String createMovie(String title, String genre, String durationInMinutes) {
        CreateMovieCommand command = new CreateMovieCommand(title, genre, durationInMinutes);
        return command.execute();
    }
}
