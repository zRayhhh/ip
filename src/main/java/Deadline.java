import java.time.LocalDate;

public class Deadline extends TimedTask {
    private LocalDate dueBy;

    public Deadline(String name, String dueBy) {
        super(name);
        this.dueBy = LocalDate.parse(dueBy);
    }

    @Override
    public String toLogString() {
        return "D " + super.toLogString() + " " + dueBy;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " <by: " + this.formatDate(dueBy) + ">";
    }
}
