package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.AttachPriceComponentScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class AttachPriceComponentScreeningCommandHandler {

    private final AttachPriceComponentScreeningCommand attachPriceComponentScreeningCommand;

    public AttachPriceComponentScreeningCommandHandler(AttachPriceComponentScreeningCommand
                                                               attachPriceComponentScreeningCommand) {
        this.attachPriceComponentScreeningCommand = attachPriceComponentScreeningCommand;
    }

    @ShellMethod(value = "attach valid price component to a valid screening",
            key = "attach price component to screening")
    public String attachPriceComponentScreening(String name, String title, String roomName, String startsDateTime) {
        String result = attachPriceComponentScreeningCommand.operate(name, title, roomName, startsDateTime);
        switch (result) {
            case "movie ":
                return StringUtils.capitalize(title) + "movie doesn't exists";
            case "room ":
                return StringUtils.capitalize(roomName) + "room doesn't exists";
            case "component ":
                return StringUtils.capitalize(name) + "price component doesn't exists";
            case "movie room ":
                return StringUtils.capitalize(title) + "movie, " + roomName + "room doesn't exists";
            case "room component ":
                return StringUtils.capitalize(roomName) + "room, " + name + "price component doesn't exists";
            case "movie component ":
                return StringUtils.capitalize(title) + "movie, " + name + "price component doesn't exists";
            case "movie room component ":
                return StringUtils.capitalize(title) + "movie, " + roomName + "room, " + name
                        + "price component doesn't exists";
            case "exist":
                return StringUtils.capitalize(title) + ", " + roomName + ", " + startsDateTime
                        + "screening doesn't exists";
            case "format":
                return "You use invalid dateformat";
            case "invalid":
                return "You add invalid date: " + startsDateTime;
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "more":
                return StringUtils.capitalize(name) + " component is already attached to " + title + " "
                        + roomName + " " + startsDateTime;
            case "okDuplicate":
                return StringUtils.capitalize(name) + " component is successfully attached again to "
                        + title + " " + roomName + " " + startsDateTime;
            default:
                return StringUtils.capitalize(name) + " component is successfully attached to "
                        + title + " " + roomName + " " + startsDateTime;
        }
    }
}
