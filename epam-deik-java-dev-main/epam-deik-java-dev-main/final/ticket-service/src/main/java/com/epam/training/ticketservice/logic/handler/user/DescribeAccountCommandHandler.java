package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.DescribeAccountCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class DescribeAccountCommandHandler {
    @ShellMethod(value = "Get information of your account", key = "describe account")
    public String describeUser() {
        DescribeAccountCommand command = new DescribeAccountCommand();
        return command.execute();
    }
}
