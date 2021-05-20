package com.epam.training.ticketservice.logic.command.price.pricecomponent;

import com.epam.training.ticketservice.database.entity.PriceComponent;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.PriceComponentRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePriceComponentCommand {

    @Value("${DIGITS}")
    List<Character> digits;

    protected final String emptyString = "";

    private PriceComponent priceComponent;

    private String permission_error;
    private String bad_integer;

    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public CreatePriceComponentCommand(PriceComponentRepository priceComponentRepository, UserRepository userRepository) {
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String price) {
        if(hasPermission()) {
            if (isConvert(price)) {
                if(isValid(name)) {
                    int price_int = convertToInt(price);
                    save(name, price_int);
                    return "ok";
                } else {
                    return "exist";
                }
            } else {
                return bad_integer;
            }
        } else {
            return permission_error;
        }
    }

    private void save(String name, int price_int) {
        priceComponentRepository.save(new PriceComponent(name, price_int));
    }

    private boolean isValid(String name) {
        priceComponent = priceComponentRepository.findByName(name);
        return priceComponent == null;
    }

    private boolean hasPermission() {
        User user = userRepository.findByIsSigned(true);
        if (user != null) {
            if (user.getIsAdmin()) {
                return true;
            }
            else {
                permission_error = "admin";
                return false;
            }
        }
        else {
            permission_error = "sign";
            return false;
        }
    }

    private boolean isConvert(String base_price) {
        bad_integer = validConvertToInt(base_price);
        return emptyString.equals(bad_integer);
    }

    private String validConvertToInt(String number_str) {
        int index = 0;
        if (number_str.charAt(0) == '-') {
            index = 1;
        }
        for (int i = index; i < number_str.length(); i++) {
            if (!digits.contains(number_str.charAt(i))) {
                return String.valueOf(number_str.charAt(i));
            }
        }
        return emptyString;
    }

    private int convertToInt(String price) {
        return Integer.parseInt(price);
    }
}
