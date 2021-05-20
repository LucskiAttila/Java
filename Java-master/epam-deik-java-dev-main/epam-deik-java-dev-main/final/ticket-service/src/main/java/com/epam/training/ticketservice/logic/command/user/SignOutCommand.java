package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignOutCommand {

    private final UserRepository userRepository;

    private User user;

    public SignOutCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String operate() {
        if (isUserSingedIn()) {
            List<Book> books = user.getBook();
            userRepository.delete(user);
            if (user.getIsAdmin()) {
                userRepository.save(new User(user.getUserName(), user.getPassword(), true, false, books));
            } else {
                userRepository.save(new User(user.getUserName(), user.getPassword(), false, false, books));
            }
            return "ok";
        } else {
            return "sign";
        }
    }

    private boolean isUserSingedIn() {
        user = userRepository.findByIsSigned(true);
        return user != null;
    }
}
