package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.logic.command.AdminText;
import org.springframework.beans.factory.annotation.Autowired;
import com.epam.training.ticketservice.database.repository.UserRepository;

import java.util.List;

public class SignOutCommand extends AdminText {

    @Autowired
    UserRepository userRepository;

    //@Override
    public String execute() {
        User user = userRepository.findSingedIn();
        String username = user.getUserName();
        String password = user.getPassword();
        Boolean isAdmin = user.getIsAdmin();
        List<Book> books = user.getBook();
        userRepository.delete(user);
        userRepository.save(username, password, isAdmin, false, books);
        return "You successfully signed out.";
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
