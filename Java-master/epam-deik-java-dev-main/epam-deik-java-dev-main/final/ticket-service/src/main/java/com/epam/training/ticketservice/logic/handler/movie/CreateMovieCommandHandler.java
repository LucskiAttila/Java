package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.CreateMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class CreateMovieCommandHandler {

    private final CreateMovieCommand createMovieCommand;

    public CreateMovieCommandHandler(CreateMovieCommand createMovieCommand) {
        this.createMovieCommand = createMovieCommand;
    }

    @ShellMethod(value = "Create movie with , genre and duration in minutes", key = "create movie")
    public String createMovie(String title, String genre, String durationInMinutes) {
        String result = createMovieCommand.operate(title, genre, durationInMinutes);
        switch (result) {
            case "ok":
                return StringUtils.capitalize(title) + " movie is successfully created";
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
