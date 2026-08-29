package ayre;

import ayre.enums.Command;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    public static ParsedInput parseInput(String input) throws UnknownCommandException,
            WrongNumberOfArgumentsException, InvalidCommandArgumentsException {
        String[] cmdWithArgs = input.trim().split(" ", 2);
        Command cmd = Command.parseCommand(cmdWithArgs[0]);
        if (cmd == null) {
            throw new UnknownCommandException("Input not recognized as a valid command");
        }

        List<String> args = Parser.tokenizeArgs(cmd, input);
        cmd.validate(args);
        return new ParsedInput(cmd, args);
    }

    // package private for junit test to run directly on this
    static List<String> tokenizeArgs(Command cmd, String input) throws WrongNumberOfArgumentsException {
        List<String> args = new ArrayList<>();
        if (cmd.getNumArgs() != 0) {    // filters out BYE and LIST
            String[] cmdWithArgs = input.trim().split(" ", 2);
            if (cmdWithArgs.length == 1) {
                throw new WrongNumberOfArgumentsException("Insufficient arguments");
            }
            String argLine = cmdWithArgs[1];
            switch (cmd) {
                case MARK:
                case UNMARK:
                case DELETE:
                case TODO:
                    if (argLine.split(" ").length > 1) {
                        throw new WrongNumberOfArgumentsException("Arguments provided where not expected");
                    }
                    args.add(argLine);
                    break;
                case DEADLINE:
                    String[] deadlineArgs = argLine.split(" /by ");
                    if (deadlineArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("Placeholder");
                    }
                    args.addAll(Arrays.asList(deadlineArgs)); // add name and time by
                    break;
                case EVENT:
                    String[] eventArgs = argLine.split(" /from ");
                    if (eventArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("Placeholder");
                    }
                    args.add(eventArgs[0]); // add name
                    eventArgs = eventArgs[1].split(" /to ");
                    if (eventArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("Placeholder");
                    }
                    args.addAll(Arrays.asList(eventArgs)); // add time from and time to
                    break;
            }
        } else {
            if (input.trim().split(" ").length != 1) {
                throw new WrongNumberOfArgumentsException("Arguments provided where not expected");
            }
        }
        return args;
    }
}
