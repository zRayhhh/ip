public enum Command {
    BYE("bye", 0),
    LIST("list", 0),
    MARK("mark", 1),
    UNMARK("unmark", 1),
    DELETE("delete", 1),
    TODO("todo", 1),
    DEADLINE("deadline", 2),
    EVENT("event", 3);

    private final String name;
    private final int numArgs;
    Command(String name, int numArgs) {
        this.name = name;
        this.numArgs = numArgs;
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
