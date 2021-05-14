package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.logic.command.AdminText;
import org.springframework.beans.factory.annotation.Autowired;
import com.epam.training.ticketservice.database.repository.UserRepository;

import java.util.List;

public class SignInCommand extends AdminText {

    private final String username;
    private final String password;

    @Autowired
    UserRepository userRepository;

    public SignInCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        if (userRepository.findSingedIn() == null) {
            User user = userRepository.findByUserNameAndPassword(username, password);
            if (user != null) {
                List<Book> books = user.getBook();
                userRepository.delete(user);
                userRepository.save(new User(username, password, true, true, books));
                return "You successfully signed in with: username : " + username + " password: " + password;
            } else {
                return "Login failed due to incorrect credentials";
            }
        } else {
            return "You already signed in";
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
