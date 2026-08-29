package ayre;

import ayre.enums.AyreStatus;
import ayre.enums.Command;

import ayre.tasks.Deadline;
import ayre.tasks.Event;

import ayre.exceptions.InvalidCommandArgumentsException;

import ayre.tasks.Todo;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// Constructed with some modifications from Claude Sonnet 5 medium
public class CommandExecutor {
    @FunctionalInterface
    interface CommandHandler {
        CommandResult handle(List<String> args) throws InvalidCommandArgumentsException;
    }

    private final Map<Command, CommandHandler> HANDLER = new EnumMap<>(Command.class);

    public CommandExecutor(LiveTaskList tasks) {
        HANDLER.put(Command.BYE, args ->
                new CommandResult("~ Terminating connection. See you again, Raven.\n", AyreStatus.TERMINATE));
        HANDLER.put(Command.LIST, args ->
                new CommandResult(tasks.toString(), AyreStatus.CONTINUE));
        HANDLER.put(Command.FIND, args ->
                new CommandResult(tasks.findTasks(args.get(0)), AyreStatus.CONTINUE));
        HANDLER.put(Command.MARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            String resultMsg = tasks.mark(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        HANDLER.put(Command.UNMARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            String resultMsg = tasks.unmark(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        HANDLER.put(Command.DELETE, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            String resultMsg = tasks.del(index);
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        HANDLER.put(Command.TODO, args -> {
            String resultMsg = tasks.add(new Todo(args.get(0)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        HANDLER.put(Command.DEADLINE, args -> {
            String resultMsg = tasks.add(new Deadline(args.get(0), args.get(1)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
        HANDLER.put(Command.EVENT, args -> {
            String resultMsg = tasks.add(new Event(args.get(0), args.get(1), args.get(2)));
            return new CommandResult(resultMsg, AyreStatus.CONTINUE);
        });
    }

    public CommandResult execute(Command cmd, List<String> args) throws InvalidCommandArgumentsException {
        return HANDLER.get(cmd).handle(args);
    }
}
