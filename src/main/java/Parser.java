import java.util.List;

public class Parser {
    public static parseCmd(String input) throws UnknownCommandException, WrongNumberOfArgumentsException {
        String[] cmdWithArgs = input.trim().split(" ");
        Command cmd = Command.parseCommand(cmdWithArgs[0]);
        if (cmd == null) throw new UnknownCommandException("Input not recognized as a valid command");

        switch (cmd) {

        }
    }

    public static List<String> tokenizeArgs(Command cmd, String args) throws WrongNumberOfArgumentsException {
            switch (cmd) {

            }

        }
    }


}
