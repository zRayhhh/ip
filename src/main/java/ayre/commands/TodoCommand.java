package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.tasks.Todo;

public class TodoCommand extends Command {
    private LiveTaskList tasks;

    public TodoCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Does nothing by default as the name does not need to be validated.
     *
     * @param args Expected to be a List with a single element that is the name.
     */
    protected void validate(List<String> args) throws InvalidCommandArgumentsException {
    }

    @Override
    protected CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        String resultMsg = tasks.add(new Todo(args.get(0)));
        return new CommandResult(resultMsg, AyreStatus.CONTINUE);
    }
}