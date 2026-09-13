package ayre.commands;

import java.util.List;

import ayre.CommandResult;
import ayre.LiveTaskList;
import ayre.ValidationTools;
import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.tasks.Event;

/**
 * Defines how a user command "event ..." should be validated and executed.
 */
public class EventCommand extends Command {
    private final LiveTaskList tasks;

    /**
     * Injects the LiveTaskList to be mutated.
     * @param tasks The list.
     */
    public EventCommand(LiveTaskList tasks) {
        this.tasks = tasks;
    }

    /**
     * Checks whether the date argument is a String that matches the ISO_LOCAL_DATE format.
     * The matching is strict, i.e. impossible dates like 13/13 are invalid.
     * Name does not need to be validated.
     *
     * @param args Expected to be a List with a name, start date, and end date in that order.
     * @throws InvalidCommandArgumentsException If argument is not a valid ISO_LOCAL_DATE.
     */
    public void validate(List<String> args) throws InvalidCommandArgumentsException {
        assert args.size() == 3 : "Argument list should have 3 elements";
        if (ValidationTools.isInvalidIsoDate(args.get(1)) || ValidationTools.isInvalidIsoDate(args.get(2))) {
            throw new InvalidCommandArgumentsException("~ Raven, I need a date in the "
                    + "ISO_LOCAL_DATE yyyy-mm-dd format.");
        }
        if (ValidationTools.isInvalidDatePair(args.get(1), args.get(2))) {
            throw new InvalidCommandArgumentsException("~ Raven, the date from should occur before the date to. "
                    + "Did you mix them up?");
        }
    }

    /**
     * Adds a new Event to the LiveTaskList.
     *
     * @param args A list of arguments to construct a new Event with.
     * @return Record holding the operation result message as a String and the update to the process status.
     */
    @Override
    protected CommandResult execute(List<String> args) {
        assert args.size() == 3 : "Argument list should have 3 elements";
        String resultMsg = tasks.add(new Event(args.get(0), args.get(1), args.get(2)));
        return new CommandResult(resultMsg, AyreStatus.CONTINUE);
    }
}
