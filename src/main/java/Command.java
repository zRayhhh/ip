import java.util.List;

// Constructed with some modifications from Claude Sonnet 5 medium
public enum Command {
    BYE("bye", 0) {
        @Override
        public void validate(List<String> args) { /* Nothing to do here */ }
    },
    LIST("list", 0) {
        @Override
        public void validate(List<String> args) { /* Nothing to do here */ }
    },
    MARK("mark", 1) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
        }
    },
    UNMARK("unmark", 1) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
        }
    },
    DELETE("delete", 1) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
        }
    },
    TODO("todo", 1) {
        @Override
        public void validate(List<String> args) { /* Nothing to do here */ }
    },
    DEADLINE("deadline", 2) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidIsoDate(args.get(1))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
        }
    },
    EVENT("event", 3) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidIsoDate(args.get(1)) || ValidationTools.isInvalidIsoDate(args.get(2))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
        }
    };

    private final String name;
    private final int numArgs;

    Command(String name, int numArgs) {
        this.name = name;
        this.numArgs = numArgs;
    }

    public abstract void validate(List<String> args) throws InvalidCommandArgumentsException;

    public int getNumArgs() {
        return numArgs;
    }

    public static Command parseCommand(String input) {
        for (Command cmd : values()) {
            if (cmd.name.equals(input)) {
                return cmd;
            }
        }
        return null;
    }
}
