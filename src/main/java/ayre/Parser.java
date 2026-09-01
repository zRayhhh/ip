package ayre;

import ayre.enums.CommandType;
import ayre.exceptions.InvalidCommandArgumentsException;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Handles the parsing of user input, involving tokenizing and validating the arguments/commands.
 * The parser does not need to hold any information as its only purpose is
 * to take a String as input and give back a Command and list of arguments as output.
 */
public class Parser {
    /**
     * Returns the matching Command and its arguments from a user-inputted String as a ParsedInput Record.
     * Expected that the output arguments can be passed into the corresponding methods without type errors.
     *
     * @param input The String entered by the user via command line.
     * @return Record comprising the Command and its arguments.
     * @throws UnknownCommandException If no matching Command is found.
     * @throws WrongNumberOfArgumentsException If wrong number of arguments are parsed.
     * @throws InvalidCommandArgumentsException If arguments will cause type errors when passed to methods.
     */
    public static ParsedInput parseInput(String input) throws UnknownCommandException,
            WrongNumberOfArgumentsException, InvalidCommandArgumentsException {
        String[] cmdWithArgs = input.trim().split(" ", 2);
        CommandType cmd = CommandType.parseCommand(cmdWithArgs[0]);
        if (cmd == null) {
            throw new UnknownCommandException("~ ...Raven, this command was not found in the Coral Collective. " +
                    "Was it a mistake?");
        }
        List<String> args = Parser.tokenizeArgs(cmd, input);
        cmd.validate(args);
        return new ParsedInput(cmd, args);
    }

    /**
     * Splits the user-inputted String into the corresponding arguments of the Command.
     * Package private for JUnit tests.
     *
     * @param cmd Command parsed from input.
     * @param input User-inputted String.
     * @return List of arguments as Strings.
     * @throws WrongNumberOfArgumentsException If wrong number of arguments are detected after tokenizing.
     */
    static List<String> tokenizeArgs(CommandType cmd, String input) throws WrongNumberOfArgumentsException {
        List<String> args = new ArrayList<>();
        if (cmd.getNumArgs() != 0) {    // filters out BYE and LIST
            String[] cmdWithArgs = input.trim().split(" ", 2);  // split once to check for name/index
            if (cmdWithArgs.length == 1) {
                throw new WrongNumberOfArgumentsException("~ Raven, some parameters are missing. I don't have " +
                        "enough information to carry out this command.");
            }
            String argLine = cmdWithArgs[1];    // take the latter half of the split input as args
            switch (cmd) {
                case FIND:      // fallthrough
                case MARK:      // fallthrough
                case UNMARK:    // fallthrough
                case DELETE:    // fallthrough
                case TODO:
                    args.add(argLine);
                    break;
                case DEADLINE:
                    String[] deadlineArgs = argLine.split(" /by ");
                    if (deadlineArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: " +
                                "deadline NAME-OF-TASK /by yyyy-mm-dd");
                    }
                    args.addAll(Arrays.asList(deadlineArgs)); // add name and time by
                    break;
                case EVENT:
                    String[] eventArgs = argLine.split(" /from ");
                    if (eventArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: " +
                                "event NAME-OF-TASK /from yyyy-mm-dd /to yyyy-mm-dd");
                    }
                    args.add(eventArgs[0]); // add name
                    eventArgs = eventArgs[1].split(" /to ");
                    if (eventArgs.length != 2) {
                        throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: " +
                                "event NAME-OF-TASK /from yyyy-mm-dd /to yyyy-mm-dd");
                    }
                    args.addAll(Arrays.asList(eventArgs)); // add time from and time to
                    break;
            }
        } else {
            if (input.trim().split(" ").length != 1) {
                throw new WrongNumberOfArgumentsException("~ Raven, there seems to be some extra parameters" +
                        " mixed in. Please try again.");
            }
        }
        return args;
    }
}
