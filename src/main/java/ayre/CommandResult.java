package ayre;

import ayre.enums.AyreStatus;

/**
 * A record to pass immutably the reply message of the completed Command and the resulting process status.
 * Record is passed to the main loop in Ayre::run().
 * The message may be contextual information or a request to print something via a Command.
 *
 * @param message
 * @param status
 */
public record CommandResult(String message, AyreStatus status) {}