package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.DeleteScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class DeleteScreeningCommandHandler {

    private final DeleteScreeningCommand deleteScreeningCommand;

    public DeleteScreeningCommandHandler(DeleteScreeningCommand deleteScreeningCommand) {
        this.deleteScreeningCommand = deleteScreeningCommand;
    }

    @ShellMethod(value = "Delete screening with the title of the movie, and the name of the room and the start time and date of the screening", key = "delete screening")
    public String deleteScreening(String title, String roomName, String startsDateTime) {
        String result = deleteScreeningCommand.operate(title, roomName, startsDateTime);
        switch (result) {
            case "ok":
                return StringUtils.capitalize(title) + " " +StringUtils.capitalize(roomName) + " " + startsDateTime  + " screening is successfully deleted";
            case "exist":
                return StringUtils.capitalize(title) + " " +StringUtils.capitalize(roomName) + " " + startsDateTime  + " screening doesn't exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "format":
                return "You use invalid dateformat";
            default:
                return "You add invalid properties: " + result;
        }
    }
}
