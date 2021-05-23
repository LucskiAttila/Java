package com.epam.training.ticketservice.logic.init;

import com.epam.training.ticketservice.database.entity.BasePrice;
import com.epam.training.ticketservice.database.entity.Book;
import com.epam.training.ticketservice.database.entity.User;
import com.epam.training.ticketservice.database.repository.BasePriceRepository;
import com.epam.training.ticketservice.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;


@Component
public class DataBaseInitializer {

    private final UserRepository userRepository;
    private final BasePriceRepository basePriceRepository;

    @Value("${ADMINISTRATOR_USERNAME}")
    private String username;

    @Value("${ADMINISTRATOR_PASSWORD}")
    private String password;

    @Value("${DEFAULT_BASE_PRICE}")
    private int basePrice;

    public DataBaseInitializer(UserRepository userRepository, BasePriceRepository basePriceRepository) {
        this.userRepository = userRepository;
        this.basePriceRepository = basePriceRepository;
    }

    @PostConstruct
    public void initDataBase() {
        User user = userRepository.findByIsAdmin(true);
        if (user != null) {
            userRepository.delete(user);
        }
        userRepository.save(new User(username, password, true, false, new ArrayList<Book>()));
        if (!basePriceRepository.findAll().isEmpty()) {
            basePriceRepository.deleteAll();
        }
        basePriceRepository.save(new BasePrice(basePrice));
    }
}
