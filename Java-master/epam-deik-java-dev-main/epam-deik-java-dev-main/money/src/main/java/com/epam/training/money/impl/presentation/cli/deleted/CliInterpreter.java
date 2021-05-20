package com.epam.training.money.impl.presentation.cli.deleted;

import com.epam.training.money.impl.command.Command;
import com.epam.training.money.impl.presentation.cli.command.exception.UnknownCommandException;

import java.io.BufferedReader;
import java.io.Writer;
import org.springFramework.stereotype.Service;
import org.springFramework.beans.factory.annotation.Autowired;

@Service
public class CliInterpreter {

    private Writer output;
    private BufferedReader input;
    private boolean shouldRun;

    private CommandParser commandParser;

    @Autowired
    public CliInterpreter(Reader input, Writer output) {
        this.output = output;
        this.input = new BufferedReader(input);
        commandParser = null;
    }

    @Autowired
    public void setCommandParser(CommandParser commandParser) {
        this.commandParser = commandParser;
    }

    public void run() throws IOException{
        validateCommandParserSet();
        shouldRun = true;
        while(shouldRun) {
            String commandLine = input.readLine();
            String result = "";
            try {
                Command commandToRun = commandParser.createCommand(commandLine);
                result = commandToRun.execute();
            } catch (UnknownCommandException e) {
                result = "Unknown command.";
            }
            output.write(result + System.LineSeperator());
            output.flush();
        }
    }

    public void validateCommandParserSet() {
        if (commandParser == null) {
            throw new IllegalStateException("Can not start the CLI Interpreter without a comand praser");
        }
    }

    public void stop() {
        shouldRun = false;
    }
}
