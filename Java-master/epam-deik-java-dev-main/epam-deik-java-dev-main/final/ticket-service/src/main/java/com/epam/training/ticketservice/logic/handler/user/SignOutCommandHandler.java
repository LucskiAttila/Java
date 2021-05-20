package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignOutCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignOutCommandHandler {

    private final SignOutCommand signOutCommand;

    public SignOutCommandHandler(SignOutCommand signOutCommand) {
        this.signOutCommand = signOutCommand;
    }

    @ShellMethod(value = "Sign out", key = "sign out")
    public String signOutUser() {
        String result = signOutCommand.operate();
        if ("sign".equals(result)) {
            return "You aren't signed in";
        } else {
            return "You successfully signed out";
        }
    }
}
