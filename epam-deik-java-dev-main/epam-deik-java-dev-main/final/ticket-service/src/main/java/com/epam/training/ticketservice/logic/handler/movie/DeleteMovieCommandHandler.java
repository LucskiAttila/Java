package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.DeleteMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DeleteMovieCommandHandler {

    @ShellMethod(value = "Deletes a movie by its title", key = "delete movie")
    public String deleteMovie(String title) {
        DeleteMovieCommand command = new DeleteMovieCommand(title);
        return command.execute();
    }
}
