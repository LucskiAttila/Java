package com.epam.training.ticketservice.logic.command.user;

import com.epam.training.ticketservice.database.entity.User;


import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DescribeAccountCommand {

    private final UserRepository userRepository;

    @Value("${CURRENCY}")
    private String currency;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private User user;

    public DescribeAccountCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<Object> operate() {
        List<Object> result = new ArrayList<>();
        if (isUserSignedIn()) {
            if (user.getIsAdmin()) {
                result.add("admin");
                result.add(user.getUserName());
                return result;
            } else {
                result.add("user");
                result.add(user.getUserName());
                result.add(user.getBook());
                result.add(currency);
                return result;
            }
        } else {
            result.add("sign");
            return result;
        }
    }

    private boolean isUserSignedIn() {
        user = userRepository.findByIsSigned(true);
        return user != null;
    }
}
