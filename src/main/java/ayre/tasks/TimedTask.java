package ayre.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
// TODO: add support for DD-MM-YYYY dates/more flexible date validation

public class TimedTask extends Task {
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd, yyyy");;

    public TimedTask(String name) {
        super(name);
    }

    public String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
}
