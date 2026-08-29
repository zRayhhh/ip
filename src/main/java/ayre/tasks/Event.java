package ayre.tasks;

import java.time.LocalDate;

public class Event extends TimedTask {
    private LocalDate startTime;
    private LocalDate endTime;

    public Event(String name, String startTime, String endTime) {
        super(name);
        this.startTime = LocalDate.parse(startTime);
        this.endTime = LocalDate.parse(endTime);
    }

    @Override
    public String toLogString() {
        return "E " + super.toLogString() + " " + startTime + " " + endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " <from: "
                + this.formatDate(startTime) + " to: "
                + this.formatDate(endTime) + ">";
    }
}
