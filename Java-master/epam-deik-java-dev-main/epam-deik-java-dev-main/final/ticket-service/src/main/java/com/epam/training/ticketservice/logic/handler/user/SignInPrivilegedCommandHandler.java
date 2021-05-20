package com.epam.training.ticketservice.logic.handler.user;

import com.epam.training.ticketservice.logic.command.user.SignInPrivilegedCommand;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class SignInPrivilegedCommandHandler {

    private final SignInPrivilegedCommand signInPrivilegedCommand;

    public SignInPrivilegedCommandHandler(SignInPrivilegedCommand signInPrivilegedCommand) {
        this.signInPrivilegedCommand = signInPrivilegedCommand;
    }

    @ShellMethod(value = "Sign in as administrator", key = "sign in privileged")
    public String signInUser(String username, String password) {
        String result = signInPrivilegedCommand.operate(username, password);
        if ("ok".equals(result)) {
            return "You successfully signed in as administrator";
        } else if ("credentials".equals(result)) {
            return "Login failed due to incorrect credentials";
        } else {
            return "You already signed in";
        }
    }
}
