package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.InvalidCommandException;

/**
 * Defines the structure of subclasses that handle how user commands should be validated and executed.
 */
public abstract class Command {
    /**
     * Validates, executes, returns the result.
     * This setup ensures that validation and execution are always bundled together.
     *
     * @param args List of arguments to be passed to validate and execute.
     * @return Record holding the operation result message as a String and the update to the process status.
     * @throws InvalidCommandException If invalid arguments are found in validation or execution.
     */
    public final CommandResult doCommand(List<String> args) throws InvalidCommandException {
        this.validate(args);
        return this.execute(args);
    }

    /**
     * Verifies the correctness of the list of arguments passed in.
     *
     * @param args A list of arguments.
     * @throws InvalidCommandArgumentsException If arguments do not hold up to expected correctness.
     */
    public abstract void validate(List<String> args) throws InvalidCommandException;

    /**
     * Executes the command by running the relevant method on the TaskList through various means.
     *
     * @param args A list of arguments.
     * @return Record holding the operation result message as a String and the update to the process status.
     * @throws InvalidCommandArgumentsException If arguments passed in may cause runtime errors.
     */
    protected abstract CommandResult execute(List<String> args) throws InvalidCommandException;

    /**
     * Ensures the index provided for relevant Commands are within the accessible range.
     * Package private for the relevant Commands to make use of this method.
     *
     * @param args A list of arguments.
     * @param tasks A list of tasks.
     * @return The valid index provided in the arguments.
     * @throws InvalidCommandException If index is outside range for task list.
     */
    int validateIndex(List<String> args, LiveTaskList tasks) throws InvalidCommandException {
        assert args.size() == 1 : "Argument list should have 1 element";
        int index = Integer.parseInt(args.get(0)) - 1; // user inputs index starting from 1
        if (tasks.getNumTasks() == 0) {
            throw new InvalidCommandException("~ Raven, the mission log is currently empty. "
                    + "Try adding a mission first.");
        }
        if (index >= tasks.getNumTasks() || index < 0) {
            throw new InvalidCommandArgumentsException("~ Invalid index entered. "
                    + "Raven, the available indexes are 1 to " + tasks.getNumTasks());
        }
        return index;
    }
}
