package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SignUpCommand {

    private final UserRepository userRepository;

    public SignUpCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String operate(String username, String password) {
        if (isValid(username)) {
            userRepository.save(new User(username, password, false, false, new ArrayList<Book>()));
            return "ok";
        } else {
            return "exist";
        }
    }

    private boolean isValid(String username) {
        return userRepository.findByUserName(username) == null;
    }
}
