package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignInCommand  {

    private final UserRepository userRepository;

    private User user;

    public SignInCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String operate(String username, String password) {
        if (isNoOneSignedIn()) {
            if(isValid(username)) {
                List<Book> books = user.getBook();
                userRepository.delete(user);
                userRepository.save(new User(username, password, false, true, books));
                return "ok";
            } else {
                return "invalid";
            }
        } else {
            return "sign";
        }
    }

    private boolean isValid(String username) {
        user = userRepository.findByUserName(username);
        return user != null;
    }

    private boolean isNoOneSignedIn() {
        return userRepository.findByIsSigned(true) == null;
    }
}
