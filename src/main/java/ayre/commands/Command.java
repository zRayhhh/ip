package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.exceptions.InvalidCommandArgumentsException;

public abstract class Command {
    public final CommandResult doCommand(List<String> args) throws InvalidCommandArgumentsException {
        this.validate(args);
        return this.execute(args);
    }

    protected abstract void validate(List<String> args) throws InvalidCommandArgumentsException;
    protected abstract CommandResult execute(List<String> args) throws InvalidCommandArgumentsException;
}
