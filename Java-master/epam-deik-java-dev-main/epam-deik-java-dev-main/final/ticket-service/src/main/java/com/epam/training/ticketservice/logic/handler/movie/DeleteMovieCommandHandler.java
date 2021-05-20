package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.DeleteMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class DeleteMovieCommandHandler {

    private final DeleteMovieCommand deleteMovieCommand;

    public DeleteMovieCommandHandler(DeleteMovieCommand deleteMovieCommand) {
        this.deleteMovieCommand = deleteMovieCommand;
    }

    @ShellMethod(value = "Deletes a movie by its title", key = "delete movie")
    public String deleteMovie(String title) {
        String result = deleteMovieCommand.operate(title);
        switch (result) {
            case "exist":
                return StringUtils.capitalize(title) + " movie doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            default:
                return StringUtils.capitalize(title) + " movie is successfully deleted";
        }
    }
}
