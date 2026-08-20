import java.util.Scanner;

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
        
        String userInput;
        while (true) {
            userInput = scanner.nextLine();
            if (userInput.compareToIgnoreCase("bye") == 0) {
                System.out.println(goodbye);
                break;
            } else {
                System.out.print("~ " + userInput + "\n> ");
            }
        }
    }
}
