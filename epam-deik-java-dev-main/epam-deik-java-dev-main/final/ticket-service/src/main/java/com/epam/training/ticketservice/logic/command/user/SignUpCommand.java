package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import com.epam.training.ticketservice.logic.command.AdminText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class SignUpCommand extends AdminText {

    private final String username;
    private final String password;

    @Autowired
    UserRepository userRepository;

    public SignUpCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //@Override
    public String execute() {
        if (userRepository.findByUserNameAndPassword(username, password) == null) {
            userRepository.save(new User(username, password, false, false, new ArrayList<Book>()));
            return "You successfully signed up with: username : " + username + " password: " + password;
        } else {
            return "You already signed up";
        }
    }

    @Override
    protected String modifiedProperties() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }
}
