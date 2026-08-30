package ayre;

import ayre.enums.AyreStatus;

import ayre.exceptions.AyreException;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;
import ayre.tasks.LoadResult;

import java.io.PrintStream;

import java.nio.charset.StandardCharsets;

/**
 * Main Class that handles the high-level operation of the chatbot.
 */
public class Ayre {
    private static final String LOG_PATH = "./data/ayre.txt";

    private final Ui ui;
    private final CommandExecutor executor;

    /**
     * Instantiate UI, Storage, TaskList, CommandExecutor and ensure they can communicate.
     */
    public Ayre() {
        this.ui = new Ui(System.in, System.out);
        Storage store = new Storage(LOG_PATH);
        LoadResult res = store.load();
        if (!res.warnings().isEmpty()) {
            ui.showWarning(res.getWarningsAsString());
        }
        LiveTaskList tasks = new LiveTaskList(res.tasks(), store);
        this.executor = new CommandExecutor(tasks);
    }

    /**
     * Hosts the main loop for Ayre.
     * In every loop, attempt to parse user input and execute the related command,
     * then displays the result as a console message.
     */
    public void run() {
        this.ui.showGreeting();
        while (true) {
            try {
                ParsedInput parsedInput = Parser.parseInput(ui.readLine());
                CommandResult result = this.executor.execute(parsedInput.command(), parsedInput.args());
                ui.showMessage(result.message());
                if (result.status() == AyreStatus.TERMINATE) {
                    break;
                }
            } catch (AyreException e) {
                ui.showError(e.getMessage());
            }
        }
        ui.close();
    }

    public static void main(String[] args) {
        // clamping output bytes to UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        new Ayre().run();
    }
}
