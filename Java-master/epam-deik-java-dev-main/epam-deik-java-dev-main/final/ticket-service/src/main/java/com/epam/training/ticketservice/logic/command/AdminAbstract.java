package com.epam.training.ticketservice.logic.command;

import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public abstract class AdminAbstract extends AdminText implements Command{

    @Autowired
    UserRepository userRepository;

    //@Autowired
    @Value("${DIGITS}")
    List<Character> digits;

    @Override
    public String execute() {
        User user = (User) userRepository.findAll();
        if (user != null) {
            if (user.getIsAdmin()) {
                return operate();
            }
            else {
                return notAdmin();
            }
        }
        else {
            return notSigned();
        }
    }

    protected abstract String operate();

    protected abstract void delete();

    protected abstract void save();

    protected void update() {
        delete();
        save();
    }

    protected String validConvertToInt(String number_str) {
        for (int i = 0; i < number_str.length(); i++) {
            if (!digits.contains(number_str.charAt(i))) {
                return String.valueOf(number_str.charAt(i));
            }
        }
        return emptyString;
    }

    protected int convertToInt(String price) {
        return Integer.parseInt(price);
    }

    protected abstract boolean isValid();
}
