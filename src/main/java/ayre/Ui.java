package ayre;

import java.io.InputStream;
import java.io.PrintStream;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Handles user input scanning and printing to console.
 * Relevant results of user input and exceptions are printed by this class.
 *
 * Constructed with some modifications from Claude Sonnet 5 medium
 */
public class Ui {
    private final Scanner SCANNER;
    private final PrintStream OUT;

    public Ui(InputStream in, PrintStream out) {
        this.SCANNER = new Scanner(in);
        this.OUT = out;
    }

    public String readLine() throws NoSuchElementException {
        return this.SCANNER.nextLine();
    }

    public void close() {
        this.SCANNER.close();
    }

    public void showGreeting() {
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
        this.OUT.print(banner + greeting);
    }

    public void showMessage(String message) {
        this.OUT.print(message);
        this.showNewUserLine();
    }

    public void showError(String errorMsg) {
        this.OUT.print("<<Main System: Error Detected>>\n" + errorMsg);
        this.showNewUserLine();
    }

    public void showWarning(String warning) {
        this.OUT.print(warning);
    }

    private void showNewUserLine() {
        this.OUT.print("\n> ");
    }
}
