package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.Basket;
import com.epam.training.money.impl.command.Command;
import com.epam.training.money.impl.command.OrderCommand;

public class OrderCommandParser extends AbstractCommandParser{
    private final static String ORDER_COMMAND = "order basket";

    private Basket basketToOrder;

    public OrderCommandParser(Basket basketToOrder) {
        this.basketToOrder = basketToOrder;
    }

    @Override
    protected boolean canCreateCommandFor(String command) {
        return ORDER_COMMAND.equals(command);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        return new OrderCommand(basketToOrder);
    }
}
