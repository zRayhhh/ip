package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.ValidationTools;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;

/**
 * Defines how a user command "mark ..." should be validated and executed.
 */
public class MarkCommand extends Command {
    private final LiveTaskList tasks;

    /**
     * Injects the LiveTaskList to be mutated.
     * @param tasks The list.
     */
    public MarkCommand(LiveTaskList tasks) {
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
        assert args.size() == 1 : "Argument list should have 1 element";
        if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
            throw new InvalidCommandArgumentsException("Expected integer value");
        }
    }

    /**
     * Marks the Task at the matching LiveTaskList index (starts from 0) to the user-provided index (starts from 1).
     *
     * @param args A list containing the index of the Task to be marked complete.
     * @return Record holding the operation result message as a String and the update to the process status.
     * @throws InvalidCommandArgumentsException If index is out of bounds.
     */
    @Override
    protected CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        assert args.size() == 1 : "Argument list should have 1 element";
        int index = Integer.parseInt(args.get(0)) - 1; // user inputs index starting from 1
        if (index >= tasks.getNumTasks() || index < 0) {
            throw new InvalidCommandArgumentsException("~ Invalid index entered. "
                    + "Raven, the available indexes are 1 to " + tasks.getNumTasks());
        }
        String resultMsg = tasks.mark(index);
        return new CommandResult(resultMsg, AyreStatus.CONTINUE);
    }
}
