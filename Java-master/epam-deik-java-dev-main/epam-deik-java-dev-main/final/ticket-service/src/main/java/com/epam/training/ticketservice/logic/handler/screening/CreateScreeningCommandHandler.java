package com.epam.training.ticketservice.logic.handler.screening;

import com.epam.training.ticketservice.logic.command.screening.CreateScreeningCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.StringUtils;

@ShellComponent
public class CreateScreeningCommandHandler {

    private CreateScreeningCommand createScreeningCommand;

    public CreateScreeningCommandHandler(CreateScreeningCommand createScreeningCommand) {
        this.createScreeningCommand = createScreeningCommand;
    }

    @ShellMethod(value = "Create room with its name, and the row and column number of charis", key = "create screening")
    public String createScreening(String title, String roomName, String startsDateTime) {
        String result = createScreeningCommand.operate(title, roomName, startsDateTime);
        switch (result) {
            case "ok":
                return StringUtils.capitalize(title) + " " + StringUtils.capitalize(roomName)
                        + " " + startsDateTime  + " screening is successfully created";
            case "exist":
                return StringUtils.capitalize(title) + " " + StringUtils.capitalize(roomName)
                        + " " + startsDateTime  + " screening is already exists";
            case "sign":
                return "You aren't signed in";
            case "admin":
                return "You don't have permission";
            case "format":
                return "You use invalid dateformat";
            case "overlap":
                return "There is an overlapping screening";
            case "breaking":
                return "This would start in the break period after another screening in this room";
            case "invalid":
                return "You add invalid date: " + startsDateTime;
            default:
                return "You add invalid properties: " + result;
        }
    }
}
