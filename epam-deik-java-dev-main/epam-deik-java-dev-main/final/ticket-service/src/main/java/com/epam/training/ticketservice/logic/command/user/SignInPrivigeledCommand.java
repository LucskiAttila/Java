package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.logic.command.AdminText;
import org.springframework.beans.factory.annotation.Autowired;
import com.epam.training.ticketservice.database.repository.UserRepository;

import java.util.Collections;
import java.util.List;

public class SignInPrivigeledCommand extends AdminText {

    private final String username;
    private final String password;

    @Autowired
    UserRepository userRepository;
    @Autowired
    @Value("${ADMINISTRATOR_USERNAME}")
    String valid_username;
    @Autowired
    @Value("${ADMINISTRATOR_PASSWORD}")
    String valid_password;

    public SignInPrivigeledCommand(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        if (userRepository.findSingedIn().isEmpty()) {
            if (isValid()) {
                List<User> user = userRepository.findSingedUpAdminsistrator();
                if (user == null) {
                    userRepository.save(new User(username, password, true, true, Collections.<Book>emptyList()));
                }
                else {
                    userRepository.delete(user.get(0));
                    userRepository.save(new User(username, password, true, true, Collections.<Book>emptyList()));
                }
                return "You successfully signed in with: username : " + username + " password: " + password;
            }
            else{
                return "Login failed due to incorrect credentials";
            }
        }
        else {
            return "You already signed in";
        }
    }

    private boolean isValid() {
        return username.equals(valid_username) && password.equals(valid_password);
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
