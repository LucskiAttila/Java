package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.UpdateMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateMovieCommandHandler {

    @ShellMethod(value = "Updates movie with new genre and duration in minutes", key = "update movie")
    public String updateMovie(String title, String genre, String durationInMinutes) {
        UpdateMovieCommand command = new UpdateMovieCommand(title, genre, durationInMinutes);
        return command.execute();
    }
}