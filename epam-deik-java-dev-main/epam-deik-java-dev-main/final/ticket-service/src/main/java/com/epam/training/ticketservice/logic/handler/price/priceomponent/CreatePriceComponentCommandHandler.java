package com.epam.training.ticketservice.logic.handler.price.priceomponent;

import com.epam.training.ticketservice.logic.command.price.pricecomponent.CreatePriceComponentCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CreatePriceComponentCommandHandler {

    @ShellMethod(value = "create price component with name and price", key = "create price component")
    public String UpdateBasePrice(String name, String price) {
        CreatePriceComponentCommand command = new CreatePriceComponentCommand(name, price);
        return command.execute();
    }
}
