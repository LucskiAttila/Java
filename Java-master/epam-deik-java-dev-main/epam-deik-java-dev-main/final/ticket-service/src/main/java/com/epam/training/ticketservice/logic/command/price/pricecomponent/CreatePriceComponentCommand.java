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

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    protected final String emptyString = "";

    private PriceComponent priceComponent;

    private String permissionError;
    private String badInteger;

    private final PriceComponentRepository priceComponentRepository;
    private final UserRepository userRepository;

    public CreatePriceComponentCommand(PriceComponentRepository priceComponentRepository,
                                       UserRepository userRepository) {
        this.priceComponentRepository = priceComponentRepository;
        this.userRepository = userRepository;
    }

    public String operate(String name, String price) {
        if (hasPermission()) {
            if (isConvert(price)) {
                if (isValid(name)) {
                    int priceFormatInt = convertToInt(price);
                    save(name, priceFormatInt);
                    return "ok";
                } else {
                    return "exist";
                }
            } else {
                return badInteger;
            }
        } else {
            return permissionError;
        }
    }

    private void save(String name, int priceFormatInt) {
        priceComponentRepository.save(new PriceComponent(name, priceFormatInt));
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
            } else {
                permissionError = "admin";
                return false;
            }
        } else {
            permissionError = "sign";
            return false;
        }
    }

    private boolean isConvert(String basePrice) {
        badInteger = validConvertToInt(basePrice);
        return emptyString.equals(badInteger);
    }

    private String validConvertToInt(String numberStr) {
        int index = 0;
        if (numberStr.charAt(0) == '-') {
            index = 1;
        }
        for (int i = index; i < numberStr.length(); i++) {
            if (!digits.contains(numberStr.charAt(i))) {
                return String.valueOf(numberStr.charAt(i));
            }
        }
        return emptyString;
    }

    private int convertToInt(String price) {
        return Integer.parseInt(price);
    }
}
