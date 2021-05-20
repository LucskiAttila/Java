package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.Command;

public interface CommandParser {
    boolean canCreateCommandFor(String commandLine);
    Command createCommand(String commandLine);
    void setSuccessor(CommandParser commandParser);
}
