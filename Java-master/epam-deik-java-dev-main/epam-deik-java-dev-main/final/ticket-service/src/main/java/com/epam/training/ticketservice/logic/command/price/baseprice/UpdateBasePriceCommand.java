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

    protected final String emptyString = "";

    private String permission_error;
    private String bad_integer;

    private final BasePriceRepository basePriceRepository;
    private final UserRepository userRepository;

    public UpdateBasePriceCommand(BasePriceRepository basePriceRepository, UserRepository userRepository) {
        this.basePriceRepository = basePriceRepository;
        this.userRepository = userRepository;
    }

    public String operate(String base_price) {
        if(hasPermission()) {
            if (isConvert(base_price)) {
                int base_price_int = convertToInt(base_price);
                BasePrice basePrice = basePriceRepository.findAll().get(0);
                if (base_price_int != basePrice.getBase_price()) {
                    basePriceRepository.delete(basePrice);
                    basePriceRepository.save(new BasePrice(base_price_int));
                    return "ok";
                } else {
                    return "same";
                }
            } else {
                return bad_integer;
            }
        } else {
            return permission_error;
        }
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
        for (int i = 0; i < number_str.length(); i++) {
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
