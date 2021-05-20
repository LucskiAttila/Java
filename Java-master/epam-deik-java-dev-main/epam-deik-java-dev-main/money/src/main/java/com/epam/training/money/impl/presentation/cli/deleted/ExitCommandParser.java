package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.Command;

public class ExitCommandParser extends AbstractCommandParser{

    private static final String EXIT_COMMAND = "exit";

    private final CliInterpreter interpreterToStop;

    public ExitCommandParser(CliInterpreter interpreterToStop) {
        this.interpreterToStop = interpreterToStop;
    }

    @Override
    public boolean canCreateCommandFor(String commandLine) {
        return EXIT_COMMAND.equals(commandLine);
    }

    @Override
    protected Command doCreateCommand(String commandLine) {
        return new ExitCommand(interpreterToStop);
    }
}
