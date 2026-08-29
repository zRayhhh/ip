package ayre;

import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

import java.io.PrintStream;

import java.nio.charset.StandardCharsets;

public class Ayre {
    private static final String LOG_PATH = "./data/ayre.txt";

    private final Ui ui;
    private final CommandExecutor executor;

    public Ayre() {
        this.ui = new Ui(System.in, System.out);
        LiveTaskList tasks = LiveTaskList.load(new Storage(LOG_PATH));
        this.executor = new CommandExecutor(tasks);
    }

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
            } catch (WrongNumberOfArgumentsException e) {
                System.out.print("~ Raven. Please try again.\n> ");
            } catch (UnknownCommandException e) {
                System.out.print("~ ...Raven, this command was not found in the Coral Collective. Was it a mistake?\n> ");
            } catch (InvalidCommandArgumentsException e) {
                System.out.print("~ The data you just gave me doesn't seem to be valid.\n> ");
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
