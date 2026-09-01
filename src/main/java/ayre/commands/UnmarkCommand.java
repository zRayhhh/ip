package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.ValidationTools;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

public class UnmarkCommand extends Command {
    private LiveTaskList tasks;

    public UnmarkCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks whether the argument is a numeric String.
     *
     * @param args Expected to be a List with a single element that is the argument for the Command.
     * @throws InvalidCommandArgumentsException If argument is not a numeric String.
     */
    @Override
    public void validate(List<String> args) throws InvalidCommandArgumentsException {
        if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
            throw new InvalidCommandArgumentsException("Expected integer value");
        }
    }

    @Override
    public CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
        if (index >= tasks.getNumTasks() || index < 0) {
            throw new InvalidCommandArgumentsException("~ Invalid index entered. " +
                    "Raven, the available indexes are 1 to " + tasks.getNumTasks());
        }
        String resultMsg = tasks.unmark(index);
        return new CommandResult(resultMsg, AyreStatus.CONTINUE);
    }
}