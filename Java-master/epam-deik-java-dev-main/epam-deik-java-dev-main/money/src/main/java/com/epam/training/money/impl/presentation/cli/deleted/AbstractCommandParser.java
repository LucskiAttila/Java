package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.Command;
import com.epam.training.money.impl.presentation.cli.command.exception.UnknownCommandException;

public abstract class AbstractCommandParser implements CommandParser{

    private CommandParser successor;

    public AbstractCommandParser() {
        successor = null;
    }

    @Override
    public Command createCommand(String commandLine) {
        if (canCreateCommandFor(commandLine)) {
            return doCreateCommand(commandLine);
        }
        if (successor != null) {
            return successor.createCommand(commandLine);
        }
        throw new UnknownCommandException();
    }

    @Override
    public void setSuccessor(CommandParser commandParser) {
        if (successor == null) {
            successor = commandParser;
        } else {
            successor.setSuccessor(commandParser);
        }
    }

    protected abstract Command doCreateCommand(String commandLine);

    public abstract boolean canCreateCommandFor(String commandLine);
}
