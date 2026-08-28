import java.util.List;

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
            if (!ValidationTools.isValidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected strictly positive integer as index");
            }
        }
    },
    UNMARK("unmark", 1) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (!ValidationTools.isValidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected strictly positive integer as index");
            }
        }
    },
    DELETE("delete", 1) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (!ValidationTools.isValidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected strictly positive integer as index");
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
            if (!ValidationTools.isValidIsoDate(args.get(1))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
        }
    },
    EVENT("event", 3) {
        @Override
        public void validate(List<String> args) throws InvalidCommandArgumentsException {
            if (!ValidationTools.isValidIsoDate(args.get(1)) || !ValidationTools.isValidIsoDate(args.get(2))) {
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
