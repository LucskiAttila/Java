package com.epam.training.ticketservice.logic.handler.movie;

import com.epam.training.ticketservice.logic.command.movie.AttachPriceComponentMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@ShellComponent
public class AttachPriceComponentMovieCommandHandler {

    private final AttachPriceComponentMovieCommand attachPriceComponentMovieCommand;

    public AttachPriceComponentMovieCommandHandler(AttachPriceComponentMovieCommand attachPriceComponentMovieCommand) {
        this.attachPriceComponentMovieCommand = attachPriceComponentMovieCommand;
    }

    @ShellMethod(value = "attach valid price component to a valid movie", key = "attach price component to movie")
    @Transactional
    public String attachPriceComponentMovie(String name, String title) {
        String result = attachPriceComponentMovieCommand.operate(name, title);
        switch (result) {
            case "first":
                return StringUtils.capitalize(title) + " movie doesn't exists";
            case "second":
                return StringUtils.capitalize(name) + " price component doesn't exists";
            case "all":
                return StringUtils.capitalize(title) + " movie, " + name + " price component doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "more":
                return StringUtils.capitalize(name) + " component is already attached to " + title;
            case "okDuplicate":
                return StringUtils.capitalize(name) + " component is successfully attached again to " + title;
            default:
                return StringUtils.capitalize(name) + " component is successfully attached to " + title;
        }
    }
}
