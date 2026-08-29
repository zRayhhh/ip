package ayre.enums;

import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.ValidationTools;

import java.util.List;

// Constructed with some modifications from Claude Sonnet 5 medium
public enum Command {
    BYE("bye", 0) {
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    LIST("list", 0) {
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    FIND("find", 1) {
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    MARK("mark", 1) {
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    UNMARK("unmark", 1) {
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    DELETE("delete", 1) {
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    TODO("todo", 1) {
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    DEADLINE("deadline", 2) {
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidIsoDate(args.get(1))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
            return true;
        }
    },
    EVENT("event", 3) {
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidIsoDate(args.get(1)) || ValidationTools.isInvalidIsoDate(args.get(2))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
            return true;
        }
    };

    private final String name;
    private final int numArgs;

    Command(String name, int numArgs) {
        this.name = name;
        this.numArgs = numArgs;
    }

    public abstract boolean validate(List<String> args) throws InvalidCommandArgumentsException;

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
