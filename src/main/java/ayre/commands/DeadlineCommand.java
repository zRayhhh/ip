package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.ValidationTools;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.tasks.Deadline;

public class DeadlineCommand extends Command {
    private LiveTaskList tasks;

    public DeadlineCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks whether the date argument is a String that matches the ISO_LOCAL_DATE format.
     * The matching is strict, i.e. impossible dates like 13/13 are invalid.
     * Name does not need to be validated.
     *
     * @param args Expected to be a List with a name and the date in that order.
     * @throws InvalidCommandArgumentsException If argument is not a valid ISO_LOCAL_DATE.
     */
    protected void validate(List<String> args) throws InvalidCommandArgumentsException {
        if (ValidationTools.isInvalidIsoDate(args.get(1))) {
            throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
        }
    }

    @Override
    protected CommandResult execute(List<String> args) throws InvalidCommandArgumentsException {
        String resultMsg = tasks.add(new Deadline(args.get(0), args.get(1)));
        return new CommandResult(resultMsg, AyreStatus.CONTINUE);
    }
}
