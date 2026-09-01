package ayre;

import ayre.commands.*;
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
    private final Map<CommandType, Command> HANDLER = new EnumMap<>(CommandType.class);

    /**
     * Class constructor.
     * Initializes the EnumMap by adding all mappings between CommandTypes and their Command class.
     *
     * @param tasks The LiveTaskList used by the main process.
     */
    public CommandExecutor(LiveTaskList tasks) {
        HANDLER.put(CommandType.BYE, new ByeCommand(tasks));
        HANDLER.put(CommandType.LIST, new ListCommand(tasks));
        HANDLER.put(CommandType.FIND, new FindCommand(tasks));
        HANDLER.put(CommandType.MARK, new MarkCommand(tasks));
        HANDLER.put(CommandType.UNMARK, new UnmarkCommand(tasks));
        HANDLER.put(CommandType.DELETE, new DeleteCommand(tasks));
        HANDLER.put(CommandType.TODO, new TodoCommand(tasks));
        HANDLER.put(CommandType.DEADLINE, new DeadlineCommand(tasks));
        HANDLER.put(CommandType.EVENT, new EventCommand(tasks));

        for (CommandType c : CommandType.values()) {
            if (!HANDLER.containsKey(c)) {
                throw new IllegalStateException("No executor registered for command: " + c);
            }
        }
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
        return HANDLER.get(cmd).doCommand(args);
    }
}
