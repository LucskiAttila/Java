package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.AddProductCommand;
import com.epam.training.money.impl.command.Command;

public class AddProductCommandParser extends AbstractCommandParser{

    private static final String ADD_PRODUCT_PATTERN = "add proudct (.+)";

    private final ProductRepository repository;
    private final Basket basket;

    public AddProductCommandParser(Basket basket, ProductRepository repository) {
        this.basket = basket;
        this.repository = repository;
    }

    @Override
    public boolean canCreateCommandFor(String commandLine) {
        return commandLine.matches(ADD_PRODUCT_PATTERN);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        Matcher matcher = Pattern.compile(ADD_PRODUCT_PATTERN).matcher(commandLine);
        if (matcher.matches()) {
            String productName = matcher.group(1);
            return new AddProductCommand(productName, basket, repository);
        }
        throw new InvalidParameterException("Unknown command line was used");
    }
}
