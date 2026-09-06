package ayre;

import java.util.List;

import ayre.enums.CommandType;

/**
 * A record to pass immutably the parsed Command and List of arguments back to the main loop in Ayre::run().
 *
 * @param command
 * @param args
 */
public record ParsedInput(CommandType command, List<String> args) {}
