package com.epam.training.ticketservice.logic.command.price.baseprice;

import com.epam.training.ticketservice.database.entity.BasePrice;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.BasePriceRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateBasePriceCommand {

    @Value("${DIGITS}")
    List<Character> digits;

    public void setDigits(List<Character> digits) {
        this.digits = digits;
    }

    protected final String emptyString = "";

    private String permissionError;
    private String badInteger;

    private final BasePriceRepository basePriceRepository;
    private final UserRepository userRepository;

    public UpdateBasePriceCommand(BasePriceRepository basePriceRepository, UserRepository userRepository) {
        this.basePriceRepository = basePriceRepository;
        this.userRepository = userRepository;
    }

    public String operate(String basePriceValue) {
        if (hasPermission()) {
            if (isConvert(basePriceValue)) {
                int basePriceFormatInt = convertToInt(basePriceValue);
                BasePrice basePrice = basePriceRepository.findAll().get(0);
                if (basePriceFormatInt != basePrice.getBasePriceValue()) {
                    basePriceRepository.delete(basePrice);
                    basePriceRepository.save(new BasePrice(basePriceFormatInt));
                    return "ok";
                } else {
                    return "same";
                }
            } else {
                return badInteger;
            }
        } else {
            return permissionError;
        }
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

    private boolean isConvert(String basePriceValue) {
        badInteger = validConvertToInt(basePriceValue);
        return emptyString.equals(badInteger);
    }

    private String validConvertToInt(String numberFormatStr) {
        for (int i = 0; i < numberFormatStr.length(); i++) {
            if (!digits.contains(numberFormatStr.charAt(i))) {
                return String.valueOf(numberFormatStr.charAt(i));
            }
        }
        return emptyString;
    }

    private int convertToInt(String price) {
        return Integer.parseInt(price);
    }
}
