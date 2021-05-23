package com.epam.training.ticketservice.logic.handler.price.baseprice;

import com.epam.training.ticketservice.logic.command.price.baseprice.UpdateBasePriceCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class UpdateBasePriceCommandHandler {

    private final UpdateBasePriceCommand updateBasePriceCommand;

    public UpdateBasePriceCommandHandler(UpdateBasePriceCommand updateBasePriceCommand) {
        this.updateBasePriceCommand = updateBasePriceCommand;
    }

    @ShellMethod(value = "Update base price to given price", key = "update base price")
    public String updateBasePrice(String basePrice) {
        String result = updateBasePriceCommand.operate(basePrice);
        switch (result) {
            case "ok":
                return "Base price is successfully set to " + basePrice;
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "same":
                return "You add same base price " + basePrice;
            default:
                return "You add invalid integer " + result;
        }
    }
}
