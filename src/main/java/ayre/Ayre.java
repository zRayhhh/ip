package ayre;

import ayre.enums.AyreStatus;
import ayre.exceptions.AyreException;

/**
 * Main Class that handles the high-level operation of the chatbot.
 */
public class Ayre {
    private static final String LOG_PATH = "./data/ayre.txt";
    private final String loadMessage;

    private final CommandExecutor executor;

    /**
     * Initialize UI, Storage, TaskList, CommandExecutor and ensure they can communicate.
     */
    public Ayre() {
        Storage store = new Storage(LOG_PATH);
        LoadResult res = store.load();
        this.loadMessage = res.loadMessage();
        LiveTaskList tasks = new LiveTaskList(res.tasks(), store);
        this.executor = new CommandExecutor(tasks);
    }

    /**
     * Getter for the message on loading from save file.
     * Allows the GUI to display the load result.
     *
     * @return Any accumulated messages from loading from save file.
     */
    public String getLoadMessage() {
        return this.loadMessage;
    }

    /**
     * Reads, executes, then returns the result of user input as a String.
     *
     * @param input User input through the GUI.
     * @return Result of executing the relevant command.
     */
    public String getResponse(String input) {
        try {
            ParsedInput parsedInput = Parser.parseInput(input);
            CommandResult result = this.executor.execute(parsedInput.command(), parsedInput.args());
            if (result.status() == AyreStatus.TERMINATE) {
                return "TERMINATE";
            }
            return result.message();
        } catch (AyreException e) {
            return e.getMessage();
        }
    }
}
