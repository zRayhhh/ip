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

        ArrayList<String> lst = new ArrayList<>();
        String userInput;
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.compareToIgnoreCase("bye") == 0) {
                System.out.println(goodbye);
                break;
            } else if (userInput.compareToIgnoreCase("list") == 0) {
                for (int i = 1; i <= lst.size(); i++) {
                    System.out.println(i + ". " + lst.get(i - 1));
                }
                System.out.print("> ");
            } else {
                lst.add(userInput);
                System.out.print("~ [" + userInput + "] has been added to the mission list.\n> ");
            }
        }
    }
}
