package ayre;

import ayre.enums.AyreStatus;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

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
                CommandResult result = this.executor.execute(parsedInput.getCommand(), parsedInput.getArgs());
                ui.showMessage(result.getMessage());
                if (result.getStatus() == AyreStatus.TERMINATE) {
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
        new Ayre().run();
    }
}
