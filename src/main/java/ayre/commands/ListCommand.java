package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

public class ListCommand extends Command {
    private LiveTaskList tasks;

    public ListCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Does nothing by default as there is nothing to validate.
     *
     * @param args Expected to be an empty List.
     */
    @Override
    protected void validate(List<String> args) throws InvalidCommandArgumentsException {
    }

    @Override
    public CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        return new CommandResult(tasks.toString(), AyreStatus.CONTINUE);
    }
}

