import java.util.Locale;
import java.util.Scanner;
import java.util.ArrayList;

public class Ayre {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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

        ArrayList<Task> lst = new ArrayList<>();
        String userInput;
        String cleanInput;
        while (true) {
            userInput = scanner.nextLine().trim();
            cleanInput = userInput.toLowerCase();
            if (cleanInput.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (cleanInput.equals("list")) {
                System.out.println("~ Current missions:");
                for (int i = 1; i <= lst.size(); i++) {
                    System.out.println(i + ". " + lst.get(i - 1));
                }
                System.out.print("> ");
            } else if (cleanInput.contains("unmark")) {
                // input.matches("^unmark [0-9]+$");
                Task task = lst.get(Integer.parseInt(cleanInput.split("\s")[1]) - 1);
                task.unmarkComplete();
                System.out.print("~ The mission is still pending, Raven. Let's get to it.\n" + task + "\n> ");
            } else if (cleanInput.contains("mark")) {
                Task task = lst.get(Integer.parseInt(cleanInput.split("\s")[1]) - 1);
                task.markComplete();
                System.out.print("~ Mission complete. Good work, Raven.\n" + task + "\n> ");
            } else {
                String[] input = userInput.split("\s", 2);
                input[0] = input[0].toLowerCase();
                if (input[0].equals("todo")) {
                    lst.add(new Todo(input[1]));
                } else if (input[0].equals("deadline")) {
                    String[] params = input[1].split(" /by ");
                    lst.add(new Deadline(params[0], params[1]));
                } else if (input[0].equals("event")) {
                    String[] params = input[1].split(" /from ");
                    String[] times = params[1].split(" /to ");
                    lst.add(new Event(params[0], times[0], times[1]));
                }
                System.out.print("~ [" + userInput + "] has been added to the mission list.\n> ");
            }
        }
    }
}
