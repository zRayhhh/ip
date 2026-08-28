import java.util.List;

public class ParsedInput {
    private final Command command;
    private final List<String> args;

    public ParsedInput(Command command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    public Command getCommand() {
        return this.command;
    }

    public List<String> getArgs() {
        return this.args;
    }
}
