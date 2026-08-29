package ayre;

import ayre.enums.Command;

import java.util.List;

/**
 * A record to pass immutably the parsed Command and List of arguments back to the main loop in Ayre::run().
 *
 * @param command
 * @param args
 */
public record ParsedInput(Command command, List<String> args) {}