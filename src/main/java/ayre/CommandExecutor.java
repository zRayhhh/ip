package ayre;

import ayre.enums.AyreStatus;
import ayre.enums.CommandType;

import ayre.tasks.Deadline;
import ayre.tasks.Event;

import ayre.exceptions.InvalidCommandArgumentsException;

import ayre.tasks.Todo;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Handles the execution of Commands with an EnumMap mapping each Command to its manner of execution.
 * Constructed with some modifications from Claude Sonnet 5 medium
 */
public class CommandExecutor {
    @FunctionalInterface
    interface CommandHandler {
        CommandResult handle(List<String> args) throws InvalidCommandArgumentsException;
    }

    private final Map<CommandType, CommandHandler> HANDLER = new EnumMap<>(CommandType.class);

    /**
     * Class constructor.
     * Initializes the EnumMap by adding all mappings between Commands and their execution methods.
     *
     * @param tasks The LiveTaskList used by the main process.
     */
    public CommandExecutor(LiveTaskList tasks) {
        // Returns a goodbye message and is the only one to return a TERMINATE status.
        HANDLER.put(CommandType.BYE, args ->
                new CommandResult("~ Terminating connection. See you again, Raven.\n", AyreStatus.TERMINATE));
        // Returns the toString() of the LiveTaskList.
        HANDLER.put(CommandType.LIST, args ->
                new CommandResult(tasks.toString(), AyreStatus.CONTINUE));
        HANDLER.put(CommandType.FIND, args ->
                new CommandResult(tasks.findTasks(args.get(0)), AyreStatus.CONTINUE));
        // Returns the toString() of the Task after marking it as complete.
        // @param A List<String> holding a single numeric String that is the index of the Task.
        HANDLER.put(CommandType.MARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("~ Invalid index entered. " +
                        "Raven, the available indexes are 1 to " + tasks.getNumTasks());
            }
            String resultMsg = tasks.mark(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        // Returns the toString() of the Task after marking it as incomplete.
        // @param A List<String> holding a single numeric String that is the index of the Task.
        HANDLER.put(CommandType.UNMARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("~ Invalid index entered. " +
                        "Raven, the available indexes are 1 to " + tasks.getNumTasks());
            }
            String resultMsg = tasks.unmark(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        // Returns the toString() of the Task after removing it from the LiveTaskList.
        // @param A List<String> holding a single numeric String that is the index of the Task.
        HANDLER.put(CommandType.DELETE, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("~ Invalid index entered. " +
                        "Raven, the available indexes are 1 to " + tasks.getNumTasks());
            }
            String resultMsg = tasks.del(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        // Returns the toString() of the Todo after adding it to the LiveTaskList.
        // @param A List<String> holding a single String that is the name of the Todo.
        HANDLER.put(CommandType.TODO, args -> {
            String resultMsg = tasks.add(new Todo(args.get(0)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        // Returns the toString() of the Deadline after adding it to the LiveTaskList.
        // @param A List<String> holding 2 Strings: the name of the Deadline and the date due by.
        HANDLER.put(CommandType.DEADLINE, args -> {
            String resultMsg = tasks.add(new Deadline(args.get(0), args.get(1)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        // Returns the toString() of the Event after adding it to the LiveTaskList.
        // @param A List<String> holding 3 Strings: the name of the Event, start date, and end date.
        HANDLER.put(CommandType.EVENT, args -> {
            String resultMsg = tasks.add(new Event(args.get(0), args.get(1), args.get(2)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
    }

    /**
     * Runs the handler of the given command.
     *
     * @param cmd Command to be executed.
     * @param args Arguments required for Command execution.
     * @return A Record of the message on completion and the process status.
     * @throws InvalidCommandArgumentsException If arguments will cause a RunTimeException.
     */
    public CommandResult execute(CommandType cmd, List<String> args) throws InvalidCommandArgumentsException {
        return HANDLER.get(cmd).handle(args);
    }
}
