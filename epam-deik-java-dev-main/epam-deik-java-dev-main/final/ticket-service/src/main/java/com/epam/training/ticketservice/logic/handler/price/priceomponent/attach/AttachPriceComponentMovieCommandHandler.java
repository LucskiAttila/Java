package com.epam.training.ticketservice.logic.handler.price.priceomponent.attach;


import com.epam.training.ticketservice.logic.command.movie.AttachPriceComponentMovieCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

    @ShellComponent
    public class AttachPriceComponentMovieCommandHandler {

        @ShellMethod(value = "attach valid price component to a valid movie", key = "attach price component to movie")
        public String AttachPriceComponentMovie(String name, String title) {
            AttachPriceComponentMovieCommand command = new AttachPriceComponentMovieCommand(name, title);
            return command.execute();
        }
    }
