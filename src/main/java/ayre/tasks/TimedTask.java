package ayre.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A subset of Task that hold(s) date(s).
 * Defines how the date should be formatted when printed.
 */
public abstract class TimedTask extends Task {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");;

    public TimedTask(String name) {
        super(name);
    }

    /**
     * Converts a LocalDate into a more readable format of MMM dd, yyyy.
     *
     * @param date LocalDate
     * @return String of formatted date
     */
    public String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
