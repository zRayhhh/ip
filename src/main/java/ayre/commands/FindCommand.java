package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.enums.AyreStatus;

/**
 * Defines how a user command "find ..." should be validated and executed.
 */
public class FindCommand extends Command {
    private final LiveTaskList tasks;

    /**
     * Injects the LiveTaskList to be mutated.
     * @param tasks The list.
     */
    public FindCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Does nothing by default as the name does not need to be validated.
     *
     * @param args Expected to be a List with a single element that is the name.
     */
    @Override
    public void validate(List<String> args) {
        assert args.size() == 1 : "Argument list should have 1 element";
    }

    /**
     * Finds all tasks that contains the user-inputted name as a substring.
     *
     * @param args A list containing the substring to match for.
     * @return Record holding the operation result message as a String and the update to the process status.
     */
    @Override
    public CommandResult execute(List<String> args) {
        assert args.size() == 1 : "Argument list should have 1 element";
        return new CommandResult(tasks.findTasks(args.get(0)), AyreStatus.CONTINUE);
    }
}

