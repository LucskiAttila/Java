package com.epam.training.money.impl.presentation.cli.configuration;

import com.epam.training.money.impl.EmailConfirmationService;
import com.epam.training.money.impl.EmailConfirmationServiceAdapter;
import com.epam.training.money.impl.Observable;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmationConfiguration {

    @Bean()
    public EmailConfirmationService emailConfirmationService(Observable basket) {
        EmailConfirmationServiceAdapter emailConfirmationService= new EmailConfirmationServiceAdapter();
        basket.subscribe(emailConfirmationService);
        return emailConfirmationService;
    }
}
