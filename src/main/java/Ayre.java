import java.util.Scanner;

public class Ayre {
    private TaskList tasks;

    public Ayre() {
        this.tasks = new TaskList();
    }

    public String parseInput(String input) throws InvalidCommandException, InsufficientArgumentsException {
        String[] body = input.trim().split("\s", 2);
        String cmd = body[0].toLowerCase();
        if (cmd.equals("bye")) {
            System.out.println("~ Terminating connection. See you again, Raven.");
        } else if (cmd.equals("list")) {
            System.out.print(tasks);
        } else if (cmd.equals("unmark")) {
            tasks.unmarkTask(Integer.parseInt(body[1]) - 1);
        } else if (cmd.equals("mark")) {
            tasks.markTask(Integer.parseInt(body[1]) - 1);
        } else {
            if (cmd.equals("todo")) {
                if (body.length < 2) throw new InsufficientArgumentsException("Mission body not found.");
                tasks.addTask(new Todo(body[1]));
            } else if (cmd.equals("deadline")) {
                String[] params = body[1].split(" /by ");
                tasks.addTask(new Deadline(params[0], params[1]));
            } else if (cmd.equals("event")) {
                String[] params = body[1].split(" /from | /to ");
                tasks.addTask(new Event(params[0], params[1], params[2]));
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
                    .~"~.__.~"~.__.~"~.__.~"~.__.~"~.\n""";
        String greeting = "~ Hello, Raven. What shall we do today?\n> ";
        String goodbye = "~ Terminating connection. See you again, Raven.";
        System.out.print(banner + greeting);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                String cmd = parseInput(scanner.nextLine());
                if (cmd.equals("bye")) break;
            } catch (InsufficientArgumentsException e) {
                System.out.print("~ Raven, I'm not seeing the mission details. Please try again.\n> ");
            } catch (InvalidCommandException e) {
                System.out.print("~ ...Raven, this command was not found in the Coral Collective. Was it a mistake?\n> ");
            }
        }
    }

    public static void main(String[] args) {
        new Ayre().run();
    }
}
