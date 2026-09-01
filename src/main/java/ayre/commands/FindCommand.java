package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

public class FindCommand extends Command {
    private LiveTaskList tasks;

    public FindCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Does nothing by default as the name does not need to be validated.
     *
     * @param args Expected to be a List with a single element that is the name.
     */
    @Override
    public void validate(List<String> args) throws InvalidCommandArgumentsException {
    }

    @Override
    public CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        return new CommandResult(tasks.findTasks(args.get(0)), AyreStatus.CONTINUE);
    }
}

