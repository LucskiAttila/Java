package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInPrivigeledCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInPrivigeledCommandHandler {

    @ShellMethod(value = "Sign in as administrator", key = "sign in privigeled")
    public String signInUser(String username, String password) {
        SignInPrivigeledCommand command = new SignInPrivigeledCommand(username, password);
        return command.execute();
    }
}
