package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.UpdateMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class UpdateMovieCommandHandler {

    private final UpdateMovieCommand updateMovieCommand;

    public UpdateMovieCommandHandler(UpdateMovieCommand updateMovieCommand) {
        this.updateMovieCommand = updateMovieCommand;
    }

    @ShellMethod(value = "Updates movie with new genre and duration in minutes", key = "update movie")
    public String updateMovie(String title, String genre, String durationInMinutes) {
        String result = updateMovieCommand.operate(title, genre, durationInMinutes);
        switch (result) {
            case "first":
                return "You successfully updated genre of " + title;
            case "second":
                return "You successfully updated duration in minutes of " + title;
            case "all":
                return "You add same properties";
            case "":
                return "You successfully updated all properties of " + title;
            case "exist":
                return StringUtils.capitalize(title) + " movie is already exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            default:
                return "You add invalid integer " + result + " in " + durationInMinutes;
        }
    }
}
