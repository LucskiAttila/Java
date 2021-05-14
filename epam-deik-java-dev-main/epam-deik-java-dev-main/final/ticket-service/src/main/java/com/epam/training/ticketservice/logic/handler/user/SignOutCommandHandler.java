package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignOutCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignOutCommandHandler {

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOutUser() {
        SignOutCommand command = new SignOutCommand();
        return command.execute();
    }
}
