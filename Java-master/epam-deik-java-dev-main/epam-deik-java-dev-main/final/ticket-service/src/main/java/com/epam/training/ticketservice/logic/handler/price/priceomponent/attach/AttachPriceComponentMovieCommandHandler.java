package com.epam.training.ticketservice.logic.handler.price.priceomponent.attach;

import com.epam.training.ticketservice.logic.command.movie.AttachPriceComponentMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
    public class AttachPriceComponentMovieCommandHandler {

    private final AttachPriceComponentMovieCommand attachPriceComponentMovieCommand;

    public AttachPriceComponentMovieCommandHandler(AttachPriceComponentMovieCommand attachPriceComponentMovieCommand) {
        this.attachPriceComponentMovieCommand = attachPriceComponentMovieCommand;
    }

    @ShellMethod(value = "attach valid price component to a valid movie", key = "attach price component to movie")
    public String AttachPriceComponentMovie(String name, String title) {
        String result = attachPriceComponentMovieCommand.operate(name, title);
        switch (result) {
            case "movie":
                return StringUtils.capitalize(title) + " movie doesn't exists";
            case "component":
                return StringUtils.capitalize(name) + " price component doesn't exists";
            case "all":
                return StringUtils.capitalize(title) + " movie, " + name + " price component doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "more":
                return StringUtils.capitalize(name) + " component is already attached to " + title;
            case "ok_duplicate":
                return StringUtils.capitalize(name) + " component is successfully attached again to " + title;
            default:
                return StringUtils.capitalize(name) + " component is successfully attached to " + title;
        }
    }
}
