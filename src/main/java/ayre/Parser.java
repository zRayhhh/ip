package ayre;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ayre.enums.CommandType;
import ayre.exceptions.UnknownCommandException;
import ayre.exceptions.WrongNumberOfArgumentsException;

/**
 * Handles the parsing of user input, involving tokenizing and validating the arguments/commands.
 * The parser does not need to hold any information as its only purpose is
 * to take a String as input and give back a Command and list of arguments as output.
 */
public class Parser {
    private static final int SPLIT_ONCE_LIMIT = 2; // String::split() splits n - 1 times so split once requires 2
    private static final int COMMAND_TYPE_INDEX = 0;
    private static final int NAME_INDEX = 0;
    private static final int ARGUMENT_STRING_INDEX = 1;
    private static final int DATES_INDEX = 1;
    /**
     * Returns the matching Command and its arguments from a user-inputted String as a ParsedInput Record.
     * Expected that the output arguments can be passed into the corresponding methods without type errors.
     *
     * @param input The String entered by the user via command line.
     * @return Record comprising the Command and its arguments.
     * @throws UnknownCommandException If no matching Command is found.
     * @throws WrongNumberOfArgumentsException If wrong number of arguments are parsed.
     */
    public static ParsedInput parseInput(String input) throws UnknownCommandException,
            WrongNumberOfArgumentsException {
        String[] cmdWithArgs = input.trim().split(" ", SPLIT_ONCE_LIMIT);
        CommandType cmd = CommandType.parseCommand(cmdWithArgs[COMMAND_TYPE_INDEX]);
        if (cmd == null) {
            throw new UnknownCommandException("~ ...Raven, this command was not found in the Coral Collective. "
                    + "Was it a mistake?");
        }
        List<String> args = Parser.tokenizeArguments(cmd, input);
        assert args.size() == cmd.getNumArgs() : "Arguments list should be of the same size "
                + "as number of arguments required by the CommandType";
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
    static List<String> tokenizeArguments(CommandType cmd, String input) throws WrongNumberOfArgumentsException {
        if (cmd.requiresArgs()) { // filters out BYE and LIST
            String[] cmdWithArgs = input.trim().split(" ", SPLIT_ONCE_LIMIT);
            if (cmdWithArgs.length == 1) {
                throw new WrongNumberOfArgumentsException("~ Raven, some parameters are missing. I don't have "
                        + "enough information to carry out this command.");
            }
            String argLine = cmdWithArgs[ARGUMENT_STRING_INDEX]; // take the latter half of the split input as args
            return parseArguments(cmd, argLine);
        } else {
            if (input.trim().split(" ").length != 1) { // strict: no extra arguments to be passed with BYE & LIST
                throw new WrongNumberOfArgumentsException("~ Raven, there seems to be some extra parameters"
                        + " mixed in. Please try again.");
            }
            return new ArrayList<>();
        }
    }

    /**
     * Parses user-inputted arguments to the command to ensure the correct number of arguments are given.
     * Logically this only involves the commands that require 1 or more arguments.
     *
     * @param cmd CommandType involved
     * @param argLine User input String containing what should be the arguments
     * @return List which elements correspond to the arguments in order
     * @throws WrongNumberOfArgumentsException If wrong number of arguments are found while parsing
     */
    private static List<String> parseArguments(CommandType cmd, String argLine) throws WrongNumberOfArgumentsException {
        List<String> arguments = new ArrayList<>();
        switch (cmd) {
            case FIND: // fallthrough
            case MARK: // fallthrough
            case UNMARK: // fallthrough
            case DELETE: // fallthrough
            case TODO:
                arguments.add(argLine);
                break;
            case DEADLINE:
                String[] deadlineArgs = argLine.split(" /by ");
                if (deadlineArgs.length != 2) { // expecting to split only once
                    throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: "
                            + "deadline NAME-OF-TASK /by yyyy-mm-dd");
                }
                arguments.addAll(Arrays.asList(deadlineArgs)); // add name and time by
                break;
            case EVENT: // split and verify x2 to make sure no misuse of flags
                String[] eventArgsFromSplit = argLine.split(" /from ");
                if (eventArgsFromSplit.length != 2) { // expecting to split only once
                    throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: "
                            + "event NAME-OF-TASK /from yyyy-mm-dd /to yyyy-mm-dd");
                }
                arguments.add(eventArgsFromSplit[NAME_INDEX]); // add name
                String[] eventArgsToSplit = eventArgsFromSplit[DATES_INDEX].split(" /to ");
                if (eventArgsToSplit.length != 2) { // expecting to split only once
                    throw new WrongNumberOfArgumentsException("~ Raven... please follow the format: "
                            + "event NAME-OF-TASK /from yyyy-mm-dd /to yyyy-mm-dd");
                }
                arguments.addAll(Arrays.asList(eventArgsToSplit)); // add time from and time to
                break;
            default: // guaranteed to never happen by cmd == null check above
                assert false : cmd; // should be guaranteed that default case will never be reached
        }
        return arguments;
    }
}
