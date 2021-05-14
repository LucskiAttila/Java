package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignUpCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignUpCommandHandler {

    @ShellMethod(value = "After sign up you can sign in", key = "sign up")
    public String signUpUser(String username, String password) {
        SignUpCommand command = new SignUpCommand(username, password);
        return command.execute();
    }
}
