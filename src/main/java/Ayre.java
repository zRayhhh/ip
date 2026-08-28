import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Ayre {
    private static final String LOG_PATH = "./data/ayre.txt";

    private final LiveTaskList tasks;
    private final BufferedReader reader;
    private final CommandExecutor executor;

    public Ayre() {
        this.tasks = LiveTaskList.load(new Storage(LOG_PATH));
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.executor = new CommandExecutor(this.tasks);
    }

    public void run() {
        String banner = """
                <<Main System: Activating Support Mode.>>
                     █████╗ ██╗   ██╗██████╗ ███████╗
                    ██╔══██╗╚██╗ ██╔╝██╔══██╗██╔════╝
                    ███████║ ╚████╔╝ ██████╔╝█████╗
                    ██╔══██║  ╚██╔╝  ██╔══██╗██╔══╝
                    ██║  ██║   ██║   ██║  ██║███████╗
                    ╚═╝  ╚═╝   ╚═╝   ╚═╝  ╚═╝╚══════╝
                    .~"~.__.~"~.__.~"~.__.~"~.__.~"~.
                """;
        String greeting = "~ Hello, Raven. What shall we do today?\n> ";
        System.out.print(banner + greeting);

        while (true) {
            try {
                ParsedInput parsedInput = Parser.parseInput(reader.readLine());
                CommandResult result = this.executor.execute(parsedInput.getCommand(), parsedInput.getArgs());
                if (result == CommandResult.TERMINATE) {
                    reader.close();
                    break;
                }
            } catch (WrongNumberOfArgumentsException e) {
                System.out.print("~ Raven. Please try again.\n> ");
            } catch (UnknownCommandException e) {
                System.out.print("~ ...Raven, this command was not found in the Coral Collective. Was it a mistake?\n> ");
            } catch (InvalidCommandArgumentsException e) {
                System.out.print("~ The data you just gave me doesn't seem to be valid.\n> ");
            } catch (IOException e) {
                // Do something
            }
        }
    }

    public static void main(String[] args) {
        new Ayre().run();
    }
}
