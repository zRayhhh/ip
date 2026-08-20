import java.util.Scanner;
import java.util.ArrayList;

public class Ayre {
    private TaskList tasks;

    public Ayre() {
        this.tasks = new TaskList();
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

        String userInput;
        String cleanInput;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            userInput = scanner.nextLine().trim();
            cleanInput = userInput.toLowerCase();
            if (cleanInput.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (cleanInput.equals("list")) {
                System.out.print(tasks);
            } else if (cleanInput.contains("unmark")) {
                tasks.unmarkTask(Integer.parseInt(cleanInput.split("\s")[1]) - 1);

            } else if (cleanInput.contains("mark")) {
                tasks.markTask(Integer.parseInt(cleanInput.split("\s")[1]) - 1);
            } else {
                String[] input = userInput.split("\s", 2);
                input[0] = input[0].toLowerCase();
                if (input[0].equals("todo")) {
                    tasks.addTask(new Todo(input[1]));
                } else if (input[0].equals("deadline")) {
                    String[] params = input[1].split(" /by ");
                    tasks.addTask(new Deadline(params[0], params[1]));
                } else if (input[0].equals("event")) {
                    String[] params = input[1].split(" /from ");
                    String[] times = params[1].split(" /to ");
                    tasks.addTask(new Event(params[0], times[0], times[1]));
                }
                System.out.print("~ [" + userInput + "] has been added to the mission list.\n> ");
            }
        }
    }

    public static void main(String[] args) {
        new Ayre().run();
    }
}
