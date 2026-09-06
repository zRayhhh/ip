package ayre.enums;

/**
 * Represents the different Commands available to the user.
 * Each Command holds its name and the number of arguments that it requires.
 * Each Command, given its correct number of arguments, is able to validate those arguments
 * for whether they adhere to the required format. Whether the input works is not checked.
 */
public enum CommandType {
    /** Terminate the process. Requires no arguments. */
    BYE("bye", 0),
    /** Print the entire TaskList for viewing. Requires no arguments. */
    LIST("list", 0),
    /** Print all tasks which name contains a strict match to the input String. */
    FIND("find", 1),
    /** Mark a Task as complete. Requires the index of the Task as shown in the TaskList. */
    MARK("mark", 1),
    /** Unmark a Task as complete. Requires the index of the Task as shown in the TaskList. */
    UNMARK("unmark", 1),
    /** Remove the Task from the TaskList. Requires the index of the Task as shown in the TaskList. */
    DELETE("delete", 1),
    /** Add a new Todo to the TaskList. Requires the name of the Todo. */
    TODO("todo", 1),
    /** Add a new Deadline to the TaskList. Requires the name of the Deadline and the date due by. */
    DEADLINE("deadline", 2),
    /** Add a new Event to the TaskList. Requires the name of the Event, the starting date, and the end date. */
    EVENT("event", 3);

    private final String name;
    private final int numArgs;

    /**
     * Initializes the name and number of arguments of the enum element.
     *
     * @param name Name of the enum element.
     * @param numArgs Number of arguments related to the enum element.
     */
    CommandType(String name, int numArgs) {
        this.name = name;
        this.numArgs = numArgs;
    }

    /**
     * Getter for the number of arguments related to the enum element.
     * Allows for correctness checks on user input.
     *
     * @return Number of arguments
     */
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
    public static CommandType parseCommand(String input) {
        for (CommandType cmd : values()) {
            if (cmd.name.equals(input)) {
                return cmd;
            }
        }
        return null;
    }
}
