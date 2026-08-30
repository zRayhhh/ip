package ayre.enums;

import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.ValidationTools;

import java.util.List;

// Constructed with some modifications from Claude Sonnet 5 medium

/**
 * Represents the different Commands available to the user.
 * Each Command holds its name and the number of arguments that it requires.
 * Each Command, given its correct number of arguments, is able to validate those arguments
 * for whether they adhere to the required format. Whether the input works is not checked.
 */
public enum Command {
    /** Terminate the process. Requires no arguments. */
    BYE("bye", 0) {
        /**
         * Returns true by default as there is nothing to validate.
         *
         * @param args Expected to be an empty List.
         * @return true
         */
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    /** Print the entire TaskList for viewing. Requires no arguments. */
    LIST("list", 0) {
        /**
         * Returns true by default as there is nothing to validate.
         *
         * @param args Expected to be an empty List.
         * @return true
         */
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
    /** Mark a Task as complete. Requires the index of the Task as shown in the TaskList. */
    MARK("mark", 1) {
        /**
         * Checks whether the argument is a numeric String.
         *
         * @param args Expected to be a List with a single element that is the argument for the Command.
         * @return true
         * @throws InvalidCommandArgumentsException If argument is not a numeric String.
         */
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    /** Unmark a Task as complete. Requires the index of the Task as shown in the TaskList. */
    UNMARK("unmark", 1) {
        /**
         * Checks whether the argument is a numeric String.
         *
         * @param args Expected to be a List with a single element that is the argument for the Command.
         * @return true
         * @throws InvalidCommandArgumentsException If argument is not a numeric String.
         */
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    /** Remove the Task from the TaskList. Requires the index of the Task as shown in the TaskList. */
    DELETE("delete", 1) {
        /**
         * Checks whether the argument is a numeric String.
         *
         * @param args Expected to be a List with a single element that is the argument for the Command.
         * @return true
         * @throws InvalidCommandArgumentsException If argument is not a numeric String.
         */
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidTaskIndex(args.get(0))) {
                throw new InvalidCommandArgumentsException("Expected integer value");
            }
            return true;
        }
    },
    /** Add a new Todo to the TaskList. Requires the name of the Todo. */
    TODO("todo", 1) {
        /**
         * Returns true by default as the name does not need to be validated.
         *
         * @param args Expected to be a List with a single element that is the name.
         * @return true
         */
        @Override
        public boolean validate(List<String> args) {
            return true;
        }
    },
    /** Add a new Deadline to the TaskList. Requires the name of the Deadline and the date due by. */
    DEADLINE("deadline", 2) {
        /**
         * Checks whether the date argument is a String that matches the ISO_LOCAL_DATE format.
         * The matching is strict, i.e. impossible dates like 13/13 are invalid.
         * Name does not need to be validated.
         *
         * @param args Expected to be a List with a name and the date in that order.
         * @return true
         * @throws InvalidCommandArgumentsException If argument is not a valid ISO_LOCAL_DATE.
         */
        @Override
        public boolean validate(List<String> args) throws InvalidCommandArgumentsException {
            if (ValidationTools.isInvalidIsoDate(args.get(1))) {
                throw new InvalidCommandArgumentsException("Date does not adhere to ISO_LOCAL_DATE format");
            }
            return true;
        }
    },
    /** Add a new Event to the TaskList. Requires the name of the Event, the starting date, and the end date. */
    EVENT("event", 3) {
        /**
         * Checks whether the date arguments are Strings that match the ISO_LOCAL_DATE format.
         * The matching is strict, i.e. impossible dates like 13/13 are invalid.
         * Name does not need to be validated.
         *
         * @param args Expected to be a List with a name, start date, and end date in that order.
         * @return true
         * @throws InvalidCommandArgumentsException If arguments are not valid ISO_LOCAL_DATE's.
         */
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

    /**
     * Returns the Command that the given String matches to.
     * If there is no match, null is returned.
     *
     * @param input A String without whitespace from processed user input.
     * @return Command matching the String.
     */
    public static Command parseCommand(String input) {
        for (Command cmd : values()) {
            if (cmd.name.equals(input)) {
                return cmd;
            }
        }
        return null;
    }
}
