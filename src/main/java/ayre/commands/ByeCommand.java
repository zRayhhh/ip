package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

public class ByeCommand extends Command {
    private LiveTaskList tasks;

    public ByeCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Does nothing by default as there is nothing to validate.
     *
     * @param args Expected to be an empty List.
     */
    public void validate(List<String> args) throws InvalidCommandArgumentsException {
    }

    @Override
    public CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        return new CommandResult("~ Terminating connection. See you again, Raven.\n", AyreStatus.TERMINATE);
    }
}
