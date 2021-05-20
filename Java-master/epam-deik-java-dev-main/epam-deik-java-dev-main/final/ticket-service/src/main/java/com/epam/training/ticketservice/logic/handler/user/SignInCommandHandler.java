package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInCommandHandler {

    private final SignInCommand signInCommand;

    public SignInCommandHandler(SignInCommand signInCommand) {
        this.signInCommand = signInCommand;
    }

    @ShellMethod(value = "Sign in as not administrator", key = "sign in")
    public String signInNoAdminUser(String username, String password) {
        String result = signInCommand.operate(username, password);
        if (result.equals("sign")) {
            return "You already signed in";
        } else if (result.equals("ok")) {
            return "You successfully signed in with " + username;
        } else {
            return "Login failed due to incorrect credentials";
        }
    }
}
