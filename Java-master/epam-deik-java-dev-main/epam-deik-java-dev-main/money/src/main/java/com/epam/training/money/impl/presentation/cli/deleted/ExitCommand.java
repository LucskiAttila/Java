package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.Command;

public class ExitCommand implements Command {

    private CliInterpreter interpreter;

    public ExitCommand(CliInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    @Override
    public String execute() {
        interpreter.stop();
        return "Exiting.";
    }
}
