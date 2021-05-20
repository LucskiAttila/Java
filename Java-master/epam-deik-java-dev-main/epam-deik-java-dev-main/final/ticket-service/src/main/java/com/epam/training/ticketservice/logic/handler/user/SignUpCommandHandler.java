package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignUpCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignUpCommandHandler {

    private final SignUpCommand signUpCommand;

    public SignUpCommandHandler(SignUpCommand signUpCommand) {
        this.signUpCommand = signUpCommand;
    }

    @ShellMethod(value = "After sign up you can sign in", key = "sign up")
    public String signUpUser(String username, String password) {
        String result = signUpCommand.operate(username, password);
        if ("ok".equals(result)) {
            return "You successfully signed up with " + username;
        } else {
            return "You already signed up";
        }
    }
}
