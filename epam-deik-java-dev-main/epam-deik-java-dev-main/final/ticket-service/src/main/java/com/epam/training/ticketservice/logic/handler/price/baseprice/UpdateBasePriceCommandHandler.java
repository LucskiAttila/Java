package com.epam.training.ticketservice.logic.handler.price.baseprice;

import com.epam.training.ticketservice.logic.command.price.baseprice.UpdateBasePriceCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateBasePriceCommandHandler {

    @ShellMethod(value = "Update base price to given price", key = "update base price")
    public String UpdateBasePrice(String base_price) {
        UpdateBasePriceCommand command = new UpdateBasePriceCommand(base_price);
        return command.execute();
    }
}
