package com.epam.training.ticketservice.logic.promt;

import org.springframework.shell.jline.PromptProvider;
import org.jline.utils.AttributedString;
import org.springframework.stereotype.Component;

@Component
public class Provider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Ticket service>");
    }
}