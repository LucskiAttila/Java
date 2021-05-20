package com.epam.training.money.impl.presentation.cli;

import org.springframework.shell.jline.PromptProvider;
import org.jline.utils.AttributedString;
import org.springframework.stereotype.Component;

@Component
public class WebshopPromptProvider implements PromptProvider{
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("Webshop# ");
    }
}
