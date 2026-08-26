import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import java.util.Scanner;   // switch to BufferedReader at some point
import java.util.stream.Stream;

public class Ayre {
    private static final Path DATA = Path.of("./data");
    private static final Path TASK_LOG = DATA.resolve("ayre.txt");

    private TaskList tasks;
    private Scanner inputScanner;

    public Ayre() {
        this.inputScanner = new Scanner(System.in);
        this.tasks = this.readTaskLog();        // shift into a factory method
    }

    // task log T/D/E,0/1,name,time1,time2

    private void createTaskLog() {
        try {
            Files.createDirectories(DATA);
            Files.createFile(TASK_LOG);
        } catch (IOException e) {
            System.out.println("Failed to create file or directory");
        }
    }

    private TaskList readTaskLog() {     // update to static factory method
        TaskList lst = new TaskList();
        try (Stream<String> lines = Files.lines(TASK_LOG)) {
            lines.forEach(line -> {
                    String[] args = line.split(",");
                    Task tsk;
                    try {
                        tsk = switch (args[0]) {
                            case "T" -> new Todo(args[2]);
                            case "D" -> new Deadline(args[2], args[3]);
                            case "E" -> new Event(args[2], args[3], args[4]);
                            default ->
                                    throw new TaskLogCorruptedException("Unexpected value encountered in file read");
                        };
                        if (args[1].equals("1")) { tsk.markComplete(); }
                        lst.addTask(tsk);
                    } catch (TaskLogCorruptedException e) {
                        // smth wrong with the data file, try to salvage or skip to next line (create error dump maybe)
                    }
                });
        } catch (NoSuchFileException e) {
            System.out.println("~ First contact with Coral Collective established");
            this.tasks = new TaskList();
            this.createTaskLog();
        } catch (IOException e) {
            // something went bad
        }
        return lst;
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
        } else if (cmd.equals("delete")) {
            tasks.delTask(Integer.parseInt(body[1]) - 1);
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
        System.out.print(banner + greeting);

        while (true) {
            try {
                String cmd = parseInput(inputScanner.nextLine());
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
