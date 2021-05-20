package com.epam.training.ticketservice.logic.handler.price.priceomponent;

import com.epam.training.ticketservice.logic.command.price.pricecomponent.CreatePriceComponentCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class CreatePriceComponentCommandHandler {

    private final CreatePriceComponentCommand createPriceComponentCommand;

    public CreatePriceComponentCommandHandler(CreatePriceComponentCommand createPriceComponentCommand) {
        this.createPriceComponentCommand = createPriceComponentCommand;
    }

    @ShellMethod(value = "create price component with name and price", key = "create price component")
    public String UpdateBasePrice(String name, String price) {
        String result = createPriceComponentCommand.operate(name, price);
        switch (result) {
            case "ok":
                return StringUtils.capitalize(name) + " price component is successfully created";
            case "exist":
                return StringUtils.capitalize(name) + " price component is already exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            default:
                return "You add invalid integer " + result + " in " + price;
        }
    }
}
