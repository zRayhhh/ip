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
        HANDLER.put(Command.BYE, args -> CommandResult.TERMINATE);
        HANDLER.put(Command.LIST, args -> {
            System.out.print(tasks);
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.MARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            tasks.mark(index);
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.UNMARK, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            tasks.unmark(index);
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.DELETE, args -> {
            int index = Integer.parseInt(args.get(0)) - 1;      // user inputs index starting from 1
            if (index >= tasks.getNumTasks() || index < 0) {
                throw new InvalidCommandArgumentsException("Invalid index entered");
            }
            tasks.del(index);
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.TODO, args -> {
            tasks.add(new Todo(args.get(0)));
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.DEADLINE, args -> {
            tasks.add(new Deadline(args.get(0), args.get(1)));
            return CommandResult.CONTINUE;
        });
        HANDLER.put(Command.EVENT, args -> {
            tasks.add(new Event(args.get(0), args.get(1), args.get(2)));
            return CommandResult.CONTINUE;
        });
    }

    public CommandResult execute(Command cmd, List<String> args) throws InvalidCommandArgumentsException {
        return HANDLER.get(cmd).handle(args);
    }
}
