package com.epam.training.ticketservice.logic.handler.price.priceomponent.attach;


import com.epam.training.ticketservice.logic.command.screening.AttachPriceComponentScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

    @ShellComponent
    public class AttachPriceComponentScreeningCommandHandler {

        @ShellMethod(value = "attach valid price component to a valid screening", key = "attach price component to screening")
        public String AttachPriceComponentScreening(String name, String title, String roomName, String startsDateAndTime) {
            AttachPriceComponentScreeningCommand command = new AttachPriceComponentScreeningCommand(name, title, roomName, startsDateAndTime);
            return command.execute();
        }
    }

