package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInCommandHandler {

    @ShellMethod(value = "Sign in as not administrator", key = "sign in")
    public String signInNoAdminUser(String username, String password) {
        SignInCommand command = new SignInCommand(username, password);
        return command.execute();
    }
}
