import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Ayre {
    private static final String LOG_PATH = "./data/ayre.txt";

    private final LiveTaskList tasks;
    private final BufferedReader reader;

    public Ayre() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.tasks = LiveTaskList.load(new Storage(LOG_PATH));
    }

    // task log T/D/E 0/1 name time1 time2

    public String parseInput(String input) throws InvalidCommandException, WrongNumberOfArgumentsException {
        String[] body = input.trim().split(" ", 2);
        String cmd = body[0].toLowerCase();
        if (cmd.equals("bye")) {
            System.out.println("~ Terminating connection. See you again, Raven.");
        } else if (cmd.equals("list")) {
            System.out.print(tasks);
        } else if (cmd.equals("unmark")) {
            tasks.unmark(Integer.parseInt(body[1]) - 1);
        } else if (cmd.equals("mark")) {
            tasks.mark(Integer.parseInt(body[1]) - 1);
        } else if (cmd.equals("delete")) {
            tasks.del(Integer.parseInt(body[1]) - 1);
        } else {
            if (cmd.equals("todo")) {
                if (body.length < 2) throw new WrongNumberOfArgumentsException("Mission body not found.");
                tasks.add(new Todo(body[1]));
            } else if (cmd.equals("deadline")) {
                String[] params = body[1].split(" /by ");
                tasks.add(new Deadline(params[0], params[1]));
            } else if (cmd.equals("event")) {
                String[] params = body[1].split(" /from | /to ");
                tasks.add(new Event(params[0], params[1], params[2]));
            } else {
                throw new InvalidCommandException("Unknown command.");
            }
            System.out.print("~ [" + input + "] has been added to the mission list.\n> ");
        }
        return cmd;
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
                String cmd = parseInput(reader.readLine());
                if (cmd.equals("bye")) break;
            } catch (WrongNumberOfArgumentsException e) {
                System.out.print("~ Raven, I'm not seeing the mission details. Please try again.\n> ");
            } catch (InvalidCommandException e) {
                System.out.print("~ ...Raven, this command was not found in the Coral Collective. Was it a mistake?\n> ");
            } catch (IOException e) {
                // Do something
            }
        }
    }

    public static void main(String[] args) {
        new Ayre().run();
    }
}
