package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.exceptions.InvalidCommandArgumentsException;

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
     * @throws InvalidCommandArgumentsException If invalid arguments are found in validation or execution.
     */
    public final CommandResult doCommand(List<String> args) throws InvalidCommandArgumentsException {
        this.validate(args);
        return this.execute(args);
    }

    /**
     * Verifies the correctness of the list of arguments passed in.
     *
     * @param args A list of arguments.
     * @throws InvalidCommandArgumentsException If arguments do not hold up to expected correctness.
     */
    public abstract void validate(List<String> args) throws InvalidCommandArgumentsException;

    /**
     * Executes the command by running the relevant method on the TaskList through various means.
     *
     * @param args A list of arguments.
     * @return Record holding the operation result message as a String and the update to the process status.
     * @throws InvalidCommandArgumentsException If arguments passed in may cause runtime errors.
     */
    protected abstract CommandResult execute(List<String> args) throws InvalidCommandArgumentsException;
}
