package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SignInPrivilegedCommand {

    private final UserRepository userRepository;

    private User user;

    public SignInPrivilegedCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String operate(String username, String password) {
        if (isNoOneSignedIn()) {
            getAdmin();
            if (user.getUserName().equals(username) && user.getPassword().equals(password)) {
                List<Book> book = user.getBook();
                userRepository.delete(user);
                userRepository.save(new User(username, password, true, true, book));
                return "ok";
            }
            else{
                return "credentials";
            }
        }
        else {
            return "sign";
        }
    }

    private void getAdmin() {
        user = userRepository.findByIsAdmin(true);
    }

    private boolean isNoOneSignedIn() {
        return userRepository.findByIsSigned(true) == null;
    }
}
